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
import java.util.Base64;
import java.util.concurrent.BlockingQueue;
import javax.crypto.SecretKey;

/**
 *
 * @author nathan
 */
public class ReceptionThread implements Runnable{
    private Socket _socket;
    private final Key _key;
    private final ServerSocket _server;
    private final BlockingQueue<Message> _blockingQueue;
    
    @Override
    public void run() {
        while (true) {
            try {
                _socket = _server.accept();
            } catch (IOException ex) {
                System.err.println(ex);
            }
            try {
                new Thread(new Reception(_socket,_blockingQueue,_key)).start();
            } catch (IOException | InterruptedException ex) {
                System.err.println(ex);
            }
        }
    }
    
    public ReceptionThread(int Lport, InetAddress Laddress, BlockingQueue<Message> queue, Key k) throws IOException{
        _key = k;
        _blockingQueue = queue;
        _server = new ServerSocket(Lport, 100,Laddress);
    }
}

class Reception implements Runnable {
    private final Key _key;
    private Message _message;
    private SecretKey _secretKey;
    private final Socket _socket;
    private final ReceiveMessage _rm;
    private final DecryptMessage _decryption;
    private final BlockingQueue<Message> _queue;
    
    private int COUNTER = 5;
    
    private void addQueue() {
        try { 
            _queue.put(_message);
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }
    
    private void getMessage(boolean secret) {
        _message = _rm.receiveMessage();
        if (secret) _decryption.setValues(_message.getMessage(), _secretKey);
        else _decryption.setValues(_message.getMessage(), _key);
        _message = _decryption.decrypt(secret);
    }
    
    private void getKey() {
        this.getMessage(false);
        _secretKey = (SecretKey) SerializationUtils.deserialize(_message.getKey());
        _message.setKey(null);
        this.sendNext();
    }
    
    private void receiveMessage() {
        this.getMessage(true);
        this.sendNext();
        COUNTER--;
    }
    
    private void sendNext(){
        if (_message.getNode() != null) 
            addQueue();
    }
    
    private void closeSocket(){
        try {
            _socket.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void run() {
        while (COUNTER > 0) receiveMessage();
        closeSocket();
    }
    
    public Reception(Socket s, BlockingQueue<Message> bQueue,Key k) throws IOException, InterruptedException{
        _key = k;
        _socket = s;
        _queue = bQueue;
        _rm = new ReceiveMessage(_socket);
        _decryption = new DecryptMessage();
        this.getKey();
    }
    
}
