/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.CryptographyController;
import Utils.SerializationUtils;
import Utils.SendUtils;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import javax.crypto.KeyGenerator;
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
            System.err.println(ex);
        }
    }
    
    // -> ERROR MESSAGE
    private void createMessageSecretKeys() {
        byte[] byteMessage = null;
        keys.add(SIZE, null); nodes.add(0, null);
        message = new MessageModel(byteMessage, SerializationUtils.serialize((keys.get(0))), nodes.get(0), 0);
        String encodedKey = Base64.getEncoder().encodeToString(keys.get(0).getEncoded());
        System.out.println("Key : "+encodedKey+" ("+0+")");
        for (int i=1; i<SIZE+1; i++){
            crypt.setValues(message, nodes.get(i));
            message = new MessageModel(crypt.crypt(false), SerializationUtils.serialize(keys.get(i)), nodes.get(i), i);
            encodedKey = null;
            if (i != SIZE) encodedKey = Base64.getEncoder().encodeToString(keys.get(i).getEncoded());
            if (i != SIZE) System.out.println("Key : "+encodedKey+" ("+i+")");
        }
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
        initSocket(message.getNode());
        sendMessage.sendObject(message);
    }
    
    public void createCircuit() {
        chooseNodes();
        createKey();
        createMessageSecretKeys();
        sendKeys();
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
        message = new MessageModel(null,null,null,0);
        crypt = new CryptographyController();
    }
    
}
