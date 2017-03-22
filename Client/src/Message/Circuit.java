/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message;

import Cryptography.CryptMessage;
import MessageTransfert.SendMessage;
import Node.Node;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author Nathan
 */
public class Circuit {
    
    private final ArrayList<Node> nodes;
    private ArrayList<Node> circuit;
    private ArrayList<SecretKey> keys;
    private Message message;
    private final CryptMessage crypt;
    private SendMessage sendMessage;
    private Socket socket;
    
    private static final int SIZE = 3;
    private int COUNTER = 5;
    
    public Circuit(ArrayList<Node> n) {
        nodes = n;
        message = new Message(null,null,null);
        crypt = new CryptMessage();
    }
    
    public void createCircuit() {
        chooseNodes();
        createKey();
        createMessageSecretKeys();
        sendKeys();
    }
    
    public ArrayList<Node> getCircuit() {
        return circuit;
    }
    
    public ArrayList<SecretKey> getSecrets() {
        return keys;
    }
    
    public SendMessage getConnection() {
        return sendMessage;
    }
    
    public void check() {
        COUNTER--;
        if (COUNTER == 0) {
            closeSocket();
            createCircuit();
        }
    }
    
    private void chooseNodes(){
        Random rand = new Random();
        circuit = new ArrayList<>();
        int[] values = new int[SIZE];
        
        for (int i=0;i<SIZE;i++) 
            values[i] = rand.nextInt(nodes.size());
        while (values[0] == values[1]) 
            values[1] = rand.nextInt(nodes.size());
        while (values[2] == values[0] || values[1] == values[2])
            values[2] = rand.nextInt(nodes.size());
        
        for (int i=0;i<SIZE;i++) circuit.add(nodes.get(values[i]));
    }
    
    private void createKey() {
        keys = new ArrayList<>();
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            for (int i=0;i<SIZE; i++) {
                SecretKey secretKey = keyGen.generateKey();
                keys.add(secretKey);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Circuit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createMessageSecretKeys() {
        byte[] byteMessage = null;
        Node node = null;
        for (int i=0; i<SIZE; i++){
            message.setMessage(byteMessage);
            message.setKey(SerializationUtils.serialize(keys.get(i)));
            message.setNode(node);
            message.setNum(i);
            node = nodes.get(i);
            crypt.setValues(message, node);
            byteMessage = crypt.crypt(false);
        }
        message.setMessage(byteMessage);
        message.setKey(null);
        message.setNode(nodes.get(SIZE-1));
    }
    
    private void initSocket(Node node) {
        try {
            socket = new Socket(node.getIp(),node.getPort());
            sendMessage = new SendMessage(socket);
        } catch (IOException ex) {
            Logger.getLogger(Circuit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Circuit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sendKeys() {
        initSocket(message.getNode());
        sendMessage.sendMessage(message);
    }
    
}
