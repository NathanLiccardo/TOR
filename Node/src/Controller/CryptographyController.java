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
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author Nathan
 */
public class CryptographyController {
    
    private PrivateKey _key;
    private int _size;
    private Cipher _cipher;
    private byte[] _message;
    private SecretKey _secretKey;
    
    private final int _SIZERSA = 128;
    private final int _SIZEAES = 144;
    
    private void initCipher(String mode) {
        _cipher = null;
        try {
            _cipher = Cipher.getInstance(mode);
            if ("RSA".equals(mode)) {
                _cipher.init(Cipher.DECRYPT_MODE, _key);
            } else {
                _cipher.init(Cipher.DECRYPT_MODE, _secretKey);
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            System.err.println(ex);
        }
    }
    
    public MessageModel decrypt(boolean symetric){
        _size = (symetric) ? _SIZEAES : _SIZERSA;
        if (symetric) this.initCipher("AES/ECB/PKCS5Padding");
        else this.initCipher("RSA");
        
        CryptedModel tmp = new CryptedModel();
        CryptedModel result = new CryptedModel();
        
        for (int i = 0; i < (_message.length/_size); i++){
            tmp.copyOfRange(_message,i*_size,(i+1)*_size);
            tmp.decryption(_cipher);
            result.concatenateByteArrays(tmp.getArray());
        }
        if (_message.length%_size!=0){
            tmp.copyOfRange(_message, (_message.length/_size)*_size, _message.length);
            tmp.decryption(_cipher);
            result.concatenateByteArrays(tmp.getArray());
        }
        return (MessageModel) SerializationUtils.deserialize(result.getArray());
    }
    
    public void setValues(byte[] m, PrivateKey n){
        _message = m;
        _key = n;
    }
    
    public void setValues(byte[] m, SecretKey n) {
        _message = m;
        _secretKey = n;
    }
    
    public CryptographyController(byte[] m, PrivateKey k) {
        _message = m;
        _key = k;
    }
    
    public CryptographyController(){}
    
}
