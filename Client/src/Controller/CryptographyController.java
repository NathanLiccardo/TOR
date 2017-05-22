package Controller;

import Model.CryptedModel;
import Model.MessageModel;
import Utils.SerializationUtils;
import Model.NodeModel;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author Nathan
 */
public class CryptographyController {
    private PublicKey _key;
    private NodeModel _node;
    private byte[] _message;
    private SecretKey _secretKey;
    
    private final int SIZE1 = 100;
    private final int SIZE2 = 128;
    
    private Cipher initCipher (String text, boolean symetric) {
        Cipher cipher = null;
        try{
            cipher = Cipher.getInstance(text);
            if (symetric) { 
                cipher.init(Cipher.ENCRYPT_MODE, _secretKey);
            }
            else {
                cipher.init(Cipher.ENCRYPT_MODE, _key);
                String encodedKey = Base64.getEncoder().encodeToString(_key.getEncoded());
                System.out.println("Key : "+encodedKey+" ("+0+")");
            }
            System.out.println("OK");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            System.err.println(ex);
        }
        return cipher;
    }
    
    public byte[] crypt(boolean symetric) {
        String texte = (symetric) ? "AES/ECB/PKCS5Padding" : "RSA";
        Cipher cipher = this.initCipher(texte, symetric);
        int size = symetric ? SIZE2 : SIZE1;
        
        CryptedModel tmp = new CryptedModel();
        CryptedModel result = new CryptedModel();
        
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
    
    public void setValues(MessageModel m, NodeModel n) {
        _message = SerializationUtils.serialize(m);
        _node = n;
        _key = _node.getKey();
    }
    
    public void setValues(MessageModel m, SecretKey k) {
        _message = SerializationUtils.serialize(m);
        _secretKey = k;
    }
   
}
