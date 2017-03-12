/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import Node.Node;
import java.util.ArrayList;

/**
 *
 * @author Nathan
 */
public class ListenSocket {
    private int port = 2000;
    private String host = "localhost";
    private ServerSocket server = null;
    
    public ListenSocket(){
        try {
            server = new ServerSocket(port, 100,InetAddress.getByName(host));
            Thread start = new Thread(new Connexion(server,port));
            start.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

class Connexion implements Runnable {
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    public ArrayList<Node> node = new ArrayList<Node>();
    public Thread cl;
    private int port;
    
    // Création du serveur
    public Connexion(ServerSocket s, int ServerPort){
        serverSocket = s;
        port = ServerPort;
    }

    @Override
    public void run() {
        while (true){
            try {
                socket = serverSocket.accept();
                cl = new Thread(new ConnexionSocket(socket, port,node));
                cl.start();
            } catch (IOException ex) {
                System.out.println("Erreur : création du thread");
            }
        }
    }
}
