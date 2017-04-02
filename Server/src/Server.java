/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Controller.ConnectionController;


import Model.NodeModel;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan
 */
public class Server implements Runnable{
    
    private final int port = 2000;
    private final String host = "localhost";
    private ServerSocket server = null;
    public ArrayList<NodeModel> node = new ArrayList<>();
    
    public void initServer() {
        try {
            server = new ServerSocket(port, 100,InetAddress.getByName(host));
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        this.initServer();
        while (true){
            try {
                new Thread(new ConnectionController(server.accept(), port,node)).start();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
    
    public static void main(String args[]) throws Exception{
        new Thread(new Server()).start();
    }
    
}
