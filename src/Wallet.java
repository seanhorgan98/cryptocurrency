import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Wallet {
	
	public PrivateKey privateKey;
	public PublicKey publicKey;
	
	public Wallet(){
		generateKeyPair();	
	}
		
	public void generateKeyPair() {
		try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(new ECGenParameterSpec("secp256r1"), new SecureRandom());
            KeyPair keyPair = keyGen.generateKeyPair();
            
            // Set the public and private keys from the keyPair
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}