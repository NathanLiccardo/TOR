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
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author Nathan
 */
public class CryptMessage {
    private Key _key;
    private Node _node;
    private byte[] _message;
    private SecretKey _secretKey;
    
    private final int SIZE1 = 53;
    private final int SIZE2 = 128;
    
    private Cipher initCipher (String text, boolean symetric) {
        Cipher cipher = null;
        try{
            cipher = Cipher.getInstance(text);
            if (symetric) cipher.init(Cipher.ENCRYPT_MODE, _secretKey);
            else cipher.init(Cipher.ENCRYPT_MODE, _key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            System.err.println(ex);
        }
        return cipher;
    }
    
    public byte[] crypt(boolean symetric) {
        String texte = (symetric) ? "AES/ECB/PKCS5Padding" : "RSA";
        Cipher cipher = this.initCipher(texte, symetric);
        int size = symetric ? SIZE2 : SIZE1;
        
        ByteArray tmp = new ByteArray();
        ByteArray result = new ByteArray();
        
        for (int i = 0; i < (_message.length/size); i++){
            tmp.copyOfRange(_message,i*size,(i+1)*size);
            tmp.action(cipher);
            result.concatenateByteArrays(tmp.getArray());
        }
        if (_message.length%size!=0){
            tmp.copyOfRange(_message, (_message.length/size)*size, _message.length);
            tmp.action(cipher);
            result.concatenateByteArrays(tmp.getArray());
        }
        return result.getArray();
    }
    
    public void setValues(Message m, Node n) {
        _message = SerializationUtils.serialize(m);
        _node = n;
        _key = _node.getKey();
    }
    
    public void setValues(Message m, SecretKey k) {
        _message = SerializationUtils.serialize(m);
        _secretKey = k;
    }
   
}
