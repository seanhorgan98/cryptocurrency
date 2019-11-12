import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class Block {
    private Long timeStamp;
    private String merkleRoot;
    public int index;
    public String previousHash;
    public String hash;
    private int nonce;
    public List<Transaction> transactions;

    public Block(int index, String previousHash){
        this.index = index;
        this.timeStamp = Instant.now().getEpochSecond();
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }

    //Will need to redo the merkle tree after adding
    public void addTransaction(Transaction t){
        transactions.add(t);
    }

    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256( 
                previousHash +
                Long.toString(timeStamp) +
                Integer.toString(nonce)
                );
        return calculatedhash;
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); 
        String newHash = hash;
		while(!(newHash.substring( 0, difficulty).equals(target))) {
            nonce ++;
			newHash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + newHash);
    }
    
    public void buildMerkleTree(){
        List<String> tree = new ArrayList<String>();

        //Create tree of hashes
        for (Transaction t : transactions){
            tree.add(t.transactionHash);
        }

        List<String> newTxList = getNewTxList(tree);
        while (newTxList.size() != 1) {
            newTxList = getNewTxList(newTxList);
        }
    
        this.merkleRoot = newTxList.get(0);
    }

    private List<String> getNewTxList(List<String> tempTxList){
        List<String> newTxList = new ArrayList<String>();
        int index = 0;
        while (index < tempTxList.size()){
            //left
            String left = tempTxList.get(index);
            index++;

            //right
            String right = "";
            if (index != tempTxList.size()){
                right = tempTxList.get(index);
            }

            //hash
            newTxList.add(StringUtil.applySha256(left + right));
            index++;
        }
        return newTxList;

    }

    public String getMerkleRoot(){
        return this.merkleRoot;
    }
}