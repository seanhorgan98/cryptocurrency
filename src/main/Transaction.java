package main;

import java.security.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


class Transaction{
    public String transactionId; //Serves as an ID
    public PublicKey sender;
    public PublicKey reciepient;
    public float value;

    private Blockchain currentBlockchain;

    private boolean coinCreationFlag;

    public byte[] signature; //Used for verification

    public List<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public List<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
    
    
    public Transaction(PublicKey sender, PublicKey reciepient, float value,  List<TransactionInput> inputs, Blockchain blockchain) {
		this.currentBlockchain = blockchain;
		this.reciepient = reciepient;
        this.value = value;
        
        //Check for coin creation
        if(sender == null || inputs == null){
            coinCreationFlag = true;
            this.inputs = Collections.emptyList();
        }else{
            coinCreationFlag = false;
            this.inputs = inputs;
            
        }
        this.sender = sender;
        
        this.transactionId = calulateHash();
        
    }

    // Creates a String hash of all the variables of the transaction
    private String calulateHash() {
		return StringUtil.applySha256(StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value));
    }


    // Generates a signature for the transaction
    // In future will add in the inputs and outputs
    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value)	;
        signature = StringUtil.applyECDSASig(privateKey,data);
    }

    // Verifies that the signature is valid for the transaction
    // In future will add in the inputs and outputs
    public boolean verifiySignature() {
        if(sender == null){
            return true;
        }else{
            String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value)	;
            return StringUtil.verifyECDSASig(sender, data, signature);
        }
    }

    // Get sum of input values
    public float getInputsSum(){
        float inputTotal = 0;
        for (TransactionInput input : inputs){
            //System.out.println("Inputs: " + input.value);
            float inputValue = input.value;
            if (inputValue != 0){
                inputTotal += inputValue;
            }
        }
        return inputTotal;
    }

    // Get sum of output values
    public float getOutputsSum() {
		float total = 0;
		for(TransactionOutput o : outputs) {
			total += o.value;
		}
		return total;
	}


    /* Processes the transaction
     * First verifying that the signature is valid
     * Then gathers all the inputs and performs checks to make sure transaction is valid
     * Then sends funds from the sender to the recipient using UTXOs
     * If there is no exact amount of UTXO to fulfill transaction then the overpay will be returned to the sender
     * Finally it updates the UTXOs
    */
    public boolean processTransaction(){
        if (verifiySignature() == false){
            System.out.println("Transaction failed to verify.");
            return false;
        }

        // Get transaction inputs
        for (TransactionInput i : inputs){
            i.UTXO = currentBlockchain.UTXOs.get(i.previousOutId);
        }
        float inputSum = getInputsSum();

        //check if transaction is valid:
		if(inputSum < Blockchain.MIN_TRANSACTION && inputs.size() > 0) {
			System.out.println("Transaction Inputs too small: " + getInputsSum());
			return false;
        }
        
        // Check sufficient funds. Might be redundant with wallet check as well
        if(inputSum < value && coinCreationFlag == false){
            System.out.println("Insufficient funds, Inputs: " + inputSum + ", Value: " + value);
            return false;
        }
        float overPay = inputSum - value;
        if(inputSum == 0){
            overPay = 0;
        }
        
        System.out.println("Value: " + value + ", Inputsum: " + inputSum + ", Overpay: " + overPay);
        transactionId = calulateHash();
        outputs.add(new TransactionOutput(this.reciepient, value, transactionId)); //Send value to reciepient
        outputs.add(new TransactionOutput( this.sender, overPay, transactionId)); //Send Left over back to sender
        
        //UTXo key cannot be address as it will overwrite previous transactions. Needs to be transaction ID

        //Add outputs to Unspent list
		for(TransactionOutput o : outputs) {
			currentBlockchain.UTXOs.put(o.id , o);
		}
		
		//Remove transaction inputs from UTXO lists as spent:
		for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue; //if Transaction can't be found skip it 
            System.out.println("Here");
			currentBlockchain.UTXOs.remove(i.UTXO.id);
		}
		
		return true;
        
    }
}