package main;

import java.util.ArrayList;
import java.util.List;

class Node{
    //List of all transactions not in the blockchain
    public List<Transaction> allTransactions;
    
    //List of all other nodes
    public List<Something> allNodes;

    public Node(String seedNodeID){

    }


    /* When transaction recieved
     * 1. Check not already seen using transactionID
     * 2. Check is valid on blockchain
     * 3. Check outputs haven't already been spent
     * 
     * Then relay transaction to all other nodes
     */
}