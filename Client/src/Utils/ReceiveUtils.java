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
import Model.NodeModel;
import java.util.ArrayList;

/**
 *
 * @author Nathan
 */
public class ReceiveUtils {
    private ObjectInputStream input = null;
    
    public ReceiveUtils(Socket socket) throws IOException{
        input = new ObjectInputStream(socket.getInputStream());
    }
    
    public ArrayList<NodeModel> receiveNodeList(){
        try {
            return (ArrayList<NodeModel>) input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
        return null;
    }
    
    public MessageModel receiveMessage() {
        try {
            return (MessageModel) input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
        return null;
    }
    
}
