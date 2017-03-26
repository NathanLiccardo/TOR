/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Node;

import Cryptography.PrivateKeyReader;
import Cryptography.PublicKeyReader;
import NodeExecution.ReceptionThread;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Key;

/**
 *
 * @author Nathan
 */
public class Main {
    private static Key _publicKey;
    private static Key _privateKey;
    private static InetAddress _address;

    /**
     * @param args
     * @throws java.net.UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException, IOException{
        System.out.println(args[0]);
        _address = InetAddress.getByName(args[0]);
        _privateKey = new PrivateKeyReader().get(args[1]);
        _publicKey = new PublicKeyReader().get(args[2]);
        ConnexionServer connexion = new ConnexionServer(_publicKey);
        new Thread(new ReceptionThread(connexion.getNumber(),_address,_privateKey)).start();
    }
    
}
