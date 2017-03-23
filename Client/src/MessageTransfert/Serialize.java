/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageTransfert;

import Message.Message;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author Nathan
 */
public class Serialize {
    
    private Message message;
    
    public Serialize(Message m) {
        message = m;
    }
    
    public Serialize() {}
    
    public void setMessage(Message m) {
        message = m;
    }
    
    public Message getMessage() {
        return message;
    }
    
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(message);
        return out.toByteArray();
    }
    
}
