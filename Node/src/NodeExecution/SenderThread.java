/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NodeExecution;

import Cryptography.DecryptMessage;
import MessageTransfert.Message;
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
                Thread t1 = new Thread(new Sender((Message) queue.take(), key));
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
    
    private Node next;
    private Message nextM;
    private Socket socket;
    private Key key;
    
    
    public Sender(Message message, Key k) {
        key = k;
        try {
            byte[] msg = (new DecryptMessage(message.getMessage(),key)).decrypt();
            nextM = (Message) deserialize(msg);
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        next = nextM.getNode();
    }
    
    public Message deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return (Message) is.readObject();
    }
    
    @Override
    public void run() {
        SendMessage sm;
        try {
            socket = new Socket(next.getIp(),next.getPort());
            sm = new SendMessage(socket);
            sm.sendMessage(nextM);
            System.out.println("Envoyé : ");
            System.out.println(nextM);
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
