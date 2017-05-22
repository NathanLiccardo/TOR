/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.CryptographyController;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 *
 * @author Nathan
 */
public class CryptedModel {
    
    private byte[] array;
    
    public CryptedModel(byte[] a) {
        array = a;
    }
    
    public CryptedModel() {
        array = new byte[0];
    }
    
    public void concatenateByteArrays(byte[] a) {
        byte[] result = new byte[array.length + a.length]; 
        System.arraycopy(array, 0, result, 0, array.length); 
        System.arraycopy(a, 0, result, array.length, a.length); 
        array = result;
    }
    
    public void copyOfRange(byte[] a, int start, int end) {
        System.out.println(start);
        System.out.println(end);
        array = Arrays.copyOfRange(a, start, end);
    }
    
    public void decryption(Cipher cipher) {
        try {
            array = cipher.doFinal(array);
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(CryptographyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setArray(byte[] a){
        array = a;
    }
    
    public byte[] getArray() {
        return array;
    }
    
}
