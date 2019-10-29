import java.security.*;
import java.util.ArrayList;


/**
 * When Transaction is created:
 * Create metadata
 * Create Transaction Inputs
 * Create Transaction Outputs
 */
class Transaction{
    public String transactionHash; //Serves as an ID
    public PublicKey sender;
    public PublicKey reciepient;

    public float value;

    public byte[] signature; //Used for verification

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();  //These types will have
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>(); //to be changed to objects
    

    public Transaction(PublicKey sender, PublicKey reciepient, float value,  ArrayList<TransactionInput> inputs) {
		this.sender = sender;
		this.reciepient = reciepient;
		this.value = value;
        this.inputs = inputs;
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
}

class TransactionInput{
    public String previousOutId; //Hash pointer to previous output
    public int index; //The index of the previous transactions' output that is being claimed
    public byte[] signature;

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