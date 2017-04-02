/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CryptedModel;
import Model.MessageModel;
import Utils.SerializationUtils;
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
public class CryptographyController {
    
    private Key _key;
    private int _size;
    private Cipher _cipher;
    private byte[] _message;
    private SecretKey _secretKey;
    
    private final int _SIZERSA = 64;
    private final int _SIZEAES = 144;
    
    private void initCipher(String mode) {
        _cipher = null;
        try {
            _cipher = Cipher.getInstance(mode);
            if ("RSA".equals(mode)) _cipher.init(Cipher.DECRYPT_MODE, _key);
            else  _cipher.init(Cipher.DECRYPT_MODE, _secretKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            System.err.println(ex);
        }
    }
    
    public MessageModel decrypt(boolean symetric){
        _size = (symetric) ? _SIZEAES : _SIZERSA;
        if (symetric) this.initCipher("AES/ECB/PKCS5Padding");
        else this.initCipher("RSA");
        int n = (_message.length/_size) + (_message.length%_size == 0 ? 0 : 1);
       
        CryptedModel tmp = new CryptedModel();
        CryptedModel result = new CryptedModel();
        
        for (int i = 0; i < n; i++){
            int start = i*_size;
            int end = (i != n-1) ? (i+1)*_size : _message.length;
            tmp.copyOfRange(_message, start, end);
            tmp.decryption(_cipher);
            result.concatenateByteArrays(tmp.getArray());
        }
        return (MessageModel) SerializationUtils.deserialize(result.getArray());
    }
    
    public void setValues(byte[] m, Key n){
        _message = m;
        _key = n;
    }
    
    public void setValues(byte[] m, SecretKey n) {
        _message = m;
        _secretKey = n;
    }
    
    public CryptographyController(byte[] m, Key k) {
        _message = m;
        _key = k;
    }
    
    public CryptographyController(){}
    
}
