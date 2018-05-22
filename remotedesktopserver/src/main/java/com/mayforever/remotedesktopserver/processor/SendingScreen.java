package com.mayforever.remotedesktopserver.processor;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mayforever.network.newtcp.TCPClient;
import com.mayforever.remotedesktopserver.data.ImageResponse;

public class SendingScreen extends com.mayforever.thread.BaseThread{
	private TCPClient client = null;
	private Robot robot = null;
	public boolean clientReady = true;
	public boolean isClientReady() {
		return clientReady;
	}
	public void setClientReady(boolean clientReady) {
		this.clientReady = clientReady;
	}
	public SendingScreen (TCPClient client, Robot robot){
		this.client = client;
		this.robot = robot;
		// this.robot.
	}
	public void startSending(){
		this.startThread();
	}
	public void stopSending(){
		this.stopThread();
	}
	public void run() {
		// TODO Auto-generated method stub
		
		while(this.getServiceState()==com.mayforever.thread.state.ServiceState.RUNNING){
			// System.out.println("Do Nothing");
//			try {
				// if()
				if(isClientReady()) {
					ImageResponse imageResponse = this.gettingScreenShot();
					if(imageResponse==null){
						// do Error no to send
					}else{
						try {
//							System.out.println(imageResponse.toBytes().length);
							this.setClientReady(false);
							this.client.sendPacket(imageResponse.toBytes());
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							this.stopThread();
							e.printStackTrace();
						}
					}
				}
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	public ImageResponse gettingScreenShot(){
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage capture = null;
		capture = robot.createScreenCapture(screenRect);
		// System.out.
		if(capture!=null){
			ImageResponse imageResponce = new ImageResponse();
			imageResponce.setProtocol((byte)1);

			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			try {
				ImageIO.write(capture, "jpeg", baos);
				baos.flush();
				imageResponce.setBufferImage(baos.toByteArray());
				baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return imageResponce;
		}
		return null;
	}
}
