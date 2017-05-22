package Controller;

import java.security.PrivateKey;

/**
 *
 * @author nathan
 */
public class PrivateKeyController {
    
    PrivateKey _private;

    public PrivateKeyController(PrivateKey _private) {
        this._private = _private;
    }
    
    public PrivateKey get() {
        return _private;
    }
}
