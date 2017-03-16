/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NodeExecution;

import Cryptography.DecryptMessage;
import Message.Message;
import Message.SerializationUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import MessageTransfert.ReceiveMessage;
import java.security.Key;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;

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
    private Key key;
    
    private void accept() {
        try {
            socket = server.accept();
        } catch (IOException ex) {
            Logger.getLogger(ReceptionThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void startT() {
        try {
            reception = new Thread(new Reception(socket,blockingQueue,key));
            reception.start();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ReceptionThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        while (isRunning) {
            accept();
            startT();
        }
    }
    
    public void endTask() {
        isRunning = false;
    }
    
    public ReceptionThread(int Lport, InetAddress Laddress, BlockingQueue<Message> queue, Key k) throws IOException{
        isRunning = true;
        port = Lport;
        key = k;
        address = Laddress;
        blockingQueue = queue;
        server = new ServerSocket(port, 100,address);
    }
}

// Ajout du décryptage avec la clé AES

class Reception implements Runnable {
    ReceiveMessage rm;
    Message message;
    BlockingQueue<Message> queue;
    DecryptMessage decryption;
    SecretKey secretKey;
    Key key;
    Socket socket;
    
    private int COUNTER = 5;
    
    private void addQueue() {
        try {
            queue.put(message);
        } catch (InterruptedException ex) {
            Logger.getLogger(Reception.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getKey() {
        message = rm.receiveMessage();
        decryption.setValues(message.getMessage(), key);
        message = decryption.decrypt();
        secretKey = (SecretKey) SerializationUtils.deserialize(message.getKey());
        message.setKey(null);
    }
    
    private void getMessage() {
        message = rm.receiveMessage();
        COUNTER--;
    }
    
    private void sendNext(){
        if (message.getNode() != null) addQueue();
        System.out.println("OK");
    }
    
    private void closeSocket(){
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Reception.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (COUNTER > 0) {
            getMessage();
            addQueue();
        }
        closeSocket();
    }
    
    public Reception(Socket s, BlockingQueue<Message> bQueue,Key k) throws IOException, InterruptedException{
        key = k;
        queue = bQueue;
        rm = new ReceiveMessage(s);
        decryption = new DecryptMessage();
        socket = s;
        getKey();
        sendNext();
    }
    
}
