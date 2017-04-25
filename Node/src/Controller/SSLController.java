/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author Nathan
 */
public class SSLController {
    private final int _port;
    private SSLSocket _sslsocket;
    private final String _inetAddress;
    
    public void connect() {
        try {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            _sslsocket = (SSLSocket) sslsocketfactory.createSocket(_inetAddress, _port);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public SSLSocket getSocket() {
        return _sslsocket;
    }
    
    public SSLController(String inetAddress, int port) {
        _inetAddress = inetAddress;
        _port = port;
    }
}