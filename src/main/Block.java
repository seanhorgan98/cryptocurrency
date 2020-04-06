package main;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class Block {
    private Long timeStamp;
    private String merkleRoot;
    public String previousHash;
    public String hash; //ID
    public List<Transaction> transactions;

    public Block(String previousHash){
        this.timeStamp = Instant.now().getEpochSecond();
        
        this.merkleRoot = null;
        this.transactions = new ArrayList<Transaction>();
        this.previousHash = previousHash;
        this.hash = calculateHash(0);
    }

    // Creates the first block in the chain
    public static Block buildGenesisBlock(){
        return new Block("0");
    }

    // Adds a transaction to the block
    public boolean addTransaction(Transaction t){
        if(t == null){return false;}
        if(previousHash == null){return false;}

        if(t.processTransaction() != true){
            System.out.println("Transaction failed to process. Discarded");
            return false;
        }
        transactions.add(t);
        return true;
    }

    // Creates a String hash of all the variables of the block
    public String calculateHash(int nonce) {
        String calculatedhash = StringUtil.applySha256( 
                previousHash +
                Long.toString(timeStamp) +
                Integer.toString(nonce)
                );
        return calculatedhash;
    }

    // Performs proof of work calculations on the hash to try match a string starting with
    // a substring of "0"s equal in length to the difficulty
    // Done as a proof of work concept in order to select which node mines the block
    public boolean mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        int nonce = 0; 
        String newHash = hash;
		while(!(newHash.substring( 0, difficulty).equals(target))) {
            nonce ++;
			newHash = calculateHash(nonce);
        }
        System.out.println("Block Mined: " + newHash);
        return true;
    }
    
    // Builds the merkle tree structure for storing the transactions in the blocks
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

    // Utility function for creating the merkle tree structure
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

    // Returns the root of the merkle tree of transactions
    public String getMerkleRoot(){
        return this.merkleRoot;
    }
}