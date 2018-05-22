/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayforever.remotedesktopclient.connection;

import com.mayforever.remotedesktopclient.App;
import com.mayforever.remotedesktopclient.ControlFrame;
import com.mayforever.remotedesktopclient.data.Authenticate;
import com.mayforever.remotedesktopclient.data.ImageRequest;
import com.mayforever.remotedesktopclient.data.ImageResponse;
import com.mayforever.remotedesktopclient.data.ScreenSize;
import com.mayforever.remotedesktopclient.processor.ScreenLoader;
import com.mayforever.tools.BitConverter;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class TCPClientScreenReciever implements com.mayforever.network.newtcp.ClientListener{
    private com.mayforever.network.newtcp.TCPClient tcpClient = null;
    private ControlFrame controlFrame = null;
    public int port = 4448;
    private ScreenLoader screenLoader = null; 
    private boolean havePending = false;
    private byte[] pendingImageData = null;
    private int pendingIndex = 0;
    public TCPClientScreenReciever (String ip,String username, String password){
        controlFrame = new ControlFrame();
        Authenticate authenticate = new Authenticate();
        authenticate.setProtocol((byte)0);
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
        
//        System.out.println(Arrays.toString(bytes));
//        System.out.println(bytes.length);
//        if(bytes.length == 0){
//            return;
//        }
        if(!havePending){
            if (bytes[0] == 1){
//               // sizeRecieved += bytes.length;
               if(pendingImageData==null){
                   int dataPendingCount = BitConverter.bytesToInt(bytes, 1, ByteOrder.BIG_ENDIAN);
//                   System.out.println(dataPendingCount);
                   pendingImageData = new byte[dataPendingCount];
                   this.havePending = true;
               }
//                   ImageResponse imageResponse = new ImageResponse();
//                   imageResponse.fromBytes(bytes);
////                   System.out.println(imageResponse.getSize());
//                   App.imageResponseQueue.add(imageResponse);
                
            }else if(bytes[0] == 3){
             ScreenSize screenSize = new ScreenSize();
             screenSize.fromBytes(bytes);
             System.out.println(screenSize.getWidth()+" * "+screenSize.getHeight());
             screenLoader = new ScreenLoader(this.controlFrame.getjPanel1(), this.tcpClient);
             screenLoader.startScreenLoader();
             this.controlFrame.getjPanel1().setPreferredSize(new Dimension(screenSize.getWidth(), screenSize.getHeight()));
//             this.screenLoader.getScreenView().setSize(screenSize.getWidth(), screenSize.getHeight());
//             controlFrame.getjPanel1().add(this.screenLoader.getScreenView());
             controlFrame.setVisible(true);
             this.controlFrame.getJScrollPane1().setViewportView(screenLoader.getScreenView());
             this.controlFrame.getJScrollPane1().validate();
             this.controlFrame.getJScrollPane1().repaint();
//             this.controlFrame.getjPanel1().addMouseListener(this);
             
             //this.controlFrame.getjPanel1().
//             this.controlFrame.getjPanel1().repaint();
//             this.controlFrame.pack();
             // this.controlFrame.
            }else{

            }
        }
        if(havePending){
           System.arraycopy(bytes, 0, pendingImageData, pendingIndex, bytes.length);
           pendingIndex+=bytes.length;
//           System.out.println(pendingImageData.length);
           if(pendingIndex == pendingImageData.length){
               try {
                   ImageResponse imageResponse = new ImageResponse();
                   imageResponse.fromBytes(pendingImageData);
//                   System.out.println(imageResponse.getSize());
                   App.imageResponseQueue.add(imageResponse);
                   ImageRequest imageRequest=new ImageRequest();
                   imageRequest.setProtocol((byte)4);
                   imageRequest.setReady(true);
//                   System.out.println(imageRequest.toBytes());
                   this.tcpClient.sendPacket(imageRequest.toBytes());
                   this.havePending = false;
                   this.pendingImageData = null;
                   this.pendingIndex = 0;
               } catch (IOException ex) {
//                   Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           
        }
        
         
        
    }

    @Override
    public void socketError(Exception excptn) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println(excptn);
    }
    
}
