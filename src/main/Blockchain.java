package main;

import java.security.PublicKey;
import java.util.*;
import main.TransactionOutput;

class Blockchain {
    public ArrayList<Block> blockChain = new ArrayList<Block>(1);
    public static Map<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //list of all unspent transactions. 
    public static int diffuculty = 1;
    public static final float MIN_TRANSACTION = 1.0f;

    public Blockchain(Block genisisBlock){
        //Create genisis block
        this.blockChain.add(genisisBlock);
    }

    public void addBlock(Block blockToAdd){
        blockChain.add(blockToAdd);
    }

    public void printBlockchain(){
        System.out.println("Blockchain:");
        for (Block block : blockChain) {
            System.out.println("Hash: " + block.hash + ", previousHash: " + block.previousHash);
        }
    }

    public Boolean validateChain(){
        for(int i = 1; i < blockChain.size(); i++){
            Block currentBlock = blockChain.get(i);
            Block previousBlock = blockChain.get(i-1);

            if (!currentBlock.hash.equals(currentBlock.calculateHash())){
                return false;
            }
            if (!currentBlock.previousHash.equals(previousBlock.hash)){
                return false;
            }
        }
        return true;
    }
    
    public void createCoin(PublicKey reciepient, float value){
        TransactionOutput createOutput = new TransactionOutput(reciepient, value, "0");
        UTXOs.put(createOutput.id, createOutput);
    }
}