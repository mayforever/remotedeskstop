/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayforever.remotedesktopclient.processor;
    

import com.mayforever.network.newtcp.TCPClient;
import com.mayforever.remotedesktopclient.App;
import com.mayforever.remotedesktopclient.data.Command;
import com.mayforever.remotedesktopclient.data.CommandRequest;
import com.mayforever.remotedesktopclient.data.ImageResponse;
import com.mayforever.tools.BitConverter;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author MIS
 */
public class ScreenLoader extends com.mayforever.thread.BaseThread 
    implements MouseMotionListener,MouseListener,KeyListener{
    private Image image1 = null;
    private Graphics graphics = null;
    private JPanel screenView = null;
    private TCPClient tcpClient = null; 
    int mouseX = 0; 
    int mouseY = 0;
    public JPanel getScreenView() {
        return screenView;
    }
    public ScreenLoader(JPanel jpanel, TCPClient tcpClient){
        screenView = jpanel;
        this.screenView.addMouseMotionListener(this);
        this.screenView.setFocusable(true);
        this.screenView.requestFocus();
        this.screenView.addKeyListener(this);
        this.screenView.addMouseListener(this);
        this.tcpClient = tcpClient;
        
    }
    
    public void startScreenLoader(){
        this.startThread();
    }
    @Override
    public void run() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        while(this.getServiceState()== com.mayforever.thread.state.ServiceState.RUNNING){
            try {
                ImageResponse imageResponse = App.imageResponseQueue.get();
                if(imageResponse!=null){
                    try {
//                        System.out.println(imageResponse.getBufferImage());
                        image1 = ImageIO.read(new ByteArrayInputStream(imageResponse.getBufferImage()));
                        image1 = image1.getScaledInstance(this.screenView.getWidth(),this.screenView.getHeight(),Image.SCALE_FAST);
                        graphics = this.screenView.getGraphics();
                        graphics.drawImage(image1, 0, 0, this.screenView.getWidth(), this.screenView.getHeight(), this.screenView);
                        
                    } catch (IOException ex) {
                       // Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        // System.out.println(controlFrame.getWidth());
                        
                        
                }   
            } catch (InterruptedException ex) {
                Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
        mouseX = e.getX();
        mouseY = e.getY();
//        System.out.println(InputEvent.getMaskForButton(e.getButton()));
        CommandRequest commandRequest = new CommandRequest();
        commandRequest.setProtocol((byte)2);
        commandRequest.setCommand(Command.MOUSE_MOVE);
        int[] params = {mouseX, mouseY};
        commandRequest.setParams(params);
        try {
            App.clientCommandSender.tcpClient.sendPacket(commandRequest.toBytes());

        } catch (IOException ex) {
            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
//        try {
//            java.lang.Thread.sleep(100);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
//        doCommand(Command.MOUSE_MOVE,e.getX(),e.getY());
//        try {
//            this.tcpClient.sendPacket(doCommand(Command.MOUSE_MOVE,e.getX(),e.getY()));
//        } catch (IOException ex) {
//            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, ex);
//        }
        mouseX = e.getX();
        mouseY = e.getY();
//        System.out.println(InputEvent.getMaskForButton(e.getButton()));
        CommandRequest commandRequest = new CommandRequest();
        commandRequest.setProtocol((byte)2);
        commandRequest.setCommand(Command.MOUSE_MOVE);
        int[] params = {mouseX, mouseY};
        commandRequest.setParams(params);
        try {
            App.clientCommandSender.tcpClient.sendPacket(commandRequest.toBytes());

        } catch (IOException ex) {
            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
//        try {
//            java.lang.Thread.sleep(100);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
//        System.out.println(InputEvent.getMaskForButton(e.getButton()));
        CommandRequest commandRequest = new CommandRequest();
        commandRequest.setProtocol((byte)2);
        commandRequest.setCommand(Command.MOUSE_PRESSED);
        int[] params = {InputEvent.getMaskForButton(e.getButton())};
        commandRequest.setParams(params);
        try {
            App.clientCommandSender.tcpClient.sendPacket(commandRequest.toBytes());

        } catch (IOException ex) {
            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
//        System.out.println(e.getButton());
////        doCommand(Command.MOUSE_RELEASED,e.getX(),e.getY());
//        try {
//            this.tcpClient.sendPacket(doCommand(Command.MOUSE_RELEASED,e.getButton()));
//        } catch (IOException ex) {
//            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println(InputEvent.getMaskForButton(e.getButton()));
        CommandRequest commandRequest = new CommandRequest();
        commandRequest.setProtocol((byte)2);
        commandRequest.setCommand(Command.MOUSE_RELEASED);
        int[] params = {InputEvent.getMaskForButton(e.getButton())};
        commandRequest.setParams(params);
        try {
            App.clientCommandSender.tcpClient.sendPacket(commandRequest.toBytes());

        } catch (IOException ex) {
            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of generated methods, choose Tools | Templates.
//        doCommand(Command.MOUSE_RELEASED,e.getKeyChar());
//        System.out.println("the key is :"+e.getExtendedKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //To change body of generated methods, choose Tools | Templates.
//        doCommand(Command.MOUSE_RELEASED,e.getKeyChar());
        System.out.println("the key is :"+e.getExtendedKeyCode());
        CommandRequest commandRequest = new CommandRequest();
        commandRequest.setProtocol((byte)2);
        commandRequest.setCommand(Command.KEY_PRESSED);
        int[] params = {e.getExtendedKeyCode()};
        commandRequest.setParams(params);
        try {
            App.clientCommandSender.tcpClient.sendPacket(commandRequest.toBytes());

        } catch (IOException ex) {
            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //To change body of generated methods, choose Tools | Templates.
        System.out.println("the key is :"+e.getExtendedKeyCode());
        CommandRequest commandRequest = new CommandRequest();
        commandRequest.setProtocol((byte)2);
        commandRequest.setCommand(Command.KEY_RELEASED);
        int[] params = {e.getExtendedKeyCode()};
        commandRequest.setParams(params);
        try {
            App.clientCommandSender.tcpClient.sendPacket(commandRequest.toBytes());

        } catch (IOException ex) {
            Logger.getLogger(ScreenLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


//    private byte[] doCommand(byte command, char key){
//        CommandRequest commandRequest=new CommandRequest();
//        commandRequest.setProtocol((byte)2);
//        commandRequest.setCommand(command);
//        byte[] param={(byte)key};
//        commandRequest.setParam(param);
//        return commandRequest.toBytes();
//    }
//    private byte[] doCommand(byte command, int x, int y){
//        CommandRequest commandRequest=new CommandRequest();
//        commandRequest.setProtocol((byte)2);
//        commandRequest.setCommand(command);
//        byte[] param=new byte[8];
//        int indexParam = 0;
//        System.arraycopy(BitConverter.intToBytes(x, ByteOrder.BIG_ENDIAN),
//                0, param, indexParam, 4);
//        indexParam+=4;
//        System.arraycopy(BitConverter.intToBytes(y, ByteOrder.BIG_ENDIAN),
//                0, param, indexParam, 4);
//        commandRequest.setParam(param);
//        return commandRequest.toBytes();
//    }
//    private byte[] doCommand(byte command, int button){
//        CommandRequest commandRequest=new CommandRequest();
//        commandRequest.setProtocol((byte)2);
//        commandRequest.setCommand(command);
//        byte[] param=new byte[8];
//        int indexParam = 0;
//        System.arraycopy(BitConverter.intToBytes(button, ByteOrder.BIG_ENDIAN),
//                0, param, indexParam, 4);
//        commandRequest.setParam(param);
//        return commandRequest.toBytes();
//    }
}
