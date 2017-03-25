package Client;

import Message.Circuit;
import Message.CreateMessage;
import Node.Node;
import java.util.ArrayList;
/**
 *
 * @author Nathan
 */
public class SendingMessage{
    private final Circuit _circuit;
    private final CreateMessage _create;
    
    public void createMessage(String msg) {
        _circuit.check();
        _create.setNodes(_circuit.getCircuit());
        _create.creation(msg,_circuit.getSecrets());
        _circuit.getConnection().sendMessage(_create.getMessage());
    }
    public SendingMessage(Node client,ArrayList<Node> nodes) {
        _create = new CreateMessage(client);
        _circuit = new Circuit(nodes);
        _circuit.createCircuit();
    }
}
