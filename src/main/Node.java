package main;

import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Node{
    //Id of this node
    public String id;
    public final int NEARBY_NODE_SIZE = 10;
    public final int BLOCK_SIZE = 5; //Number of transactions in a block

    //List of all transactions not in the blockchain
    private List<Transaction> allTransactions;
    
    //List of all other nodes
    private List<Node> nearbyNodes;

    //Copy of the current blockchain to use
    private Blockchain currentBlockchain;


    //Main Constructor
    public Node(Node seedNode, Blockchain blockchain, int index){
        this.nearbyNodes = new ArrayList<Node>();
        this.allTransactions = new ArrayList<Transaction>();
        this.currentBlockchain = blockchain;

        //If not genesis node
        if(seedNode != null){
            //Get list of all nearby nodes from seedNode. Need to request random assortment of nodes.
            while(nearbyNodes.size() < NEARBY_NODE_SIZE){
                Node nodeToAdd = getRandomNearbyNode(seedNode);
                if(!nearbyNodes.contains(nodeToAdd)){
                    nearbyNodes.add(nodeToAdd);
                }
            }

            //Assign node ID
            this.id = calculateHash(0, seedNode.id, index);
        }else{
            this.id = calculateHash(0, "", index);
        }

        //Add itself to the nearby nodes list
        nearbyNodes.add(this);

        

    }

    // Creates a String hash of all the variables of the node
    public String calculateHash(int nonce, String seedNodeID, int index) {
        String calculatedhash = StringUtil.applySha256( 
                seedNodeID +
                Integer.toString(index) +
                Integer.toString(nonce)
                );
        return calculatedhash;
    }

    public Node getRandomNearbyNode(Node seedNode){
        Random r = new Random();
        if(seedNode.nearbyNodes.size() == 1){ //if genesis node or only 1 adjacent node for some reason
            return seedNode.nearbyNodes.get(0);
        }

        //Return random node from seedNode nearby nodes
        return seedNode.nearbyNodes.get(r.nextInt(seedNode.nearbyNodes.size()));
    }

    //When new blockchain is longer than current, and valid, switch to new blockchain
    public void switchBlockchain(Blockchain newChain){
        if(newChain.validateChain() && newChain.blockChain.size() > currentBlockchain.blockChain.size()){
            currentBlockchain = newChain;
        }
    }

    //When transaction is recieved from wallet, send to nearbyNodes
    public void floodTransaction(Transaction tx){
        //Perform checks
        if(!isTransactionOnBlockchain(tx)){
            if(!areOutputsSpent(tx)){
                if(!isTransactionAlreadySeen(tx)){
                    //Add to this nodes transactions
                    allTransactions.add(tx);

                    //If enough transaction are present to mine a block do so
                    if(allTransactions.size() == BLOCK_SIZE){
                        //Create a new block using the hash from the last block on the current blockchain
                        Block blockToAdd = new Block(currentBlockchain.blockChain.get(currentBlockchain.blockChain.size()-1).hash);

                        //For each transaction this node has seen, add it to the block
                        for (Transaction transaction : allTransactions) {
                            blockToAdd.addTransaction(transaction);
                        }
                        

                        //Mine the block and add it to the current blockchain
                        if (blockToAdd.mineBlock(Blockchain.DIFFICULTY)){
                            currentBlockchain.blockChain.add(blockToAdd);
                        }
                        
                        //Remove transactions in block from other nodes transaction list
                        for(Iterator<Transaction> iterator = allTransactions.iterator();iterator.hasNext();){
                            Transaction t = iterator.next();
                            iterator.remove();
                        }
                        
                            
                    }

                    //Relay to other nodes
                    for (Node node : nearbyNodes) {
                        node.floodTransaction(tx);
                    }
                }
            }
        }
    }

    //Check if transaction is valid by looking at inputs and outputs
    private boolean isTransactionOnBlockchain(Transaction tx){
        if(currentBlockchain.containsTransaction(tx)){
            return true;
        }
        return false;
    }

    //Check if transaction outputs have already been spent
    private boolean areOutputsSpent(Transaction tx){
        //Need to see if tx inputs are contained in the blockchain UTXOs
        for(TransactionInput i : tx.inputs){
            if(!currentBlockchain.UTXOs.containsKey(i.previousOutId)){
                return true;
            }
        }
        return false;
    }

    //Check if transaction has already been seen by this node
    private boolean isTransactionAlreadySeen(Transaction tx){
        return allTransactions.contains(tx);
    }
}