/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
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
            new Thread(new Connexion(server,port)).start();
        } catch (UnknownHostException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
}

class Connexion implements Runnable {
    private final int port;
    private final ServerSocket serverSocket;
    public ArrayList<Node> node = new ArrayList<>();

    @Override
    public void run() {
        while (true){
            try {
                new Thread(new ConnexionSocket(serverSocket.accept(), port,node)).start();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
    
    public Connexion(ServerSocket s, int ServerPort){
        serverSocket = s;
        port = ServerPort;
    }
}
