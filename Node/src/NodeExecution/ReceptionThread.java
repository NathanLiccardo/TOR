/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NodeExecution;

import Message.Message;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import MessageTransfert.ReceiveMessage;
import MessageTransfert.SendMessage;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nathan
 */
public class ReceptionThread implements Runnable{
    private Boolean isRunning;
    private int port;
    private InetAddress address;
    private ServerSocket server;
    private Socket socket;
    private Thread reception;
    private BlockingQueue<Message> blockingQueue;
    
    
    public ReceptionThread(int Lport, InetAddress Laddress, BlockingQueue<Message> queue) throws IOException{
        isRunning = true;
        port = Lport;
        address = Laddress;
        blockingQueue = queue;
        System.out.println(address);
        System.out.println(port);
        server = new ServerSocket(port, 100,address);
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                socket = server.accept();
                reception = new Thread(new Reception(socket,blockingQueue));
                reception.start();
            } catch (IOException ex) {
                System.out.println("Err : run");
            }
        }
    }
    
    public void endTask() {
        isRunning = false;
    }
}

class Reception implements Runnable {
    ReceiveMessage rm;
    SendMessage sm;
    Message message;
    BlockingQueue<Message> queue;
    
    public Reception(Socket s, BlockingQueue<Message> bQueue) throws IOException{
        rm = new ReceiveMessage(s);
        sm = new SendMessage(s);
        queue = bQueue;
    }

    @Override
    public void run() {
        message = rm.receiveMessage();
        System.out.println("Message reçu :");
        System.out.println(message);
        try {
            queue.put(message);
        } catch (InterruptedException ex) {
            Logger.getLogger(Reception.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
