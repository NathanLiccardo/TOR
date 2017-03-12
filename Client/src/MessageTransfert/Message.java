/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageTransfert;

import Node.Node;
import java.io.Serializable;

/**
 *
 * @author Nathan
 */
public class Message implements Serializable{
    
    private byte[] message;
    private Node next;
    
    public Message(byte[] m, Node n) {
        message = m;
        next = n;
    }
    
    public byte[] getMessage() {
        return message;
    }
    
    public void setMessage(byte[] m) {
        message = m;
    }
    
    public Node getNode() {
        return next;
    }
    
    public void setNode(Node n) {
        next = n;
    }
    
}
