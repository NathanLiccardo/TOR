/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import GUI.MainFrame;
import Message.Message;
import MessageTransfert.ReceiveMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Nathan
 */
public class WaitingMessage extends Thread{
    private ServerSocket _server;
    private final MainFrame _mainFrame;
    
    public WaitingMessage(InetAddress add, int port, MainFrame mainFrame){
        _mainFrame = mainFrame;
        try { 
            _server = new ServerSocket(port, 100, add);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    @Override
    public void run() { 
        while (true) {
            try {
                new Thread(new Receive(_server.accept(), _mainFrame)).start();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
    
}

class Receive implements Runnable {
    private String _message;
    private MainFrame _mainFrame;
    private ReceiveMessage _receiveMessage;
    
    public Receive(Socket socket, MainFrame mainFrame) {
        _mainFrame = mainFrame;
        try {
            _receiveMessage = new ReceiveMessage(socket);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    @Override
    public void run() {
        Message message = _receiveMessage.receiveMessage();
        byte[] byteArray = message.getMessage();
        _message = new String(byteArray);
        _mainFrame.updateScrollPane(_message);
    }
}
