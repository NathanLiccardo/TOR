/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageTransfert;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import Node.Node;
import java.util.ArrayList;
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
    
    public void sendNodeList(ArrayList<Node> nodeList){
        try {
            output.writeObject(nodeList);
            output.flush();
        } catch (IOException ex) {
            System.out.println("Erreur : écriture liste noeud");
        }
    }
    
    public void sendInt(int message){
        try {
            output.writeInt(message);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
