/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import GUI.MainFrame;
import Node.Node;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan
 */
public class InitSystem {
    
    private int port = 0;
    
    public InitSystem() {}
    
    public void getPort() {
        System.out.println("Sur quel port voulez-vous être visible ?");
        Scanner in = new Scanner(System.in);
        port = in.nextInt();
    }
    
    public void initThreads() {
        try {
            ConnexionServer connexion = new ConnexionServer();
            SendingMessage send = new SendingMessage(getClient(),connexion.getNodes());
            MainFrame gui = new MainFrame(send);
            WaitingMessage wait = new WaitingMessage(InetAddress.getByName("localhost"),port,gui);
            Thread t1 = new Thread(wait);
            t1.start();
        } catch (IOException ex) {
            Logger.getLogger(InitSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Node getClient() {
        System.out.println("Avec quel port voulez-vous communiquer ?");
        Scanner in = new Scanner(System.in);
        int portLocal = in.nextInt();
        Node client = null;
        try {
            client = new Node(InetAddress.getByName("localhost"),portLocal);
        } catch (UnknownHostException ex) {
            System.out.println(ex);
        }
        return client;
    }
    
}
