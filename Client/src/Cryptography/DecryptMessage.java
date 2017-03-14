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
    private Node node;
    private Key key;
    
    private static int SIZE = 256;
    
    public DecryptMessage(byte[] m,Node n) {
        message = m;
        node = n;
        key = node.getKey();
    }
    
    public DecryptMessage(){}
    
    private Cipher initCipher() {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key); 
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cipher;
    }
    
    public byte[] decrypt(){
        ByteArray tmp = new ByteArray();
        ByteArray result = new ByteArray();
        Cipher cipher = initCipher();
        
        int n = message.length/SIZE;
        if (message.length % SIZE != 0) n += 1;
        
        for (int i = 0; i < n; i++){
            int start = i*SIZE;
            int end = (i != n-1) ? (i+1)*SIZE : message.length;
            tmp.copyOfRange(message, start, end);
            tmp.decryption(cipher);
            result.concatenateByteArrays(tmp.getArray());
        }
        return result.getArray();
    }
    
    public void setValues(byte[] m, Node n){
        message = m;
        node = n;
        key = node.getKey();
    }
    
}
