/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message;

import Node.Node;
import java.io.Serializable;

/**
 *
 * @author Nathan
 */
public class Message implements Serializable{
    
    private byte[] message;
    private byte[] key;
    private Node next;
    
    public Message(byte[] m, byte[] k, Node n) {
        message = m;
        key = k;
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
    
    public byte[] getKey(){
        return key;
    }
    
    public void setKey(byte[] k) {
        key = k;
    }
    
    private int _val = -1;
    public void setNum(int num) {
        _val = num;
    }
    public int getNum() {
        return _val;
    }
    
}
