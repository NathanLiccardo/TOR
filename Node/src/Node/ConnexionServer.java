/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Node;

import MessageTransfert.ReceiveMessage;
import MessageTransfert.SendMessage;
import java.io.IOException;
import java.net.Socket;
import java.security.PublicKey;

/**
 *
 * @author Nathan
 */
public class ConnexionServer {
    private Socket connect = null;
    private final String HOST = "localhost";
    private final int PORT = 2000;
    private final int number;
    private final ReceiveMessage rm;
    private final SendMessage sm;
    
    public ConnexionServer(PublicKey key) throws IOException {
        connect = new Socket(HOST, PORT);
        sm = new SendMessage(connect);
        rm = new ReceiveMessage(connect);
        sm.sendInt(1);
        sm.sendKey(key);
        number = rm.receiveInt();
        connect.close();
    }
    
    public int getNumber(){
        return number;
    }
    
}
