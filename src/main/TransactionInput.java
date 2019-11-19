package main;

class TransactionInput{
    public String previousOutId; //Hash pointer to previous output
    public int index; //The index of the previous transactions' output that is being claimed
    public byte[] signature;
    public float value;
    public TransactionOutput UTXO;

    public TransactionInput(String previousOutId){
        this.previousOutId = previousOutId;
    }

    

}