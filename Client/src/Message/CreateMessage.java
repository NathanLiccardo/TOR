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
    private Node _client;
    private Message _message;
    private ArrayList<Node> _nodes;
    private ArrayList<SecretKey> _key;
    private final CryptMessage _cryptage;
    
    private final int SIZE = 3;
    
    public void creation(String m, ArrayList<SecretKey> s){
        _key = s;
        _message = new Message(m.getBytes(),null,_client,0);
        for (int i=0; i < SIZE; i++) {
            _cryptage.setValues(_message, _key.get(i));
            _message.setMessage(_cryptage.crypt(true));
            _message.setNode(_nodes.get(i));
        }
    }
    
    public Message getMessage() {
        return _message;
    }
    
    public void setNodes(ArrayList<Node> array) {
        _nodes = array;
    }
    
    public CreateMessage(Node c) {
        _client = c;
        _cryptage = new CryptMessage();
        _message = new Message(null,null,null,0);
    }  
}
