package com.example.encryptionlab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.crypto.*;
import java.util.Base64;

//digitalSignatureStartingCode.java
public class DigitalSignatureSolution {
    public static void main(String[] args) throws Exception {
        String fileName = "shorttext.txt";
        String data = "";
        String line;
        BufferedReader bufferedReader = new BufferedReader( new FileReader(fileName));
        while((line= bufferedReader.readLine())!=null){
            data = data +"\n" + line;
        }
        System.out.println("Original content: "+ data);

        //TODO: generate secret key using DES algorithm
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        SecretKey desKey = keyGen.generateKey();

        //TODO: create cipher object, initialize the ciphers with the given key, choose encryption mode as DES
        Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        //TODO: do encryption, by calling method Cipher.doFinal().
        desCipher.init(Cipher.ENCRYPT_MODE, desKey);
        byte[] ciphertextByteArray = desCipher.doFinal(data.getBytes());

        //TODO: print the length of output encrypted byte[], compare the length of file smallSize.txt and largeSize.txt
        // System.out.println(ciphertextByteArray.length);
        // System.out.println(new String(ciphertextByteArray)); // This is non-human readable.

        //TODO: do format conversion. Turn the encrypted byte[] format into base64format String using DatatypeConverter
        String ciphertext = Base64.getEncoder().encodeToString(ciphertextByteArray);

        //TODO: print the encrypted message (in base64format String format)
        // System.out.println(ciphertext); // This is human readable.

        //TODO: create cipher object, initialize the ciphers with the given key, choose decryption mode as DES
        Cipher desCipher_decrpyt = Cipher.getInstance("DES/ECB/PKCS5Padding");
        desCipher_decrpyt.init(Cipher.DECRYPT_MODE, desKey);

        //TODO: do decryption, by calling method Cipher.doFinal().
        byte[] plaintextByteArray = desCipher_decrpyt.doFinal(ciphertextByteArray);

        //TODO: do format conversion. Convert the decrypted byte[] to String, using "String a = new String(byte_array);"
        String plaintext = new String(plaintextByteArray);

        //TODO: print the decrypted String text and compare it with original text
        //System.out.println(plaintext);


    }
}