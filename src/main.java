/**
 * Main Class used for testing the blockchain and blocks.
 */

import Block.java;
import Blockchain.java;
import java.time.Instant;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

class Main{
    public static void main(String[] args) {
        System.out.println("-----------------------------------------");
        //1. Create Genisis Block
        //2. Create blockchain
        
        
        String hashInput = "Hello World";

        System.out.println("Input: " + hashInput);
        System.out.println("Output: " + getSHAString(hashInput));
    }

    public static String getSHAString(String input){
        BigInteger number = new BigInteger(1, getSHA(input));
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while(hexString.length() < 32){
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    public static byte[] getSHA(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8)); 
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No Such algorithm");
            return null;
        }
        
    }


}