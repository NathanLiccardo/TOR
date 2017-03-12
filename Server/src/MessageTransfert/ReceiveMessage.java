/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageTransfert;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan
 */
public class ReceiveMessage {
    private ObjectInputStream input = null;
    
    public ReceiveMessage(Socket socket) {
        try {
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ReceiveMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int receiveInt(){
        int value = 0;
        try {
            value =  input.readInt();
        } catch (IOException ex) {
            System.out.println("Erreur : lecture int");
        }
        return value;
    }
    
    public Key receiveKey(){
        Key key = null;
        try {
            key = (Key) input.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ReceiveMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReceiveMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return key;
    }
    
}
