package Client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import MessageTransfert.ReceiveMessage;
import MessageTransfert.SendMessage;
import Node.Node;

/**
 * @author Nathan
 * Récupère la liste des noeuds sur le serveur
 */
public class ConnexionServer {
    private final String _host;
    private final Socket _connect;
    private ArrayList<Node> _nodes;
    private final static int PORT = 2000;
    private final SendMessage _sendMessage;
    private final ReceiveMessage _receiveMessage;
    
    private void connection() throws IOException {
        _sendMessage.sendInt(0);
        _nodes = _receiveMessage.receiveNodeList();
        _connect.close();
    }
     
    public ArrayList<Node> getNodes(){
        return _nodes;
    }
    
    public ConnexionServer() throws IOException{
        _host = "localhost";
        _connect = new Socket(_host, PORT);
        _sendMessage = new SendMessage(_connect);
        _receiveMessage = new ReceiveMessage(_connect);
        this.connection();
    }
}
