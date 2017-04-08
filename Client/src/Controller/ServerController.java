package Controller;

import Utils.SendUtils;
import Utils.ReceiveUtils;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import Model.NodeModel;

/**
 * @author Nathan
 * Récupère la liste des noeuds sur le serveur
 */
public class ServerController {
    private final ArrayList<NodeModel> _nodes;
    
    public ArrayList<NodeModel> getNodes(){
        return _nodes;
    }
    
    public ServerController() throws IOException{
        try (Socket connect = new Socket("localhost", 2000)) {
            new SendUtils(connect).sendInt(0);
            _nodes = new ReceiveUtils(connect).receiveNodeList();
            System.out.println(_nodes);
        }
    }
}
