/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageTransfert;

import Message.Message;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan
 */
public class SendMessage {
    ObjectOutputStream output = null;
    
    public SendMessage(Socket socket) throws IOException{
        output = new ObjectOutputStream(socket.getOutputStream());
        output.flush();
    }
    
    public void sendInt(int message) {
        try {
            output.writeInt(message);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void sendMessage(Message message) {
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendKey(Key key) {
        try {
            output.writeObject(key);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
