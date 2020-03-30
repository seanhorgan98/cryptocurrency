package main;

import java.util.ArrayList;
import java.util.List;

class Main {
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

        int NUMBER_OF_NODES = 10;
        List<Wallet> walletList = new ArrayList<Wallet>();
        List<Node> nodeList = new ArrayList<Node>();
        Block block1 = new Block(genesisBlock.hash);;

        // TEST HARNESS
        for (int i = 1; i <= NUMBER_OF_NODES; i++){
            // Create Nodes
            //If not 1st node
            Node newNode;
            if(i!= 1){
                //Create node from previous node in the list as seed node
                Node previousNode = nodeList.get(nodeList.size()-1);
                newNode = new Node(previousNode, blockchain, i);
            }else{
                //Create node from genesis node
                newNode = new Node(genesisNode, blockchain, i);
            }

            //Create Wallets
            Wallet newWallet = new Wallet(newNode, blockchain);

            //Store new node and wallet
            nodeList.add(newNode); 
            walletList.add(newWallet);

            //Set balance of all nodes to 100
            Transaction initialBalance = new Transaction(null, newWallet.publicKey, 100, null, blockchain);
            initialBalance.transactionId = Integer.toString(i);
            
            block1.addTransaction(initialBalance);
        }
        
        // //Genesis (Initialise both wallets with 500)
        // Transaction genesisTransactionA = new Transaction(null, walletA.publicKey, 100, null);	
        // genesisTransactionA.transactionId = "0"; //manually set the transaction id
        // Transaction genesisTransactionB = new Transaction(null, walletB.publicKey, 100, null);
        // genesisTransactionB.transactionId = "1"; //manually set the transaction id	
        
        //Starting Block
        // Block block1 = new Block(genesisBlock.hash);
        // block1.addTransaction(genesisTransactionA);
        // block1.addTransaction(genesisTransactionB);
        blockchain.addBlock(block1);

        //System.out.println("Wallet B balance: " + walletB.getBalance());

        //Block 2
        // walletA.sendFunds(walletB.publicKey, 10);
        //Block block2 = new Block(block1.hash);
        //block2.addTransaction(walletA.sendFunds(walletB.publicKey, 10));
        //block2.addTransaction(walletB.sendFunds(walletA.publicKey, 100));
        //blockchain.addBlock(block2);
        //System.out.println(genesisTransactionA.outputs.get(0).value);

        //Block 3
        // walletB.sendFunds(walletA.publicKey, 1);
        // walletB.sendFunds(walletA.publicKey, 1);
        // walletB.sendFunds(walletA.publicKey, 1);
        // walletB.sendFunds(walletA.publicKey, 1);

        // Block block3 = new Block(block2.hash);
        // block3.addTransaction(walletB.sendFunds(walletA.publicKey, 30));
        // blockchain.addBlock(block3);

        System.out.println("1--5--2");
        walletList.get(1).sendFunds(walletList.get(2).publicKey, 5);
        System.out.println("2--10--1");
        walletList.get(2).sendFunds(walletList.get(1).publicKey, 10);

        System.out.println("1--50--3");
        walletList.get(1).sendFunds(walletList.get(3).publicKey, 50);
        System.out.println("2--10--1");
        walletList.get(2).sendFunds(walletList.get(1).publicKey, 10);

        System.out.println("RANDOM WALLET BALANCE: " + walletList.get(3).getBalance());


        
        System.out.println("\n---------BLOCKCHAIN--------");
        System.out.println("Valid Blockchain: " + Boolean.toString(blockchain.validateChain()));
        blockchain.printBlocks();

        
        System.out.println("\n---------BALANCE-----------");
        System.out.println("Wallet 1: " + walletList.get(1).getBalance());
        System.out.println("Wallet 2: " + walletList.get(2).getBalance());
        

        System.out.println("\n---------UTXOs--------");
        System.out.println("Wallet 1 hash: " + StringUtil.getStringFromKey(walletList.get(1).publicKey));
        System.out.println("Wallet 1 UTXOs: "); walletList.get(1).printUTXOs();

        System.out.println("\nWallet 2 hash: " + StringUtil.getStringFromKey(walletList.get(2).publicKey));
        System.out.println("Wallet 2 UTXOs: "); walletList.get(2).printUTXOs();

        //blockchain.printTransactions();

    }
}


