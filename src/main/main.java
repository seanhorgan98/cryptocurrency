/**
 * Main Class used for testing the blockchain and blocks.
 */

class Main{
    public static void main(String[] args) {
        System.out.println("---------------OUTPUT------------------");

        Wallet walletA = new Wallet();
        Wallet walletB = new Wallet();

        System.out.println("Private and public keys:");
		System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
		System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
        
        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
        transaction.generateSignature(walletA.privateKey);
        
        System.out.println("Is signature verified?");
		System.out.println(transaction.verifiySignature());

/*
        //Add Block 1
        Block genisisBlock = new Block(0, "0", "Genisis Data!");
        Blockchain blockChain = new Blockchain(genisisBlock);

        //Add block 2
        Block secondBlock = new Block(1, genisisBlock.hash, "Second block data!");
        blockChain.addBlock(secondBlock);

        //Add block 3
        Block thirdBlock = new Block(2, secondBlock.hash, "Third block data!");
        blockChain.addBlock(thirdBlock);

        
        
        System.out.println("Mining block 0...");
        blockChain.blockChain.get(0).mineBlock(2);

        blockChain.printBlockchain();
        System.out.println("Valid blockchain: " + blockChain.validateChain());
*/
    }
}


