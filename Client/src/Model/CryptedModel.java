/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 *
 * @author Nathan
 */
public class CryptedModel {
    private byte[] _array;
    
    public void concatenateByteArrays(byte[] a) {
        byte[] result = new byte[_array.length + a.length]; 
        System.arraycopy(_array, 0, result, 0, _array.length); 
        System.arraycopy(a, 0, result, _array.length, a.length); 
        _array = result;
    }
    
    public void copyOfRange(byte[] a, int start, int end) {
        _array = Arrays.copyOfRange(a, start, end);
    }
    
    public void action(Cipher cipher) {
        try {
            _array = cipher.doFinal(_array);
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            System.err.println(ex);
        }
    }
    
    public byte[] getArray() {
        return _array;
    }
    
    public CryptedModel() {
        _array = new byte[0];
    }
    
}
