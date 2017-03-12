/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import GUI.MainFrame;
import MessageTransfert.ReceiveMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan
 */
public class WaitingMessage extends Thread{
    
    private final ServerSocket server;
    private Boolean isRunning = true;
    private MainFrame gui;
    
    public WaitingMessage(InetAddress add, int p, MainFrame mf) throws IOException{
        server = new ServerSocket(p, 100, add); 
        gui = mf;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread t1 = new Thread(new Receive(server.accept(), gui));
                t1.start();
            } catch (IOException ex) {
                Logger.getLogger(WaitingMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void stopThread() { isRunning = false; }
    
}

class Receive implements Runnable {
    
    private ReceiveMessage rm;
    private String msg;
    private MainFrame gui;
    
    private void printMessage() {
        System.out.println("Message reçu : ");
        System.out.println(msg);
        System.out.println("Veuillez entrer votre message");
    }
    
    public Receive(Socket socket, MainFrame mf) throws IOException {
        rm = new ReceiveMessage(socket);
        gui = mf;
    }

    @Override
    public void run() {
        msg = new String(rm.receiveMessage().getMessage());
        gui.updateScrollPane("Reçu : \n"+msg);
        printMessage();
    }
    
    public String getMessage() { return msg; }
    
}
