/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Controller.ServerController;

import Controller.PrivateKeyController;
import Controller.PublicKeyController;
import Controller.ReceptionController;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 *
 * @author Nathan
 */
public class Node {
    private static PublicKey _publicKey;
    private static PrivateKey _privateKey;
    private static InetAddress _address;

    /**
     * @param args
     * @throws java.net.UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException, IOException, NoSuchAlgorithmException{
        System.out.println(args[0]);
        final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        final KeyPair key = keyGen.generateKeyPair();
        _privateKey = new PrivateKeyController(key.getPrivate()).get();
        _publicKey = new PublicKeyController(key.getPublic()).get();
        String encodedKey2 = Base64.getEncoder().encodeToString(_publicKey.getEncoded());
        System.out.println("Key (public) : "+encodedKey2+" ("+0+")");
        _address = InetAddress.getByName(args[0]);
        //_privateKey = new PrivateKeyController().get(args[1]);
        //_publicKey = new PublicKeyController().get(args[2]);
        ServerController connexion = new ServerController(_publicKey);
        new Thread(new ReceptionController(connexion.getNumber()-1,_address,_privateKey)).start();
    }
    
}
