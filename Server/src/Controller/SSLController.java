/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 *
 * @author Nathan
 */
public class SSLController {
    private final int _port;
    private final int _backlog;
    private SSLServerSocket _sslServer;
    private final InetAddress _inetAddress;
    
    public void connect() throws IOException{
        SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        _sslServer = (SSLServerSocket) sslserversocketfactory.createServerSocket(_port,_backlog,_inetAddress);
    }
        
    public SSLServerSocket getServer() {
        return _sslServer;
    }
    
    public SSLController(int port, int backlog, String address) throws UnknownHostException {
        _port = port;
        _backlog = backlog;
        _inetAddress = InetAddress.getByName(address);
    }
}