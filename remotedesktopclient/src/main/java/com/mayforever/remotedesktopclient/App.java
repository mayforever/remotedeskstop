/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayforever.remotedesktopclient;

import com.mayforever.queue.Queue;
import com.mayforever.remotedesktopclient.connection.TCPClientCommandSender;
import com.mayforever.remotedesktopclient.connection.TCPClientScreenReciever;
import com.mayforever.remotedesktopclient.data.ImageResponse;
/**
 *
 * @author User
 */
public class App {
    public static Queue<ImageResponse> imageResponseQueue = null;
    public static TCPClientCommandSender clientCommandSender = null;
    public static void main(String [] args){
        imageResponseQueue = new Queue<ImageResponse>();
        clientCommandSender = new TCPClientCommandSender("192.168.0.139", "loverboy", "loverboycontrol");
        new TCPClientScreenReciever( "192.168.0.139", "loverboy","loverboyview");
    }
    
}
