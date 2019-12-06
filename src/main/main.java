package main;

class Main{
    public static void main(String[] args) {
        System.out.println("---------------OUTPUT------------------");

        Wallet walletA = new Wallet();
        Wallet walletB = new Wallet();
        

        // Transactions should always be wallet to wallet
        // But coin creation can just be a new transaction created with no sender or inputs

        //Genesis Transaction
        Transaction genesisTransactionA = new Transaction(null, walletA.publicKey, 999, null);
        Transaction genesisTransactionB = new Transaction(null, walletB.publicKey, 999, null);

        //Genesis Block
        Block genesisBlock = Block.buildGenesisBlock();
        genesisBlock.addTransaction(genesisTransactionA);
        genesisBlock.addTransaction(genesisTransactionB);
        
        //Merkle tree caught in infinite loop
        //genesisBlock.buildMerkleTree();

        Blockchain blockchain = new Blockchain(genesisBlock);
        
        //Coin creation, remember to add to block
        //Transaction creationTx = new Transaction(null, walletA.publicKey, 999, null);
        
        Block block1 = new Block(genesisBlock.hash);
        block1.addTransaction(walletB.createTransaction(walletA.publicKey, 5, null));
        blockchain.addBlock(block1);

        Block block2 = new Block(block1.hash);
        block2.addTransaction(walletA.createTransaction(walletB.publicKey, 3, null));
        blockchain.addBlock(block2);

        Block block3 = new Block(block2.hash);
        block3.addTransaction(walletA.createTransaction(walletB.publicKey, 3, null));
        blockchain.addBlock(block3);
        
        System.out.println("Blockchain is: " + Boolean.toString(blockchain.validateChain()));
        blockchain.printBlockchain();
        
        


/*
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
*/
    }
}


