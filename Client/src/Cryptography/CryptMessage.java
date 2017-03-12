/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cryptography;

import Node.Node;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author Nathan
 */
public class CryptMessage {
    
    private byte[] message;
    private Node node;
    private Key key;
    
    private int SIZECONSTANT = 245;
    
    private Cipher initCipherAsymetric() {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA"); // create conversion processing object
            cipher.init(Cipher.ENCRYPT_MODE, key); // initialize object's mode and key
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            Logger.getLogger(CryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cipher;
    }
    private Cipher initCipherSymetric() {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES"); // create conversion processing object
            cipher.init(Cipher.ENCRYPT_MODE, key); // initialize object's mode and key
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            Logger.getLogger(CryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cipher;
    }
    private byte[] encryption(byte[] tmp, Cipher cipher) {
        try {
            tmp = cipher.doFinal(tmp);
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(CryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmp;
    }
    private byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length]; 
        System.arraycopy(a, 0, result, 0, a.length); 
        System.arraycopy(b, 0, result, a.length, b.length); 
        return result;
    }
    
    public byte[] crypt() {
        byte[] tmp;
        byte[] result = new byte[0];
        Cipher cipher = initCipherAsymetric();
        
        int n = message.length/SIZECONSTANT;
        if (message.length % SIZECONSTANT != 0) n += 1;
        for (int i = 0; i < n; i++){
            if (i != n-1) tmp = Arrays.copyOfRange(message, i*SIZECONSTANT, (i+1)*SIZECONSTANT);
            else tmp = Arrays.copyOfRange(message, i*SIZECONSTANT, message.length);
            tmp = encryption(tmp,cipher);
            result = concatenateByteArrays(result,tmp);
        }
        return result;
    }
    
    public void setValues(byte[] m, Node n){
        message = m;
        node = n;
        key = node.getKey();
    }
    
    public CryptMessage(byte[] m,Node n) {
        message = m;
        node = n;
        key = node.getKey();
    }
    public CryptMessage(byte[] m,SecretKey secret) {
        message = m;
        key = secret;
    }
    
    public CryptMessage(){}
}
