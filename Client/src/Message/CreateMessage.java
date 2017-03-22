/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message;

import Cryptography.CryptMessage;
import Node.Node;
import java.util.ArrayList;
import javax.crypto.SecretKey;
/**
 *
 * @author Nathan
 */
public class CreateMessage {
    private ArrayList<SecretKey> key;
    private Message message;
    private Node client;
    private ArrayList<Node> nodes;
    private final CryptMessage cryptage;
    
    private final int SIZE = 3;
    
    public void creation(String m, ArrayList<SecretKey> s){
        key = s;
        message.setKey(m.getBytes());
        message.setKey(null);
        message.setNode(client);
        for (int i=0; i < SIZE; i++) {
            cryptage.setValues(message, key.get(i));
            message.setMessage(cryptage.crypt(true));
            message.setNode(nodes.get(i));
        }
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
        message = new Message(null,null,null);
    }  
}
