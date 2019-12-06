package main;

class TransactionInput{
    public String previousOutId; //Hash pointer to previous output
    public int index; //The index of the previous transactions' output that is being claimed DONT THINK THIS IS RELEVANT
    public byte[] signature;
    public float value;
    public TransactionOutput UTXO;

    public TransactionInput(String previousOutId, float value){
        this.previousOutId = previousOutId;
        this.value = value;
    }

    

}