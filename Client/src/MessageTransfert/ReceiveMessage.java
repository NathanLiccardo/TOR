/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageTransfert;

import Message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import Node.Node;
import java.util.ArrayList;

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
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
        return null;
    }
    
    public Message receiveMessage() {
        try {
            return (Message) input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
        return null;
    }
    
}
