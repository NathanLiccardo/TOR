package Controller;

import java.security.PublicKey;

/**
 *
 * @author nathan
 */
public class PublicKeyController {

    PublicKey _public;
    
    public PublicKeyController(PublicKey publicKey) {
        _public = publicKey;
    }
    
    public PublicKey get(){
        return _public;
    }
}
