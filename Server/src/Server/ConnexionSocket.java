/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.net.Socket;
import java.util.ArrayList;
import MessageTransfert.ReceiveMessage;
import MessageTransfert.SendMessage;
import java.io.IOException;
import Node.Node;
import java.security.Key;


/**
 *
 * @author Nathan
 */
public class ConnexionSocket implements Runnable{
    private Socket client; 
    private ArrayList<Node> node;
    private ReceiveMessage rm;
    private SendMessage sm;
    private int ServerPort;
    
    public ConnexionSocket(Socket C, int SPort, ArrayList<Node> N) throws IOException{
        System.out.print("Client connecté : ");
        client = C;
        node = N;
        ServerPort = SPort;
        rm = new ReceiveMessage(client);
        sm = new SendMessage(client);
    }

    @Override
    public void run() {
        int type = rm.receiveInt();
        if (type == 0) { // Client
            System.out.println("Client connecté");
            sm.sendNodeList(node);
        } else { // Noeud
            System.out.println("Noeud connecté");
            int nodePort = ServerPort+node.size()+1;
            node.add(new Node(client,nodePort,rm.receiveKey()));
            sm.sendInt(nodePort);
        }
    }
      
}
