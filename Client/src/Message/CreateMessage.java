/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message;

import Cryptography.CryptMessage;
import Node.Node;
import java.util.ArrayList;
/**
 *
 * @author Nathan
 */
public class CreateMessage {
    private ArrayList<Node> nodes;
    private final Node client;
    private final CryptMessage cryptage;
    private Message message;
    
    private static int SIZE = 3;
    
    public void creation(){
        for (int i=0; i < SIZE; i++) {
            cryptage.setValues(message, nodes.get(i));
            message.setMessage(cryptage.crypt());
            message.setNode(nodes.get(i));
        }
    }
    
    public void setMessage(String m) {
        message.setMessage(m.getBytes());
        message.setKey(null);
        message.setNode(client);
    }
    
    public Message getMessage() {
        return message;
    }
    
    public void setNodes(ArrayList<Node> array) {
        nodes = array;
    }
    
    public CreateMessage(Node c) {
        client = c;
        cryptage = new CryptMessage();
    }  
}
