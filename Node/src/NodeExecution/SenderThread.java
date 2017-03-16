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
import java.util.logging.Level;
import java.util.logging.Logger;


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
        try {
            Thread t1 = new Thread(new Sender((Message) queue.take()));
            t1.start();
        } catch (InterruptedException ex) {
            Logger.getLogger(SenderThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        while (isRunning) startThread(); 
    }
    
    public void endTask(){
        isRunning = false;
    }
    
}

class Sender implements Runnable{
    private Message message;
    private Node nextNode;
    private Socket socket;
    SendMessage sm;
    
    private void initSocket() {
        try {
            socket = new Socket(nextNode.getIp(),nextNode.getPort());
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sendMessage() {
        try {
            sm = new SendMessage(socket);
            sm.sendMessage(message);
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        initSocket();
        sendMessage();
    }
    
    public Sender(Message m) {
        nextNode = m.getNode();
        message = m;
    }
}
