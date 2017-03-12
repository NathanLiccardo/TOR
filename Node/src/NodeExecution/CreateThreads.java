/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NodeExecution;

import Message.Message;
import java.io.IOException;
import java.net.InetAddress;
import java.security.Key;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author nathan
 */
public class CreateThreads {
    private int idNode;
    private InetAddress localAddress;
    private ReceptionThread receive;
    private SenderThread send;
    private Thread reception = null;
    private Thread sender = null;
    private BlockingQueue<Message> queue;
    
    private static int MAXCONNECTION = 1000;
    
    public CreateThreads(int port, InetAddress address, Key k) throws IOException{
        idNode = port;
        localAddress = address;
        queue = new ArrayBlockingQueue<Message>(MAXCONNECTION);
        receive = new ReceptionThread(idNode,localAddress,queue);
        send = new SenderThread(queue,k);
        reception = new Thread(receive);
        sender = new Thread(send);
    }
    
    public void startThreads(){
        reception.start();
        sender.start();
    }
    
    public void stopThread(){
        receive.endTask();
        send.endTask();
    }
    
}
