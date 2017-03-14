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

/**
 *
 * @author Nathan
 */
public class DecryptMessage {
    
    private byte[] message;
    private Key key;
    
    private final int SIZE;
    
    private Cipher initCipher() {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA"); // create conversion processing object
            cipher.init(Cipher.DECRYPT_MODE, key); // initialize object's mode and key
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cipher;
    }
    
    public Message decrypt(){
        ByteArray tmp = new ByteArray();
        ByteArray result = new ByteArray();
        Cipher cipher = initCipher();
        
        int n = ((message.length%SIZE)==0) ? message.length/SIZE : (message.length/SIZE)+1;
        
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
    
    public DecryptMessage(byte[] m, Key k) {
        SIZE = 256;
        message = m;
        key = k;
    }
    
    public DecryptMessage(){
        SIZE = 256;
    }
    
}
