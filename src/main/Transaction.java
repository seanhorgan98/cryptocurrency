package main;

import java.security.*;
import java.util.ArrayList;


class Transaction{
    public String transactionId; //Serves as an ID
    public PublicKey sender;
    public PublicKey reciepient;

    public float value;

    public byte[] signature; //Used for verification

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
    

    public Transaction(PublicKey sender, PublicKey reciepient, float value,  ArrayList<TransactionInput> inputs) {
		this.sender = sender;
		this.reciepient = reciepient;
        this.value = value;
        
        if (inputs == null){
            inputs = new ArrayList<TransactionInput>();
        }else{
            this.inputs = inputs;
        }
        
        this.transactionId = calulateHash();
    }

    private String calulateHash() {
		return StringUtil.applySha256(StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value));
    }
    

    //Generates a signature for the transaction
    //In future will add in the inputs and outputs
    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value)	;
        signature = StringUtil.applyECDSASig(privateKey,data);		
    }

    //Verifies that the signature is valid for the transaction
    //In future will add in the inputs and outputs
    public boolean verifiySignature() {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value)	;
        return StringUtil.verifyECDSASig(sender, data, signature);
    }

    //Get sum of input values
    public float getInputsSum(){
        float inputTotal = 0;
        for (TransactionInput input : inputs){
            float inputValue = input.value;
            if (inputValue != 0){
                inputTotal += inputValue;
            }
        }
        return inputTotal;
    }

    //Get sum of output values
    public float getOutputsSum() {
		float total = 0;
		for(TransactionOutput o : outputs) {
			total += o.value;
		}
		return total;
	}


    public boolean processTransaction(){
        if (verifiySignature() == false){
            System.out.println("Transaction failed to verify.");
            return false;
        }

        // Get transaction inputs
        for (TransactionInput i : inputs){
            i.UTXO = Blockchain.UTXOs.get(i.previousOutId);
        }

        //check if transaction is valid:
		if(getInputsSum() < Blockchain.MIN_TRANSACTION) {
			System.out.println("#Transaction Inputs to small: " + getInputsSum());
			return false;
		}

        float transactionFee = getInputsSum() - value;
        transactionId = calulateHash();
        outputs.add(new TransactionOutput(this.reciepient, value, transactionId));
        //Transaction Fee
        outputs.add(new TransactionOutput( this.sender, transactionFee, transactionId));

        //add outputs to Unspent list
		for(TransactionOutput o : outputs) {
			Blockchain.UTXOs.put(o.id , o);
        }
        
        //remove transaction inputs from UTXO lists as spent:
		for(TransactionInput i : inputs) {
			if(i.UTXO == null) continue; //if Transaction can't be found skip it 
			Blockchain.UTXOs.remove(i.UTXO.id);
		}

        return true;
        
    }
}