/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.Socket;
import java.util.ArrayList;
import Utils.ReceiveUtils;
import Utils.SendUtils;
import java.io.IOException;
import Model.NodeModel;


/**
 *
 * @author Nathan
 */
public class ConnectionController implements Runnable{
    private final Socket client;    
    private final int ServerPort;
    private final SendUtils sm;
    private final ReceiveUtils rm;
    private final ArrayList<NodeModel> node;
    
    public ConnectionController(Socket C, int SPort, ArrayList<NodeModel> N) throws IOException{
        client = C;
        node = N;
        ServerPort = SPort;
        rm = new ReceiveUtils(client);
        sm = new SendUtils(client);
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
            node.add(new NodeModel(client,nodePort,rm.receiveKey()));
            sm.sendInt(nodePort);
        }
    }
      
}
