/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PublicKey;

/**
 *
 * @author Nathan
 */
public class NodeModel implements Serializable{
    InetAddress ip = null;
    Integer port = null;
    PublicKey key;
    
    public NodeModel(InetAddress ipAddress, Integer p) {
        ip = ipAddress;
        port = p;
    }
    
    public NodeModel(Socket s, int nodePort, PublicKey k){
        ip = s.getInetAddress();
        port = nodePort;
        key = k;
    }
    
    public void setIp(InetAddress ipAddress){
        ip = ipAddress;
    }
    
    public void setPort(int p){
        port = p;
    }
    
    public InetAddress getIp(){
        return ip;
    }
    
    public int getPort(){
        return port;
    }
    
    public PublicKey getKey() {
        return key;
    }
    
}
