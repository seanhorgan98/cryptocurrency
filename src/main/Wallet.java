package main;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Map;
import java.util.ArrayList;

public class Wallet {
	
	public PrivateKey privateKey;
	public PublicKey publicKey;
	private float balance;
	
	public Wallet(){
		generateKeyPair();
		this.balance = 0;
	}

	public float getBalance(){
		updateWallet();
		return balance;
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

	public void updateWallet(){
		//Loop through UTXOs and update balance where hashs match this wallet
		for (Map.Entry<String, TransactionOutput> entry : Blockchain.UTXOs.entrySet()) {
			if(StringUtil.getStringFromKey(entry.getValue().reciepient)  == StringUtil.getStringFromKey(publicKey)){
				balance += entry.getValue().value;
			}
		}
	}

	//Client side check to see if wallet has enough coins to make transaction
	public boolean sufficientFunds(Transaction tx){
		if(balance < tx.value){
			return false;
		}

		return true;
	}


	public Transaction createTransaction(PublicKey recipient, float value, ArrayList<TransactionInput> inputs){
		Transaction tx = new Transaction(publicKey, recipient, value, inputs);
		tx.outputs.add(new TransactionOutput(tx.reciepient, tx.value, tx.transactionId));
		tx.generateSignature(privateKey);
		return tx;
	}
	
}