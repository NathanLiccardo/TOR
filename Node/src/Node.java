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
import java.security.PrivateKey;
import java.security.PublicKey;

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
    public static void main(String[] args) throws UnknownHostException, IOException{
        System.out.println(args[0]);
        _address = InetAddress.getByName(args[0]);
        _privateKey = new PrivateKeyController().get(args[1]);
        _publicKey = new PublicKeyController().get(args[2]);
        ServerController connexion = new ServerController(_publicKey);
        new Thread(new ReceptionController(connexion.getNumber(),_address,_privateKey)).start();
    }
    
}
