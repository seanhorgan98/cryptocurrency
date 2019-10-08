import Block.java;
import java.util.*;

class Blockchain {
    public ArrayList<Block> blockChain = new ArrayList<Block>();
    public static int diffuculty = 1;

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

    public Boolean validateChain(){
        for(int i = 0; i < blockChain.size(); i++){
            Block currentBlock = blockChain.get(i);
            Block previousBlock = blockChain.get(i-1);

            if (currentBlock.hash != currentBlock.calculateHash()){
                return false;
            }
            if (currentBlock.previousHash != previousBlock.hash){
                return false;
            }
        }
        return true;
    }
}