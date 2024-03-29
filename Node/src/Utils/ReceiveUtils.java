/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Model.MessageModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan
 */
public class ReceiveUtils {
    private ObjectInputStream input = null;
    
    public ReceiveUtils(Socket socket) throws IOException{
        input = new ObjectInputStream(socket.getInputStream());
    }
    
    public int receiveInt(){
        try {
            return input.readInt();
        } catch (IOException ex) {
            Logger.getLogger(ReceiveUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public String receiveString(){
        try {
            return (String) input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ReceiveUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public MessageModel receiveMessage(){
        try {
            return (MessageModel) input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ReceiveUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
