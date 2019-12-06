package main;

import java.util.*;
import main.TransactionOutput;

class Blockchain {
    public ArrayList<Block> blockChain = new ArrayList<Block>(1);

    //List of all unspent transactions in a Map<TransactionOutput.id, TransactionOutput>
    public static Map<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();
    
    public static final float MIN_TRANSACTION = 0.1f;
    public static final int DIFFICULTY = 2;

    // Create blockchain using a genesis block
    public Blockchain(Block genesisBlock){
        this.blockChain.add(genesisBlock);
    }

    // Adds a block to the blockchain by setting it's previous hash to the hash of the
    // current tail of the blockchain and then mining the block
    public void addBlock(Block blockToAdd){        
        blockToAdd.previousHash = blockChain.get(blockChain.size()-1).hash;
        blockToAdd.mineBlock(DIFFICULTY);
        blockChain.add(blockToAdd);
    }

    // Print out the hashs for every block in the blockchain
    public void printBlocks(){
        System.out.println("Blockchain:");
        for (Block block : blockChain) {
            System.out.println("Hash: " + block.hash + ", previousHash: " + block.previousHash);
        }
    }

    // Loop through all blocks and print out all transactions from each
    public void printTransactions(){
        System.out.println("\n--------Full Blockchain Transactions--------");
        for (Block block : blockChain){
            for(Transaction t : block.transactions){
                System.out.println("From: " + StringUtil.getStringFromKey(t.sender) + ", To: " + StringUtil.getStringFromKey(t.reciepient));
                System.out.println("Value: " + t.value);
            }
        }
    }

    // Loops through each block and makes sure it's previousHash matches with the previous
    // block's hash
    public Boolean validateChain(){
        for(int i = 1; i < blockChain.size(); i++){
            Block currentBlock = blockChain.get(i);
            Block previousBlock = blockChain.get(i-1);

            if (currentBlock.hash.equals(currentBlock.calculateHash(0)) == false){
                return false;
            }
            if (!currentBlock.previousHash.equals(previousBlock.hash)){
                return false;
            }
        }
        return true;
    }
}