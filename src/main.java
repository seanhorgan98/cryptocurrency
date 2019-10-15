/**
 * Main Class used for testing the blockchain and blocks.
 */

import Block.java;
import Blockchain.java;

class Main{
    public static void main(String[] args) {
        System.out.println("---------------OUTPUT------------------");

        //Add Block 1
        Block genisisBlock = new Block(0, "0", "Genisis Data!");
        Blockchain blockChain = new Blockchain(genisisBlock);

        //Add block 2
        Block secondBlock = new Block(1, genisisBlock.hash, "Second block data!");
        blockChain.addBlock(secondBlock);

        //Add block 3
        Block thirdBlock = new Block(2, secondBlock.hash, "Third block data!");
        blockChain.addBlock(thirdBlock);

        
        
        System.out.println("Mining block 0...");
        blockChain.blockChain.get(0).mineBlock(2);

        blockChain.printBlockchain();
        System.out.println("Valid blockchain: " + blockChain.validateChain());
    }
}