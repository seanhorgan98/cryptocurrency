/**
 * Main Class used for testing the blockchain and blocks.
 */

import Block.java;
import Blockchain.java;

class Main{
    public static void main(String[] args) {
        System.out.println("---------------OUTPUT------------------");

        Block genisisBlock = new Block(0, "previousBlockHash", "Genisis Data!");
        Blockchain blockChain = new Blockchain(genisisBlock);

        Block secondBlock = new Block(1, genisisBlock.hash, "Second block data!");
        blockChain.addBlock(secondBlock);
        
        System.out.println("Genisis Block Hash: " + genisisBlock.hash);
        System.out.println("Second Block previousHash: " + blockChain.blockChain.get(1).previousHash);
    }
}