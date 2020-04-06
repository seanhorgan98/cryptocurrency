package main;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class Wallet {
	
	public PrivateKey privateKey;
	public PublicKey publicKey;
	private float balance;
	private Node parentNode;

	private Blockchain currentBlockchain;

	public HashMap<String,TransactionOutput> walletUTXOs = new HashMap<String,TransactionOutput>();
	
	public Wallet(Node node, Blockchain blockchain){
		this.currentBlockchain = blockchain;
		this.parentNode = node;
		generateKeyPair();
		this.balance = 0;
	}

	// Utility function to return the balance of this wallet
	public float getBalance(){
		balance = updateBalance();
		return balance;
	}

	// Loops through all the UTXOs and picks out the one addressed to this wallet.
	// Then returns the sum of the values for these UTXOs
	public float updateBalance(){
		float total = 0;	
        for (Map.Entry<String, TransactionOutput> item: currentBlockchain.UTXOs.entrySet()){
        	TransactionOutput UTXO = item.getValue();
            if(UTXO.isMine(publicKey) && !walletUTXOs.containsKey(UTXO.id)) { //if output belongs to me ( if coins belong to me )
            	walletUTXOs.put(UTXO.id,UTXO); //add it to our list of unspent transactions.
            	total += UTXO.value ; 
            }
		}
		return total;
	}
	
	// Generates a public and private key pair for this wallets addresses
	public void generateKeyPair() {
		try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(new ECGenParameterSpec("secp256r1"), new SecureRandom());
            KeyPair keyPair = keyGen.generateKeyPair();
            
            // Set the public and private keys from the keyPair
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	//Client side check to see if wallet has enough coins to make transaction
	public boolean sufficientFunds(Transaction tx){
		if(balance < tx.value){
			return false;
		}

		return true;
	}

	// Loops through the UTXOs and prints out the value for each
	public void printUTXOs(){
		updateBalance();
		System.out.println("UTXOs size: " + walletUTXOs.size());
		for (Map.Entry<String, TransactionOutput> item: walletUTXOs.entrySet()){
			System.out.println(item.getValue().value);
		}
	}

	//Update the parent node to start flooding the transaction to other nodes
	private void updateNode(Transaction tx){
		parentNode.floodTransaction(tx);
	}


	// Creates a transaction from this wallet to the recipient address of amount 'value'
	// Checks to make sure the wallet has the sufficient funds to send the coins first
	public Transaction sendFunds(PublicKey recipient, float value){
		if(getBalance() < value){
			System.out.println("Wallet: Not enough funds! Value: " + value + ", Current Balance: " + balance);
			return null;
		}

		ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
		
		
		float total = 0;
		for (Map.Entry<String, TransactionOutput> item: walletUTXOs.entrySet()){
			TransactionOutput UTXO = item.getValue();

			
			if(UTXO.value >= value){
				inputs.add(new TransactionInput(UTXO.id, UTXO.value));
				break;
			}else{
				total += UTXO.value;
				//System.out.println("UTXO value: " + UTXO.value);
				inputs.add(new TransactionInput(UTXO.id, UTXO.value));
				if(total > value) break;
			}
		}
	
		
		Transaction tx = new Transaction(publicKey, recipient, value, inputs, currentBlockchain);
		//Is this line necessary
		tx.outputs.add(new TransactionOutput(tx.reciepient, tx.value, tx.transactionId));
		tx.generateSignature(privateKey);

		updateBalance();

		for(TransactionInput input: inputs){
			walletUTXOs.remove(input.previousOutId);
		}
		updateNode(tx);
		return tx;
	}
	
}