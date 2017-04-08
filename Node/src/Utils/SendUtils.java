/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Model.MessageModel;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan
 */
public class SendUtils {
    ObjectOutputStream output = null;
    
    public SendUtils(Socket socket) throws IOException{
        output = new ObjectOutputStream(socket.getOutputStream());
        output.flush();
    }
    
    public void sendInt(int message) {
        try {
            output.writeInt(message);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(SendUtils.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void sendMessage(MessageModel message) {
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(SendUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendKey(PublicKey key) {
        try {
            output.writeObject(key);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(SendUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
