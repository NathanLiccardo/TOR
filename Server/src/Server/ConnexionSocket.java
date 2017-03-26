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


/**
 *
 * @author Nathan
 */
public class ConnexionSocket implements Runnable{
    private final Socket client;    
    private final int ServerPort;
    private final SendMessage sm;
    private final ReceiveMessage rm;
    private final ArrayList<Node> node;
    
    public ConnexionSocket(Socket C, int SPort, ArrayList<Node> N) throws IOException{
        client = C;
        node = N;
        ServerPort = SPort;
        rm = new ReceiveMessage(client);
        sm = new SendMessage(client);
    }

    @Override
    public void run() {
        int type = rm.receiveInt();
        if (type == 0) {
            System.out.println("-> Client connecté");
            sm.sendNodeList(node);
        } else {
            System.out.println("-> Noeud connecté");
            int nodePort = ServerPort+node.size()+1;
            node.add(new Node(client,nodePort,rm.receiveKey()));
            sm.sendInt(nodePort);
        }
    }
      
}
