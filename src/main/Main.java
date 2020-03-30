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


        walletList.get(1).sendFunds(walletList.get(2).publicKey, 5);
        walletList.get(2).sendFunds(walletList.get(1).publicKey, 10);

        walletList.get(1).sendFunds(walletList.get(3).publicKey, 50);
        walletList.get(2).sendFunds(walletList.get(1).publicKey, 10);

        System.out.println("WALLET 3 BALANCE: " + walletList.get(3).getBalance());


        
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


