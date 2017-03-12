/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import MessageTransfert.ReceiveMessage;
import MessageTransfert.SendMessage;
import Node.Node;

/**
 *
 * @author Nathan
 * Permet de se connecter au serveur et d'obtenir la liste des noeuds existants.
 */
public class ConnexionServer {
    
    // Informations de connexion au serveur.
    private int port = 2000;
    private String host = "localhost";
    
    // Echange de messages
    private Socket connect;
    private ReceiveMessage rm;
    private SendMessage sm;
    
    // Liste des noeuds
    private ArrayList<Node> node;
    
    /**
     * Connexion au serveur
     * @throws IOException 
     */
    private void initExchange() throws IOException {
        connect = new Socket(host, port);
        sm = new SendMessage(connect);
        rm = new ReceiveMessage(connect);
    }
    
    /**
     * Envoi de l'info client au serveur
     */
    private void connectAsClient() {
        sm.sendInt(0);
        node = rm.receiveNodeList();
    }
    
    /**
     * Fermerture de la connexion
     * @throws IOException 
     */
    private void closeConnection() throws IOException {
        connect.close();
    }
    
    /**
     * Constructeur
     * @throws IOException 
     */
    public ConnexionServer() throws IOException{
        initExchange();
        connectAsClient();
        closeConnection();
    }
    
    /**
     * @return Nodes
     */
    public ArrayList<Node> getNodes(){
        return node;
    }
}
