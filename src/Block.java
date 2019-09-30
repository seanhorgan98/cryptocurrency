import java.time.Instant;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

class Block {
    public final int index;
    public final Long timeStamp;
    public final String previousHash;
    public final String data;
    public final String hash;

    public Block(int index, Long timeStamp, String previousHash, String data){
        this.index = index;
        this.timeStamp = timeStamp;
        this.previousHash = previousHash;
        this.data = data;
        this.hash = getSHAString(Integer.toString(index) + previousHash + timeStamp.toString() + data);;
    }

    public void addTransaction(){

    }

    private static String getSHAString(String input){
        BigInteger number = new BigInteger(1, getSHA(input));
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while(hexString.length() < 32){
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    private static byte[] getSHA(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8)); 
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No Such algorithm");
            return null;
        }
        
    }

}