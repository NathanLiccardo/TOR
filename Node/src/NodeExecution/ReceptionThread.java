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
import MessageTransfert.SendMessage;
import Node.Node;
import java.security.Key;
import javax.crypto.SecretKey;

/**
 *
 * @author nathan
 */
public class ReceptionThread implements Runnable{
    private Socket _socket;
    private final Key _key;
    private ServerSocket _server;
    
    @Override
    public void run() {
        while (true) {
            try {
                _socket = _server.accept();
            } catch (IOException ex) {
                System.err.println(ex);
            }
            try {
                new Thread(new Reception(_socket,_key)).start();
            } catch (IOException | InterruptedException ex) {
                System.err.println(ex);
            }
        }
    }
    
    public ReceptionThread(int Lport, InetAddress Laddress, Key k){
        _key = k;
        try {
            _server = new ServerSocket(Lport, 100,Laddress);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}

class Reception implements Runnable {
    private final Key _key;
    private Node _nextNode;
    private Message _message;
    private SecretKey _secretKey;
    private final Socket _socket;
    private SendMessage _sendMessage;
    private final DecryptMessage _decryption;
    private final ReceiveMessage _receiveMessage;
    
    private int COUNTER = 5;
    
    private void openCommunication() {
        Socket socket = null;
        try {
            socket = new Socket(_nextNode.getIp(),_nextNode.getPort());
        } catch (IOException ex) {
            System.err.println(ex);
        }
        try {
            _sendMessage = new SendMessage(socket);
        } catch (IOException ex) { 
            System.err.println(ex); 
        }
    }
    
    private void getMessage() {
        _message = _receiveMessage.receiveMessage();
        _decryption.setValues(_message.getMessage(), _secretKey);
        _message = _decryption.decrypt(true);
    }
    
    private void getKey() {
        _message = _receiveMessage.receiveMessage();
        System.out.print(_message.getNum());
        _decryption.setValues(_message.getMessage(), _key);
        _message = _decryption.decrypt(false);
        _secretKey = (SecretKey) SerializationUtils.deserialize(_message.getKey());
        _message.setKey(null);
        _nextNode = _message.getNode();
        sendNext();
    }
    
    private void receiveMessage() {
        this.getMessage();
        System.out.print("Message Received");
        this.sendNext();
        COUNTER--;
    }
    
    private void sendNext(){
        if (_message.getNode() != null) {
            _nextNode = _message.getNode();
            if (_sendMessage == null) openCommunication();
            _sendMessage.sendMessage(_message);
            System.out.println(" -> Message Sended");
        } else {
            System.out.println(" -> Last Node");
        }
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
    
    public Reception(Socket s, Key k) throws IOException, InterruptedException{
        _key = k;
        _socket = s;
        _sendMessage = null;
        _receiveMessage = new ReceiveMessage(_socket);
        _decryption = new DecryptMessage();
        this.getKey();
    }
    
}
