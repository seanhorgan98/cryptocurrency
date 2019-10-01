import Block.java;
import java.util.*;

class Blockchain {
    public ArrayList<Block> blockChain = new ArrayList<Block>();

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
            System.out.println("Block " + block.index + ": " + block.hash);
        }
    }
}