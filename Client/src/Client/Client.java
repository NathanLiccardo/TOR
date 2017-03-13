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

/**
 *
 * @author Nathan
 */
public class Client {
    
    private static int port;
    private static Thread t1;
    private static MainFrame gui;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        getPort();
        initThreads();
        startThreads();
    }
    
    /**
     * Obtient un port spécifique auprès du client
     */
    private static void getPort() {
        System.out.println("Sur quel port voulez-vous être visible ?");
        Scanner in = new Scanner(System.in);
        port = in.nextInt();
    }
    
    /**
     * Initialise les threads qui vont être utilisés
     * @throws IOException 
     */
    private static void initThreads() throws IOException {
        ConnexionServer connexion = new ConnexionServer();
        SendingMessage send = new SendingMessage(getClient(),connexion.getNodes());
        gui = new MainFrame(send);
        WaitingMessage wait = new WaitingMessage(InetAddress.getByName("localhost"),port,gui);
        t1 = new Thread(wait);
    }
    
    /**
     * Permet d'obtenir les informations concernant le destinataire
     * @return Node
     * @throws UnknownHostException 
     */
    private static Node getClient() throws UnknownHostException {
        System.out.println("Avec quel port voulez-vous communiquer ?");
        Scanner in = new Scanner(System.in);
        int portLocal = in.nextInt();
        return new Node(InetAddress.getByName("localhost"),portLocal);
    }
    
    /**
     * Démarre les threads
     */
    private static void startThreads() {
        t1.start();
    }
    
}
