import Controller.ServerController;
import Controller.SenderController;
import Controller.ReceiverController;
import Model.NodeModel;
import View.MainFrame;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @author Nathan
 */
public class Client {
    
    private static int portLocal;
    private static int portClient;
    
    private static int getPort(String text) {
        System.out.println(text);
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }
    
    private static void initThread() throws IOException {
        Client.portLocal = Client.getPort("Sur quel port souhaitez-vous être visible ?");
        Client.portClient = Client.getPort("Avec quel port voulez-vous communiquer ?");
        NodeModel client = new NodeModel(InetAddress.getByName("localhost"),portClient);
        SenderController send = new SenderController(client,new ServerController().getNodes());
        MainFrame frame = new MainFrame(send);
        new Thread(new ReceiverController(InetAddress.getByName("localhost"),portLocal,frame)).start();
    }

    public static void main(String[] args) throws IOException {
        Client.portLocal = 0;
        Client.initThread();
    }
}
