package com.mayforever.remotedesktopserver.connection;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;

import com.mayforever.remotedesktopserver.App;
import com.mayforever.remotedesktopserver.data.Authenticate;
import com.mayforever.remotedesktopserver.data.ScreenSize;
import com.mayforever.remotedesktopserver.tcpdata.TCPData;

public class TCPClient implements com.mayforever.network.newtcp.ClientListener{
	com.mayforever.network.newtcp.TCPClient tcpClient = null;
	AsynchronousSocketChannel asc = null;
	public TCPClient(AsynchronousSocketChannel asc) {
		this.asc = asc;
		this.tcpClient = new com.mayforever.network.newtcp.TCPClient(asc);
		this.tcpClient.addListener(this);
	
	}
	public void packetData(byte[] data) {
		// TODO Auto-generated method stub
		Authenticate authenticate = new Authenticate();
		authenticate.fromBytes(data);
		String username = authenticate.getUsername();
		String password = authenticate.getPassword();
		if (authenticate.getProtocol() == 0) {
			if (password.equals(App.passwordview)||
					password.equals(App.passwordcontrol)) {
				
				if (!App.controllerMap.containsKey(username)) {
					App.controllerMap.put(username, new TCPData());					
				}
					
				App.controllerMap.get(username).
					setClientScreenSender(new TCPClientScreenSender(asc,
							App.controllerMap.get(username).getRobot()));
				Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
				String width=""+dim.getWidth();
				String height=""+dim.getHeight();
				ScreenSize screen = new ScreenSize();
				screen.setProtocol((byte)3);
				screen.setWidth((int)Float.parseFloat(width));
				screen.setHeight((int)Float.parseFloat(height));
				try {
					this.tcpClient.sendPacket(screen.toBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
//				this.tcpClient.d
				try {
					asc.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}else if(authenticate.getProtocol() == 1) {
//			System.out.println("control request password is" +password);
			if (password.equals(App.passwordcontrol)) {
				if (!App.controllerMap.containsKey(username)) {
					App.controllerMap.put(username, new TCPData());					
				}
				
				App.controllerMap.get(username).
					setClientCommandListener(new TCPClientCommandListener(asc,
							App.controllerMap.get(username).getRobot()));
				
				
			}else {
//				this.tcpClient.d
				try {
					asc.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
		
		// this.tcpClient.c
	}

	public void socketError(Exception e) {
		// TODO Auto-generated method stub
		
	}
	
}
