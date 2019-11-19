package main;

class Main{
    public static void main(String[] args) {
        System.out.println("---------------OUTPUT------------------");

        Wallet walletA = new Wallet();
        Wallet walletB = new Wallet();
        

        // System.out.println("Private and public keys:");
		// System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
		// System.out.println(StringUtil.getStringFromKey(walletA.publicKey));

        //Genesis Transaction
        Transaction genesisTransaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
        genesisTransaction.generateSignature(walletA.privateKey);
        genesisTransaction.inputs.add(new TransactionInput("0"));
        genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.value, genesisTransaction.transactionId));

        //Genesis Block
        Block genesisBlock = new Block("genesis");
        genesisBlock.addTransaction(genesisTransaction);
        //genesisBlock.buildMerkleTree();

        Blockchain blockchain = new Blockchain(genesisBlock);

        Block block1 = new Block(genesisBlock.hash);
        Transaction transactionB = new Transaction(walletB.publicKey, walletA.publicKey, 5, null);
        block1.addTransaction(transactionB);
        block1.mineBlock(2);
        blockchain.addBlock(block1);

        Block block2 = new Block(block1.hash);
        Transaction transactionC = new Transaction(walletA.publicKey, walletB.publicKey, 3, null);
        block2.addTransaction(transactionC);
        block2.mineBlock(2);
        blockchain.addBlock(block2);

        Block block3 = new Block(block2.hash);
        Transaction transactionD = new Transaction(walletA.publicKey, walletB.publicKey, 3, null);
        block3.addTransaction(transactionD);
        block3.mineBlock(2);
        blockchain.addBlock(block3);

        System.out.println("Blockchain is: " + Boolean.toString(blockchain.validateChain()));
        
        


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


