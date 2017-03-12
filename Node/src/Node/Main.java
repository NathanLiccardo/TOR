/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Node;

import NodeExecution.CreateThreads;
import java.io.IOException;
import java.net.InetAddress;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan
 */
public class Main {
    
    private static InetAddress addres;
    private static Key privateK;
    private static Key publicK;
    
    private static KeyPairGenerator keyGen() {
        KeyPairGenerator generator = null;
        try { 
            generator = KeyPairGenerator.getInstance("RSA");
        } 
        catch (NoSuchAlgorithmException ex) { 
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return generator;
    }
    private static void initKey(KeyPairGenerator generator) {
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();
        privateK = keyPair.getPrivate();
        publicK = keyPair.getPublic();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        addres = InetAddress.getByName("localhost");
        initKey(keyGen());
        CreateThreads creation;
        
        ConnexionServer connexion = new ConnexionServer(publicK);
        creation = new CreateThreads(connexion.getNumber(),addres,privateK);
        creation.startThreads();
    }
    
}
