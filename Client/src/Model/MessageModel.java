/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.crypto.SealedObject;

/**
 *
 * @author Nathan
 */
public class MessageModel implements Serializable{
    private int _val;
    private NodeModel _next;
    private byte[] _key;
    private byte[] _message;
    private SealedObject _messageS;
    
    public MessageModel(byte[] m, byte[] k, NodeModel n, int num) {
        _key = k;
        _next = n;
        _val = num;
        _message = m;
    }
    
    public MessageModel(SealedObject m, byte[] k, NodeModel n, int num) {
        _key = k;
        _next = n;
        _val = num;
        _messageS = m;
    }
    
    public byte[] getMessage() {
        return _message;
    }
    public SealedObject getMessageS() {
        return _messageS;
    }
    public NodeModel getNode() {
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
    public void setMessage(SealedObject m) {
        _messageS = m;
    }
    public void setNode(NodeModel n) {
        _next = n;
    }
    public void setKey(byte[] k) {
        _key = k;
    }
    public void setNum(int num) {
        _val = num;
    }
    
}
