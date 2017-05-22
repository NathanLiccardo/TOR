/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.CryptographyController;
import Utils.SendUtils;
import Utils.SerializationUtils;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

/**
 *
 * @author Nathan
 */
public class CircuitModel {
    private Socket socket;
    private MessageModel message;
    private SendUtils sendMessage;
    private ArrayList<NodeModel> circuit;
    private final CryptographyController crypt;
    private ArrayList<SecretKey> keys;
    private final ArrayList<NodeModel> nodes;
    
    private int COUNTER = 4;
    private static final int SIZE = 3;
    
    private void chooseNodes(){
        circuit = new ArrayList<>();
        for (int i=0;i<SIZE;i++) circuit.add(nodes.get(i));
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
            System.err.println(ex);
        }
    }
    
    private void createMessageSecretKeys() {
        byte[] byteMessage = null;
        
        keys.add(SIZE, null); nodes.add(0, null);
        message = new MessageModel(byteMessage, SerializationUtils.serialize((keys.get(0))), nodes.get(0), 0);
        // 1
        crypt.setValues(message, nodes.get(1));
        message = new MessageModel(crypt.crypt(false), SerializationUtils.serialize(keys.get(1)), nodes.get(1), 1);
        // 2
        crypt.setValues(message, nodes.get(2));
        message = new MessageModel(crypt.crypt(false), SerializationUtils.serialize(keys.get(2)), nodes.get(2), 2);
        // 3
        crypt.setValues(message, nodes.get(3));
        message = new MessageModel(crypt.crypt(false), SerializationUtils.serialize(keys.get(3)), nodes.get(3), 3);
        
        keys.remove(SIZE); nodes.remove(0);
    }
    
    private void initSocket(NodeModel node) {
        try {
            socket = new Socket(node.getIp(),node.getPort());
            sendMessage = new SendUtils(socket);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    private void sendKeys() {
        System.out.println(message.getNum());
        System.out.println(message.getNode().getIp());
        System.out.println(message.getNode().getPort());
        initSocket(message.getNode());
        sendMessage.sendObject(message);
    }
    
    public void createCircuit() {
        chooseNodes();
        createKey();
        createMessageSecretKeys();
        sendKeys();
        System.out.println("OK creation");
    }
    
    public void check() {
        COUNTER--;
        if (COUNTER == 0) {
            closeSocket();
            createCircuit();
        }
    }
    
    public ArrayList<NodeModel> getCircuit() {
        return circuit;
    }
    
    public ArrayList<SecretKey> getSecrets() {
        return keys;
    }
    
    public SendUtils getConnection() {
        return sendMessage;
    }
    
    public CircuitModel(ArrayList<NodeModel> n) {
        System.out.println(n);
        nodes = n;
        SealedObject obj = null;
        message = new MessageModel(obj,null,null,0);
        crypt = new CryptographyController();
    }
    
}
