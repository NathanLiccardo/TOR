package Controller;

import Model.CircuitModel;
import Model.NodeModel;
import java.util.ArrayList;
/**
 *
 * @author Nathan
 */
public class SenderController{
    private final CircuitModel _circuit;
    private final NodeModel _client;
    
    public void createMessage(String msg) {
        _circuit.createCircuit();
        _circuit.check();
        _circuit.getConnection().sendObject(new MessageController().creation(_client,msg,_circuit.getSecrets(),_circuit.getCircuit()));
    }
    
    public SenderController(NodeModel client,ArrayList<NodeModel> nodes) {
        _client = client;
        _circuit = new CircuitModel(nodes);
    }
}
