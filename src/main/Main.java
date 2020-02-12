package main;

class Main{
    public static void main(String[] args) {
        System.out.println("\n---------OUTPUT------------");

        //Create Genesis Block
        Block genesisBlock = Block.buildGenesisBlock();

        //Create blockchain
        Blockchain blockchain = new Blockchain(genesisBlock);

        //Create Genesis Node
        Node genesisNode = new Node(null, blockchain, 0);

        //Create wallets
        Wallet walletA = new Wallet(genesisNode, blockchain);
        Wallet walletB = new Wallet(genesisNode, blockchain);
        

        //Genesis (Initialise both wallets with 500)
        Transaction genesisTransactionA = new Transaction(null, walletA.publicKey, 500, null, blockchain);	
        genesisTransactionA.transactionId = "0"; //manually set the transaction id
        Transaction genesisTransactionB = new Transaction(null, walletB.publicKey, 500, null, blockchain);
        genesisTransactionB.transactionId = "1"; //manually set the transaction id	
                

        //Starting Block
        Block block1 = new Block(genesisBlock.hash);
        block1.addTransaction(genesisTransactionA);
        block1.addTransaction(genesisTransactionB);
        blockchain.addBlock(block1);

        //blockchain.printUTXOs();

        //Block 2
        walletA.sendFunds(walletB.publicKey, 10);
        walletB.sendFunds(walletA.publicKey, 0);
        walletB.sendFunds(walletA.publicKey, 0);
        walletB.sendFunds(walletA.publicKey, 1);
        walletB.sendFunds(walletA.publicKey, 0); 

        //Block 3
        walletB.sendFunds(walletA.publicKey, 1);
        walletB.sendFunds(walletA.publicKey, 0);
        walletB.sendFunds(walletA.publicKey, 0);
        walletB.sendFunds(walletA.publicKey, 0);
        walletB.sendFunds(walletA.publicKey, 0);

        

        // Block block3 = new Block(block2.hash);
        // block3.addTransaction(walletB.sendFunds(walletA.publicKey, 30));
        // blockchain.addBlock(block3);




        
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


        System.out.println("\n---------BLOCKCHAIN TRANSACTIONS--------");
        blockchain.printTransactions();

        System.out.println("Wallet A balance: " + walletA.getBalance());
        System.out.println("Wallet B balance: " + walletB.getBalance());

        System.out.println("Blockchain UTXOs: ");
        blockchain.printUTXOs();

    }
}


