package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Main {
    public static void main(String[] args) {
        System.out.println("\n---------OUTPUT------------");
        final int TEST_LENGTH = 10;

        //Create Genesis Block
        Block genesisBlock = Block.buildGenesisBlock();

        //Create blockchain
        Blockchain blockchain = new Blockchain(genesisBlock);

        //Create Genesis Node
        Node genesisNode = new Node(null, blockchain, 0);

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
        //Necessary as coin creation transactions are deliberately not added to the blockchain automatically
        blockchain.addBlock(block1);

        Random r = new Random();
        r.setSeed(2184253);
        for(int i = 0; i < TEST_LENGTH; i++){
            //Create a transaction from two random wallets
            int wallet1 = r.nextInt(10);
            int wallet2 = r.nextInt(10);
            //Make sure wallets are not the same
            while(wallet2 == wallet1){
                wallet2 = r.nextInt(10);
            }
            float value = r.nextInt(10);
            walletList.get(wallet1).sendFunds(walletList.get(wallet2).publicKey, value);
        }
        
        System.out.println("\n---------BLOCKCHAIN--------");
        System.out.println("Valid Blockchain: " + Boolean.toString(blockchain.validateChain()));
        blockchain.printBlocks();

        
        System.out.println("\n---------BALANCE-----------");
        for(int i = 0; i < walletList.size(); i++){
            System.out.println("Wallet " + i + ": " + walletList.get(i).getBalance());
        }
        

        // System.out.println("\n---------UTXOs--------");
        // System.out.println("Wallet 1 hash: " + StringUtil.getStringFromKey(walletList.get(1).publicKey));
        // System.out.println("Wallet 1 UTXOs: "); walletList.get(1).printUTXOs();

        // System.out.println("\nWallet 2 hash: " + StringUtil.getStringFromKey(walletList.get(2).publicKey));
        // System.out.println("Wallet 2 UTXOs: "); walletList.get(2).printUTXOs();

        blockchain.printTransactions();

    }
}


