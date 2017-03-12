/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageTransfert;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Nathan
 */
public class SendMessage {
    ObjectOutputStream output = null;
    
    public SendMessage(Socket socket) throws IOException{
        output = new ObjectOutputStream(socket.getOutputStream());
        output.flush();
    }
    
    public void sendInt(int message){
        try {
            output.writeInt(message);
            output.flush();
            System.out.println("Send ok");
        } catch (IOException ex) {
            System.out.println("Erreur écriture du int");
        }
    }

    public void sendString(String message) {
        try {
            output.writeObject(message);
            output.flush();
            System.out.println("Send ok");
        } catch (IOException ex) {
            System.out.println("Erreur écriture du int");
        }
    }
    
    public void sendMessage(Message message) {
        try {
            output.writeObject(message);
            output.flush();
            System.out.println("Send ok");
        } catch (IOException ex) {
            System.out.println("Erreur écriture du message");
        }
    }
}
