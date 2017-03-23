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
import java.util.Scanner;

/**
 *
 * @author Nathan
 */
public class InitSystem {
    
    private int port;
    
    private int getPort(String text) {
        System.out.println(text);
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }
    
    private void initThreads() throws IOException {
        port = this.getPort("Sur quel port souhaitez-vous être visible ?");
        int portLocal = this.getPort("Avec quel port voulez-vous communiquer ?");
        Node client = new Node(InetAddress.getByName("localhost"),portLocal);
        ConnexionServer connexion = new ConnexionServer();
        SendingMessage send = new SendingMessage(client,connexion.getNodes());
        new Thread(new WaitingMessage(InetAddress.getByName("localhost"),port,new MainFrame(send))).start();
    }
    
    public InitSystem() throws IOException {
        this.port = 0;
        this.initThreads();
    }
    
}
