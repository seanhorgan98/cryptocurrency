package main;

import java.util.*;
import main.TransactionOutput;

class Blockchain {
    public ArrayList<Block> blockChain = new ArrayList<Block>(1);
    //List of all unspent transactions in a Map<TransactionOutput.id, TransactionOutput>
    public static Map<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();
    public static int diffuculty = 1;
    public static final float MIN_TRANSACTION = 0.1f;
    public static final int DIFFICULTY = 2;

    public Blockchain(Block genesisBlock){
        //Create genisis block
        this.blockChain.add(genesisBlock);
    }

    public void addBlock(Block blockToAdd){        
        blockToAdd.previousHash = blockChain.get(blockChain.size()-1).hash;
        blockToAdd.mineBlock(DIFFICULTY);
        blockChain.add(blockToAdd);
    }

    public void printBlocks(){
        System.out.println("Blockchain:");
        for (Block block : blockChain) {
            System.out.println("Hash: " + block.hash + ", previousHash: " + block.previousHash);
        }
    }

    public void printTransactions(){
        System.out.println("\n--------Full Blockchain Transactions--------");
        for (Block block : blockChain){
            for(Transaction t : block.transactions){
                System.out.println("From: " + StringUtil.getStringFromKey(t.sender) + ", To: " + StringUtil.getStringFromKey(t.reciepient));
                System.out.println("Value: " + t.value);
            }
        }
    }

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