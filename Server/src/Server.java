
import Controller.NodeController;
import Model.NodeModel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nathan
 */
public class Server implements Runnable{
    private final int _port;
    private final String _host ;
    private ServerSocket _server;
    public ArrayList<NodeModel> _node;
    
    public void initServer() {
        try {
            _server = new ServerSocket(_port, 100,InetAddress.getByName(_host));
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
                new Thread(new NodeController(_server.accept(), _port,_node)).start();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
    /*
    private SSLServerSocket initServer() throws UnknownHostException,IOException {
        SSLController sslConnection = new SSLController(_port, 100, _host);
        sslConnection.connect();
        return sslConnection.getServer();
    }
    
    @Override
    public void run() {
        try {
            SSLServerSocket server = this.initServer();
            while (true) {
                SSLSocket socket = (SSLSocket) server.accept();
                new Thread(new NodeController(socket,_port, _node)).start();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }*/
    
    public Server(){
        _host = "localhost";
        _port = 2000;
        _node = new ArrayList<>();
    }
    
    public Server(String host, int port){
        _host = host;
        _port = port;
        _node = new ArrayList<>();
    }
    
    public static void main(String args[]) throws Exception{
        new Thread(new Server()).start();
    }
    
}
