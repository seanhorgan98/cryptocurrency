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

	public HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();
	
	public Wallet(){
		generateKeyPair();
		this.balance = 0;
	}

	public float getBalance(){
		updateBalance();
		return balance;
	}
	public void updateBalance(){
		float total = 0;	
        for (Map.Entry<String, TransactionOutput> item: Blockchain.UTXOs.entrySet()){
        	TransactionOutput UTXO = item.getValue();
            if(UTXO.isMine(publicKey)) { //if output belongs to me ( if coins belong to me )
            	UTXOs.put(UTXO.id,UTXO); //add it to our list of unspent transactions.
            	total += UTXO.value ; 
            }
		}
		balance = total;
	}
		
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

	public void printUTXOs(){
		updateBalance();
		System.out.println("UTXOs size: " + UTXOs.size());
		for (Map.Entry<String, TransactionOutput> item: UTXOs.entrySet()){
			System.out.println(item.getValue().value);
		}
	}


	public Transaction sendFunds(PublicKey recipient, float value){
		if(getBalance() < value){
			System.out.println("Wallet: Not enough funds!");
			return null;
		}

		ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
		
		float total = 0;
		for (Map.Entry<String, TransactionOutput> item: UTXOs.entrySet()){
			TransactionOutput UTXO = item.getValue();
			total += UTXO.value;
			
			inputs.add(new TransactionInput(UTXO.id, UTXO.value));

			if(total > value) break;
		}
	
		
		Transaction tx = new Transaction(publicKey, recipient, value, inputs);
		tx.outputs.add(new TransactionOutput(tx.reciepient, tx.value, tx.transactionId));
		tx.generateSignature(privateKey);

		for(TransactionInput input: inputs){
			UTXOs.remove(input.previousOutId);
		}
		//System.out.println("Sent from: " + StringUtil.getStringFromKey(publicKey) + ", to: " + StringUtil.getStringFromKey(recipient));
		return tx;
	}
	
}