package com.example.encryptionlab;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.imageio.ImageIO;

public class desImageStartingCodeFlipped {
    public static void main(String[] args) throws Exception{
        int image_width = 200;
        int image_length = 200;

        String fileName = "SUTD.bmp";

        // read image file and save pixel value into int[][] imageArray
        BufferedImage img = ImageIO.read(new File(fileName));
        image_width = img.getWidth();
        image_length = img.getHeight();

        // byte[][] flipped_imageArray = new byte[image_width][image_length];
        int[][] flipped_imageArray = new int[image_width][image_length];
        for(int idx = 0; idx < image_width; idx++) {
            for(int idy = 0; idy < image_length; idy++) {
                int color = img.getRGB(idx, idy);
                flipped_imageArray[idx][image_length - 1 - idy] = color;
            }
        }

        // TODO: generate secret key using DES algorithm
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        SecretKey desKey = keyGen.generateKey();

        // TODO: Create cipher object, initialize the ciphers with the given key, choose encryption algorithm/mode/padding,
        //you need to try both ECB and CBC mode, use PKCS5Padding padding method
        Cipher desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        desCipher.init(Cipher.ENCRYPT_MODE, desKey);

        // define output BufferedImage, set size and format
        BufferedImage outImage = new BufferedImage(image_width,image_length, BufferedImage.TYPE_3BYTE_BGR);

        for(int idx = 0; idx < image_width; idx++) {
            // convert each column int[] into a byte[] (each_width_pixel)
            byte[] each_width_pixel = new byte[4*image_length];
            for(int idy = 0; idy < image_length; idy++) { // iterating down a column of pixels
                ByteBuffer dbuf = ByteBuffer.allocate(4); // allocate a 4-byte buffer
                dbuf.putInt(flipped_imageArray[idx][idy]); // put a 3-byte integer number into the 4-byte buffer (a pixel is 24-bits)
                byte[] bytes = dbuf.array();
                System.arraycopy(bytes, 0, each_width_pixel, idy*4, 4); // Copy all 4 bytes in 'bytes' to destination: which is each_width_pixel; at its index idy*4.
            } // Note that all encryption is done in blocks of 64-bits (8 bytes). So every 2 adjacent pixels (down a column) is mapped to a block in 'each_width_pixel'.
            // This block will be the smallest unit of input into the encryption algorithm.

            // TODO: encrypt each column or row bytes
            byte[] byteArray = desCipher.doFinal(each_width_pixel);

            // TODO: convert the encrypted byte[] back into int[] and write to outImage (use setRGB)
            IntBuffer intBuf =
                    ByteBuffer.wrap(byteArray)
                            .order(ByteOrder.BIG_ENDIAN)
                            .asIntBuffer();
            int[] intarray = new int[intBuf.remaining()];
            intBuf.get(intarray);

            for(int idy = 0; idy < image_length; idy++) {
                outImage.setRGB(idx, idy, intarray[image_length - 1 - idy]);
            }

        }
        //write outImage into file
        ImageIO.write(outImage, "BMP",new File("CBC_bottomup_SUTD.bmp"));
    }
}
