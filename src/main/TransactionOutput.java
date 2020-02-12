package main;

import java.security.*;

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

    // Check if a coin belongs to you
    public boolean isMine(PublicKey publicKey) {
        if(publicKey.equals(reciepient)){
            return true;
        }else{
            return false;
        }
	}
}