package main;

class Main{
    public static void main(String[] args) {
        System.out.println("\n---------OUTPUT------------");

        Wallet walletA = new Wallet();
        Wallet walletB = new Wallet();
        
        // Transactions should always be wallet to wallet
        // But coin creation can just be a new transaction created with no sender or inputs

        

        //Genesis (Initialise both wallets with 500)
        Transaction genesisTransactionA = new Transaction(null, walletA.publicKey, 500, null);	
        genesisTransactionA.transactionId = "0"; //manually set the transaction id
        Transaction genesisTransactionB = new Transaction(null, walletB.publicKey, 500, null);
        genesisTransactionB.transactionId = "1"; //manually set the transaction id	
        
		//genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.value, genesisTransaction.transactionId)); //manually add the Transactions Output
        
        //Blockchain.UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
        //System.out.println(genesisTransaction.outputs.get(0).value);

        //Transaction genesisTransactionB = new Transaction(null, walletB.publicKey, 500, null);
        Block genesisBlock = Block.buildGenesisBlock();
        Blockchain blockchain = new Blockchain(genesisBlock);
        

        //Block 1
        Block block1 = new Block(genesisBlock.hash);
        block1.addTransaction(genesisTransactionA);
        block1.addTransaction(genesisTransactionB);
        blockchain.addBlock(block1);

        //Block 2
        Block block2 = new Block(block1.hash);
        block2.addTransaction(walletA.sendFunds(walletB.publicKey, 10));
        //block2.addTransaction(walletB.sendFunds(walletA.publicKey, 100));
        blockchain.addBlock(block2);
        //System.out.println(genesisTransactionA.outputs.get(0).value);

        //Block 3
        Block block3 = new Block(block2.hash);
        block3.addTransaction(walletB.sendFunds(walletA.publicKey, 30));
        blockchain.addBlock(block3);
        
        System.out.println("\n---------BLOCKCHAIN--------");
        System.out.println("Valid Blockchain: " + Boolean.toString(blockchain.validateChain()));
        blockchain.printBlocks();

        
        System.out.println("\n---------BALANCE-----------");
        System.out.println("Wallet A: " + walletA.getBalance());
        System.out.println("Wallet B: " + walletB.getBalance());
        

        System.out.println("\n---------UTXOs--------");
        System.out.println("Wallet A hash: " + StringUtil.getStringFromKey(walletA.publicKey));
        System.out.println("Wallet A UTXOs: "); walletA.printUTXOs();

        System.out.println("\nWallet B hash: " + StringUtil.getStringFromKey(walletB.publicKey));
        System.out.println("Wallet B UTXOs: "); walletB.printUTXOs();

        blockchain.printTransactions();

    }
}


