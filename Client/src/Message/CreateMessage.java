/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message;

import Cryptography.CryptMessage;
import MessageTransfert.Serialize;
import Node.Node;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Nathan
 */
public class CreateMessage {
    private String message;
    private final ArrayList<Node> nodes;
    private final Node client;
    private final Serialize serialize;
    private final CryptMessage cryptage;
    private ArrayList<Node> next;
    
    private ArrayList<Node> chooseNodes(){
        Random rand = new Random();
        ArrayList<Node> result = new ArrayList<>(3);
        result.add(client);
        
        int[] values = new int[3];
        for (int i=0; i<3;i++) values[i] = (rand.nextInt(nodes.size()));
        while (values[0] == values[1]) values[1] = rand.nextInt(nodes.size());
        while (values[2] == values[0] || values[1] == values[2])
            values[2] = rand.nextInt(nodes.size());
        for (int i=0;i<3;i++) result.add(nodes.get(values[i]));
        
        return result;
    }
    private byte[] serialize(byte[] mBytes, Message m) {
        try {
            serialize.setMessage(m);
            mBytes = serialize.serialize();
        } catch (IOException ex) {
            Logger.getLogger(CreateMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mBytes;
    }
    private byte[] encrypt(byte[] message, Node client, Node next) {
        byte[] mBytes;
        Message m = new Message(message, null,client);
        cryptage.setValues(m, next);
        mBytes = cryptage.crypt();
        return mBytes;
    }
    
    public Message creation(){
        next = chooseNodes();
        byte[] mbytes = message.getBytes();
        for (int i=1; i < 4; i++) {
            Node n1 = next.get(i-1);
            Node n2 = next.get(i);
            mbytes = encrypt(mbytes,n1,n2);
        } 
        return new Message(mbytes, null, next.get(3));
    }
    
    public void setMessage(String m) {
        message = m;
    }
    
    public CreateMessage(Node c,ArrayList<Node> n) {
        nodes = n;
        client = c;
        serialize = new Serialize();
        cryptage = new CryptMessage();
    }  
}
