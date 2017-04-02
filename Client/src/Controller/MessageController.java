package Controller;

import Model.MessageModel;
import Model.NodeModel;
import java.util.ArrayList;
import javax.crypto.SecretKey;
/**
 *
 * @author Nathan
 */
public class MessageController {
    private final int SIZE = 3;
    
    public MessageModel creation(NodeModel c,String m, ArrayList<SecretKey> key, ArrayList<NodeModel> nodes){
        NodeModel client = c;
        CryptographyController cryptage = new CryptographyController();
        MessageModel message = new MessageModel(m.getBytes(),null,client,0);
        for (int i=0; i < SIZE; i++) {
            cryptage.setValues(message, key.get(i));
            message.setMessage(cryptage.crypt(true));
            message.setNode(nodes.get(i));
        }
        return message;
    }  
}
