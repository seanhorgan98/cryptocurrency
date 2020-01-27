package main;

import java.util.ArrayList;
import java.util.List;

class Node{
    //List of all transactions not in the blockchain
    public List<Transaction> allTransactions;
    
    //List of all other nodes
    public List<Node> allNodes;

    //Copy of the current blockchain to use

    public Node(String seedNodeID, Blockchain blockchain){
        //getNodeList(seedNode)
        //Get list of all nodes from seedNode
        //repeat a few times with new nodes

        //Add itself to the list of nodes

        //Let all the other nodes know that it now exists

    }

    private List<Node> createNodeList(){
        //newList = getNodeList();
        return new ArrayList<Node>();
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