import java.util.*;

class Blockchain {
    public ArrayList<Block> blockChain = new ArrayList<Block>(1);
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
            System.out.println("Block: " + block.index + ", Hash: " + block.hash + ", previousHash: " + block.previousHash);
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
}