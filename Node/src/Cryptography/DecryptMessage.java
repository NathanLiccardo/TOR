/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cryptography;

import Message.Message;
import Message.SerializationUtils;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author Nathan
 */
public class DecryptMessage {
    
    private byte[] message;
    private Key key;
    private SecretKey secretKey;
    
    private final int SIZE1 = 256;
    private final int SIZE2 = 128;
    
    private Cipher initCipherAsymetric() {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key); 
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cipher;
    }
    private Cipher initCipherSymetric() {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES"); 
            cipher.init(Cipher.DECRYPT_MODE, secretKey); 
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cipher;
    }
    
    public Message decrypt(boolean symetric){
        int SIZE = (symetric) ? SIZE2 : SIZE1;
        Cipher cipher = (symetric) ? initCipherSymetric() : initCipherAsymetric();
        int n = (message.length/SIZE) + (message.length%SIZE == 0 ? 0 : 1);
        
        ByteArray tmp = new ByteArray();
        ByteArray result = new ByteArray();
        
        for (int i = 0; i < n; i++){
            int start = i*SIZE;
            int end = (i != n-1) ? (i+1)*SIZE : message.length;
            tmp.copyOfRange(message, start, end);
            tmp.decryption(cipher);
            result.concatenateByteArrays(tmp.getArray());
        }
        return (Message) SerializationUtils.deserialize(result.getArray());
    }
    
    public void setValues(byte[] m, Key n){
        message = m;
        key = n;
    }
    
    public void setValues(byte[] m, SecretKey n) {
        message = m;
        secretKey = n;
    }
    
    public DecryptMessage(byte[] m, Key k) {
        message = m;
        key = k;
    }
    
    public DecryptMessage(){}
    
}
