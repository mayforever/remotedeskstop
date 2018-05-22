/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayforever.remotedesktopclient.connection;

import com.mayforever.remotedesktopclient.ControlFrame;
import com.mayforever.remotedesktopclient.data.Authenticate;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mis
 */
public class TCPClientCommandSender implements com.mayforever.network.newtcp.ClientListener{
    public com.mayforever.network.newtcp.TCPClient tcpClient = null;
    public int port = 4448;
    public TCPClientCommandSender(String ip,String username, String password){
        // controlFrame = new ControlFrame();
        
        Authenticate authenticate = new Authenticate();
        authenticate.setProtocol((byte)1);
        authenticate.setPassword(password);
        authenticate.setUsername(username);
        
        this.tcpClient = new com.mayforever.network.newtcp.TCPClient(ip, port);
        this.tcpClient.setAllocationPerBytes(2048*2048);
        this.tcpClient.addListener(this);
        
        
        try {
            this.tcpClient.sendPacket(authenticate.toBytes());
        } catch (IOException ex) {
            Logger.getLogger(TCPClientScreenReciever.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    @Override
    public void packetData(byte[] bytes) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void socketError(Exception excptn) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
