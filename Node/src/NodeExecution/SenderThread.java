/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NodeExecution;

import Message.Message;
import MessageTransfert.SendMessage;
import Node.Node;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;


/**
 *
 * @author nathan
 */
public class SenderThread implements Runnable{
    private Boolean isRunning;
    private BlockingQueue<Message> queue;
    
    public SenderThread(BlockingQueue<Message> bQueue){
        isRunning = true;
        queue = bQueue;
    }
    
    private void startThread() {
        while (isRunning) {
            try { new Thread(new Sender((Message) queue.take())).start(); }
            catch (InterruptedException ex) { System.err.println(ex); }
        }
    }
    
    @Override
    public void run() {
        startThread(); 
    }
    
    public void endTask(){
        isRunning = false;
    }
    
}

class Sender implements Runnable{
    private final Message message;
    private final Node nextNode;
    private Socket socket;
    SendMessage sm;
    
    private void send() {
        try { 
            socket = new Socket(nextNode.getIp(),nextNode.getPort()); 
        } catch (IOException ex) { 
            System.err.println(ex); 
        } 
        try {
            sm = new SendMessage(socket);
        } catch (IOException ex) { 
            System.err.println(ex); 
        }
        sm.sendMessage(message);
    }
    
    @Override
    public void run() {
        send();
    }
    
    public Sender(Message m) {
        nextNode = m.getNode();
        message = m;
    }
}
