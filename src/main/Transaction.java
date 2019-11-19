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

    public float getInputSum(){
        float inputTotal = 0;
        for (TransactionInput input : inputs){
            float inputValue = input.value;
            if (inputValue != 0){
                inputTotal += inputValue;
            }
        }
        return inputTotal;
    }


    public boolean processTransaction(){
        if (verifiySignature() == false){
            System.out.println("Transaction failed to verify.");
            return false;
        }

        float transactionFee = getInputSum() - value;
        transactionId = calulateHash();
        outputs.add(new TransactionOutput(this.reciepient, value, transactionId));
        //Transaction Fee
        outputs.add(new TransactionOutput( this.sender, transactionFee, transactionId));

        return true;
        
    }
}

class TransactionInput{
    public String previousOutId; //Hash pointer to previous output
    public int index; //The index of the previous transactions' output that is being claimed
    public byte[] signature;
    public float value;

    public TransactionInput(String previousOutId){
        this.previousOutId = previousOutId;
    }

    

}

class TransactionOutput{
    public float value;
    public String id;
    public PublicKey reciepient;
    public String parentTransactionId;

    public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId){
        this.reciepient = reciepient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtil.applySha256(StringUtil.getStringFromKey(reciepient) + Float.toString(value) + parentTransactionId);
    }
}