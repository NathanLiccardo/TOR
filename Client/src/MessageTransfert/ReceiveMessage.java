/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageTransfert;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import Node.Node;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan
 */
public class ReceiveMessage {
    private ObjectInputStream input = null;
    
    public ReceiveMessage(Socket socket) throws IOException{
        input = new ObjectInputStream(socket.getInputStream());
    }
    
    public ArrayList<Node> receiveNodeList(){
        try {
            return (ArrayList<Node>) input.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ReceiveMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReceiveMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Message receiveMessage() {
        try {
            return (Message) input.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ReceiveMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReceiveMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
