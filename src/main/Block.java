package main;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class Block {
    private Long timeStamp;
    private String merkleRoot;
    public String previousHash;
    public String hash; //ID
    private int nonce;
    public List<Transaction> transactions;

    public Block(){
        this.timeStamp = Instant.now().getEpochSecond();
        this.hash = calculateHash();
        this.merkleRoot = null;
        this.transactions = new ArrayList<Transaction>();
        this.nonce = 0;
    }

    public boolean addTransaction(Transaction t){
        if(t == null){return false;}
        if(previousHash == null){return false;}

        if(t.processTransaction() != true){
            System.out.println("Transaction failed to process. Discarded");
            return false;
        }
        transactions.add(t);
        System.out.println("Transaction successfully added to block.");
        return true;
    }

    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256( 
                previousHash +
                Long.toString(timeStamp) +
                Integer.toString(nonce)
                );
        return calculatedhash;
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); 
        String newHash = hash;
		while(!(newHash.substring( 0, difficulty).equals(target))) {
            nonce ++;
			newHash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + newHash);
    }
    
    //Called once all transactions are added
    public void buildMerkleTree(){
        List<String> tree = new ArrayList<String>();

        //Create tree of hashes
        for (Transaction t : transactions){
            tree.add(t.transactionId);
        }

        List<String> newTxList = getNewTxList(tree);
        while (newTxList.size() != 1) {
            newTxList = getNewTxList(newTxList);
        }
    
        this.merkleRoot = newTxList.get(0);
    }

    public boolean merkleTreeTest(){


        return true;
    }

    private List<String> getNewTxList(List<String> tempTxList){
        List<String> newTxList = new ArrayList<String>();
        int index = 0;
        while (index < tempTxList.size()){
            //left
            String left = tempTxList.get(index);
            index++;

            //right
            String right = "";
            if (index != tempTxList.size()){
                right = tempTxList.get(index);
            }

            //hash
            newTxList.add(StringUtil.applySha256(left + right));
            index++;
        }
        return newTxList;

    }

    public String getMerkleRoot(){
        return this.merkleRoot;
    }
}