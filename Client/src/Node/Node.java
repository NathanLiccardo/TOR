/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Node;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Key;
import javax.crypto.SecretKey;

/**
 *
 * @author Nathan
 */
public class Node implements Serializable{
    InetAddress ip = null;
    Integer port = null;
    Key key;
    SecretKey secret = null;
    
    public Node(InetAddress ipAddress, Integer p) {
        ip = ipAddress;
        port = p;
    }
    
    public Node(Socket s, int nodePort, Key k){
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
    
    public void setSecret(SecretKey s){
        secret = s;
    }
    
    public InetAddress getIp(){
        return ip;
    }
    
    public int getPort(){
        return port;
    }
    
    public Key getKey() {
        return key;
    }
    
    public SecretKey getSecret() {
        return secret;
    }
    
}
