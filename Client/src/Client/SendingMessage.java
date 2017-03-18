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
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        create.setMessage(msg);
        create.creation();
        message = create.getMessage();
        new Thread(new Send(message)).start();
    }
    
    public SendingMessage(Node c,ArrayList<Node> n) {
        client = c;
        create = new CreateMessage(client);
        circuit = new Circuit(n);
        circuit.createCircuit();
    }
}

class Send implements Runnable {
    
    private Socket socket;
    private SendMessage sm;
    private final Node node;
    private final Message message;
    
    private void initSocket() {
        try {
            socket = new Socket(node.getIp(),node.getPort());
        } catch (IOException ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void sendMessage() {
        try {
            sm = new SendMessage(socket);
        } catch (IOException ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
        }
        sm.sendMessage(message);
    }
    
    public Send(Message m){ 
        message = m;
        node = m.getNode(); 
    }

    @Override
    public void run() {
        initSocket();
        sendMessage();
        closeSocket();
    }
}
