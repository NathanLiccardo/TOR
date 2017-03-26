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
    private int _val;
    private Node _next;
    private byte[] _key;
    private byte[] _message;
    
    public Message(byte[] m, byte[] k, Node n, int num) {
        _key = k;
        _next = n;
        _val = num;
        _message = m;
    }
    
    public byte[] getMessage() {
        return _message;
    }
    public Node getNode() {
        return _next;
    }
    public byte[] getKey(){
        return _key;
    }
    public int getNum() {
        return _val;
    }
    
    public void setMessage(byte[] m) {
        _message = m;
    }
    public void setNode(Node n) {
        _next = n;
    }
    public void setKey(byte[] k) {
        _key = k;
    }
    public void setNum(int num) {
        _val = num;
    }
    
}
