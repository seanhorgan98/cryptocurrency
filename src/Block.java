import java.time.Instant;

class Block {
    private Long timeStamp;
    private String data;
    public int index;
    public String previousHash;
    public String hash;
    private int nonce;

    public Block(int index, String previousHash, String data){
        this.index = index;
        this.timeStamp = Instant.now().getEpochSecond();
        this.previousHash = previousHash;
        this.data = data;
        this.hash = calculateHash();
    }

    public void addTransaction(){

    }

    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256( 
                previousHash +
                Long.toString(timeStamp) +
                Integer.toString(nonce) +
                data 
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

}