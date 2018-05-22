package com.mayforever.remotedesktopserver.connection;

import java.awt.Robot;
import java.nio.channels.AsynchronousSocketChannel;

import com.mayforever.remotedesktopserver.data.ImageRequest;
import com.mayforever.remotedesktopserver.processor.SendingScreen;

public class TCPClientScreenSender implements com.mayforever.network.newtcp.ClientListener{
	public com.mayforever.network.newtcp.TCPClient tcpClient = null;
	public Robot robot = null;
	public boolean control = false;
	public AsynchronousSocketChannel asc = null;
	private SendingScreen sendingScreen = null;
	public int allocation =1024*1024;
	
	
	public TCPClientScreenSender(AsynchronousSocketChannel asc, Robot robot){
		this.robot = robot;
		this.tcpClient = new com.mayforever.network.newtcp.TCPClient(asc);
		this.tcpClient.setAllocationPerBytes(allocation);
		this.tcpClient.addListener(this);
		
		this.sendingScreen = new SendingScreen(this.tcpClient,this.robot);
		this.sendingScreen.startSending();
	}
	public void packetData(byte[] arg0) {
		// TODO Auto-generated method stub
//		System.out.println(Arrays.toString(arg0));
		if (arg0[0] == 0){
//			
		}else if(arg0[0] == 4) {
			ImageRequest imageRequest = new ImageRequest();
			imageRequest.fromBytes(arg0);
			
			this.sendingScreen.setClientReady(imageRequest.isReady());
		}
	}

	public void socketError(Exception arg0) {
		// TODO Auto-generated method stub
//		this.
		System.out.println(arg0.toString());
		sendingScreen.stopSending();
		
		// App.clientList.
		// for(client)
	}

}
