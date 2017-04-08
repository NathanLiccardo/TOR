/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.PublicKey;

/**
 *
 * @author Nathan
 */
public class ReceiveUtils {
    private ObjectInputStream input = null;
    
    public ReceiveUtils(Socket socket) {
        try {
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public int receiveInt(){
        int value = 0;
        try {
            value =  input.readInt();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return value;
    }
    
    public PublicKey receiveKey(){
        PublicKey key = null;
        try {
            key = (PublicKey) input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
        return key;
    }
    
}
