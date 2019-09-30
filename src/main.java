/**
 * Main Class used for testing the blockchain and blocks.
 */

import Block.java;
import Blockchain.java;
import java.time.Instant;

class Main{
    public static void main(String[] args) {
        System.out.println("-----------------------------------------");
        //1. Create Genisis Block
        //2. Create blockchain
        Long unixTime = Instant.now().getEpochSecond();
        Block firstBlock = new Block(1, unixTime, "previousBlockHash", "new data!");
        
        System.out.println("Block Info: " + firstBlock.hash);
    }
}