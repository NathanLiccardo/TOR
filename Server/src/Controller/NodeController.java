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
import Model.NodeModel;
import java.io.IOException;
import javax.net.ssl.SSLSocket;


/**
 *
 * @author Nathan
 */
public class NodeController implements Runnable{
    private final int _port;
    private final Socket _socket;
    private final SSLSocket _sslsocket;
    private final SendUtils _send;
    private final ReceiveUtils _receive;
    private final ArrayList<NodeModel> _nodes;
    
    private void clientConnected() {
        System.out.println("-> Client connecté");
        _send.sendNodeList(_nodes);
    }
    private void nodeConnected() {
        System.out.println("-> Noeud connecté");
        _nodes.add(new NodeModel(_socket,_port+_nodes.size()+1,_receive.receiveKey()));
        _send.sendInt(_port+_nodes.size()+1);
    }    

    @Override
    public void run() {
        int type = _receive.receiveInt();
        if (type == 0) clientConnected();
        else nodeConnected();
    }
    
    public NodeController(SSLSocket socket, int port, ArrayList<NodeModel> nodes) throws IOException {
        _sslsocket = socket;
        _socket = null;
        _port = port;
        _nodes = nodes;
        _receive = new ReceiveUtils(_sslsocket);
        _send = new SendUtils(_sslsocket);
    }
    
    public NodeController(Socket socket, int port, ArrayList<NodeModel> nodes) throws IOException{
        _socket = socket;
        _sslsocket = null;
        _port = port;
        _nodes = nodes;
        _receive = new ReceiveUtils(_socket);
        _send = new SendUtils(_socket);
    }
      
}
