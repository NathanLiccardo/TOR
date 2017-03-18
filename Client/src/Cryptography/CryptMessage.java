/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cryptography;

import Message.Message;
import Message.SerializationUtils;
import Node.Node;
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
public class CryptMessage {
    
    private byte[] message;
    private Node node;
    private Key key;
    
    private final int SIZE = 245;
    
    public CryptMessage(byte[] m,Node n) {
        message = m;
        node = n;
        key = node.getKey();
    }
    
    public CryptMessage(){}
    
    private Cipher initCipherAsymetric() {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            Logger.getLogger(CryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cipher;
    }
    
    // Create symetric crypt
    private Cipher initCipherSymetric() {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            Logger.getLogger(CryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cipher;
    }
    
    public byte[] crypt(boolean symetric) {
        ByteArray tmp = new ByteArray();
        ByteArray result = new ByteArray();
        Cipher cipher = symetric ? this.initCipherSymetric() : this.initCipherAsymetric();
        
        int n = (message.length/SIZE) + ((message.length%SIZE!=0) ? 1 : 0);
        
        for (int i = 0; i < n; i++){
            int start = i*SIZE;
            int end = (i+1)*SIZE;
            tmp.copyOfRange(message,start,end);
            tmp.encryption(cipher);
            result.concatenateByteArrays(tmp.getArray());
        }
        if (message.length%SIZE!=0){
            tmp.copyOfRange(message, (n-1)*SIZE, message.length);
            tmp.encryption(cipher);
            result.concatenateByteArrays(tmp.getArray());
        }
        return result.getArray();
    }
    
    public void setValues(Message m, Node n){
        message = SerializationUtils.serialize(m);
        node = n;
        key = node.getKey();
    }
   
}
