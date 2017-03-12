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

/**
 *
 * @author Nathan
 */
public class DecryptMessage {
    
    private byte[] message;
    private Key key;
    
    private int SIZECONSTANT = 256;
    
    private Cipher initCipher() {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA"); // create conversion processing object
            cipher.init(Cipher.DECRYPT_MODE, key); // initialize object's mode and key
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cipher;
    } 
    private byte[] decryption(byte[] tmp, Cipher cipher) {
        byte[] decryptedByteData = null;
        try {
            decryptedByteData = cipher.doFinal(tmp); // use object for decryption
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return decryptedByteData;
    }
    private byte[] concatenateByteArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length]; 
        System.arraycopy(a, 0, result, 0, a.length); 
        System.arraycopy(b, 0, result, a.length, b.length); 
        return result;
    }
    
    public byte[] decrypt(){
        byte[] tmp;
        byte[] result = new byte[0];
        Cipher cipher = initCipher();
        
        int n = message.length/SIZECONSTANT;
        if (message.length % SIZECONSTANT != 0) n += 1;
        for (int i = 0; i < n; i++){
            if (i != n-1) tmp = Arrays.copyOfRange(message, i*SIZECONSTANT, (i+1)*SIZECONSTANT);
            else tmp = Arrays.copyOfRange(message, i*SIZECONSTANT, message.length);
            result = concatenateByteArrays(result, decryption(tmp,cipher));
        }
        return result;
    }
    
    public void setValues(byte[] m, Key n){
        message = m;
        key = n;
    }
    
    public DecryptMessage(byte[] m, Key k) {
        message = m;
        key = k;
    }
    
    public DecryptMessage(){}
    
}
