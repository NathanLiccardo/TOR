/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Nathan
 */
public class SendUtils {
    ObjectOutputStream output = null;
    
    public SendUtils(Socket socket) throws IOException{
        output = new ObjectOutputStream(socket.getOutputStream());
        output.flush();
    }
    
    public void sendInt(int message){
        try {
            output.writeInt(message);
            output.flush();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public void sendObject(Object message) {
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
