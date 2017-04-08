/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Utils.ReceiveUtils;
import Utils.SendUtils;
import java.io.IOException;
import java.net.Socket;
import java.security.PublicKey;

/**
 *
 * @author Nathan
 */
public class ServerController {
    private Socket connect = null;
    private final String HOST = "localhost";
    private final int PORT = 2000;
    private final int number;
    private final ReceiveUtils rm;
    private final SendUtils sm;
    
    public ServerController(PublicKey key) throws IOException {
        connect = new Socket(HOST, PORT);
        sm = new SendUtils(connect);
        rm = new ReceiveUtils(connect);
        sm.sendInt(1);
        sm.sendKey(key);
        number = rm.receiveInt();
        connect.close();
    }
    
    public int getNumber(){
        return number;
    }
    
}
