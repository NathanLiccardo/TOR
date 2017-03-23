/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Message.Circuit;
import Message.CreateMessage;
import Message.Message;
import MessageTransfert.SendMessage;
import Node.Node;
import java.util.ArrayList;
/**
 *
 * @author Nathan
 */
public class SendingMessage{
    
    private final Node client;
    
    private Message message;
    private final CreateMessage create;
    private final Circuit circuit;
    
    public void createMessage(String msg) {
        circuit.check();
        create.setNodes(circuit.getCircuit());
        create.creation(msg,circuit.getSecrets());
        message = create.getMessage();
        new Thread(new Send(message,circuit.getConnection())).start();
    }
    
    public SendingMessage(Node c,ArrayList<Node> n) {
        client = c;
        create = new CreateMessage(client);
        circuit = new Circuit(n);
        circuit.createCircuit();
    }
}

class Send implements Runnable {
    
    private SendMessage sm;
    private final Message message;
    
    private void sendMessage() {
        sm.sendMessage(message);
    }
    
    public Send(Message m, SendMessage send){ 
        message = m;
        sm = send;
    }

    @Override
    public void run() {
        sendMessage();
    }
}
