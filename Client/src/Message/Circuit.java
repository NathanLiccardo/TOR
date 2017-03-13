/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message;

import Cryptography.CryptMessage;
import Node.Node;
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
    private final ArrayList<Node> circuit;
    private final ArrayList<SecretKey> keys;
    private final Message message;
    private final CryptMessage crypt;
    
    private static final int SIZE = 3;
    private static final int COUNTER = 5;
    
    public Circuit(ArrayList<Node> n) {
        nodes = n;
        circuit = new ArrayList<>();
        keys = new ArrayList<>();
        message = new Message(null,null,null);
        crypt = new CryptMessage();
    }
    
    public void createCircuit() {
        chooseNodes();
        createKey();
        createMessageSecretKeys();
    }
    
    private void chooseNodes(){
        Random rand = new Random();
        int[] values = new int[SIZE];
        
        for (int i=0;i<SIZE;i++) 
            values[i] = (rand.nextInt(nodes.size()));
        while (values[0] == values[1]) 
            values[1] = rand.nextInt(nodes.size());
        while (values[2] == values[0] || values[1] == values[2])
            values[2] = rand.nextInt(nodes.size());
        
        for (int i=0;i<SIZE;i++) circuit.add(nodes.get(values[i]));
    }
    
    private void createKey() { 
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
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
            node = nodes.get(i);
            crypt.setValues(message, node);
            byteMessage = crypt.crypt();
        }
    }
    
}
