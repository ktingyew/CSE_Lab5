package com.example.encryptionlab;

import java.util.Base64;
import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.*;

//desStartingCode.java, prolly the desTextStartingCode
public class digitalSignatureStartingCode {

    public static void main(String[] args) throws Exception {
    //Read the text file and save to String data
        String fileName = "C:\\Users\\kting\\AndroidStudioProjects\\CSELab5\\EncryptionLab\\src\\main\\java\\com\\example\\encryptionlab\\longtext.txt";;
        String data = "";
        String line;
        BufferedReader bufferedReader = new BufferedReader( new FileReader(fileName));
        while((line= bufferedReader.readLine())!=null){
            data = data +"\n" + line;
        }
        System.out.println("Original content: "+ data);

        //TODO: generate a RSA keypair, initialize as 1024 bits, get public key and private key from this keypair.
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair keyPair = keyGen.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();

        //TODO: Calculate message digest, using MD5 hash function
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data.getBytes());

        //TODO: print the length of output digest byte[], compare the length of file smallSize.txt and largeSize.txt
        byte[] bytedigest = md.digest();
        System.out.println(bytedigest.length); // confirm is 16 bytes, or 128 bits.

        //TODO: Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as encrypt mode, use PRIVATE key.
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, privateKey);

        //TODO: encrypt digest message
        byte[] encrypted_digest = rsaCipher.doFinal(bytedigest);

        //TODO: print the encrypted message (in base64format String using DatatypeConverter)
        String encrypted_text = Base64.getEncoder().encodeToString(encrypted_digest);
        System.out.println(encrypted_text);

        //TODO: Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as decrypt mode, use PUBLIC key.
        Cipher public_rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        public_rsaCipher.init(Cipher.DECRYPT_MODE, publicKey);

        //TODO: decrypt message
        byte[] decrypted_digest = public_rsaCipher.doFinal(encrypted_digest);

        //TODO: print the decrypted message (in base64format String using DatatypeConverter), compare with origin digest
        System.out.println(Base64.getEncoder().encodeToString(decrypted_digest));
        System.out.println(Base64.getEncoder().encodeToString(bytedigest));

    }

}