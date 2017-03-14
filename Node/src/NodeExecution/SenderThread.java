/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NodeExecution;

import Cryptography.DecryptMessage;
import Message.Message;
import MessageTransfert.SendMessage;
import Node.Node;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.Key;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author nathan
 */
public class SenderThread implements Runnable{
    private Boolean isRunning;
    private BlockingQueue queue;
    Key key;
    
    public SenderThread(BlockingQueue bQueue, Key privateK){
        isRunning = true;
        queue = bQueue;
        key = privateK;
    }
    
    @Override
    public void run() {
        while (isRunning){
            try {
                Thread t1 = new Thread(new Sender((Message) queue.take()));
                t1.start();
            } catch (InterruptedException ex) {
                System.out.println("Err : reception message");
            }
        }    
    }
    
    public void endTask(){
        isRunning = false;
    }
    
}

class Sender implements Runnable{
    private final Node next;
    private Message nextM;
    private Socket socket;
    SendMessage sm;
    
    private void initSocket() {
        try {
            socket = new Socket(next.getIp(),next.getPort());
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sendMessage() {
        try {
            sm = new SendMessage(socket);
            sm.sendMessage(nextM);
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        initSocket();
        sendMessage();
        closeSocket();
        System.out.println("Envoyé : ");
        System.out.println(nextM);
    }
    
    public Sender(Message message) {
        next = nextM.getNode();
        nextM = message;
    }
}
