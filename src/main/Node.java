package main;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

class Node{
    //Id of this node
    public String id;
    public final int NEARBY_NODE_SIZE = 10;

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
        }
        
        //Add itself to the nearby nodes list
        nearbyNodes.add(this);

        //Assign node ID
        this.id = calculateHash(0, seedNode.id, index);

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

    //public void switchBlockchain
    //When new blockchain is longer than current, and valid
    //switch to new blockchain


    /* When transaction recieved
     * 1. Check not already seen using transactionID
     * 2. Check is valid on current blockchain
     * 3. Check outputs haven't already been spent
     * 
     * Then relay transaction to all other nodes
     */
}