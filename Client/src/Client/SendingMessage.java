/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import MessageTransfert.CreateMessage;
import MessageTransfert.Message;
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
    private final ArrayList<Node> nodes;
    private int counter;
    
    private Message message;
    private CreateMessage create;
    
    public void createMessage(String msg) {
        create.setMessage(msg);
        message = create.creation(counter);
        counter--;
        if (counter == 0) counter = 5;
    }
    public void startThreads() {
        Thread t = new Thread(new Send(message));
        t.start();
    }
    
    public SendingMessage(Node c,ArrayList<Node> n) {
        client = c;
        nodes = n;
        counter = 5;
        create = new CreateMessage(client,nodes);
    }
}

// TO DO : envoyer les clés tous les 5 messages !
class Send implements Runnable {
    
    private Node node;
    private Message message;
    private Socket socket;
    private SendMessage sm;
    
    private void initSocket() {
        try {
            socket = new Socket(node.getIp(),node.getPort());
        } catch (IOException ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void sendMessage() {
        sm = null;
        try {
            sm = new SendMessage(socket);
        } catch (IOException ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
        }
        sm.sendMessage(message);
    }
    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
        }
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
