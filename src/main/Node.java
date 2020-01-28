package main;

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
        this.currentBlockchain = blockchain;

        //If not genesis node
        if(seedNode != null){
            //Get list of all nearby nodes from seedNode
            for (Node node : seedNode.getNearbyNodes()) {
                if(nearbyNodes.size() <= NEARBY_NODE_SIZE){
                    nearbyNodes.add(node);
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


    private List<Node> createNodeList(){
        //newList = getNodeList();
        return new ArrayList<Node>();
    }


    public List<Node> getNearbyNodes(){
        return nearbyNodes;
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