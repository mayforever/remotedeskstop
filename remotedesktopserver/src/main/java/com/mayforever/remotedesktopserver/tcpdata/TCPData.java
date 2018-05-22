package com.mayforever.remotedesktopserver.tcpdata;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;

import com.mayforever.remotedesktopserver.connection.TCPClientCommandListener;
import com.mayforever.remotedesktopserver.connection.TCPClientScreenSender;

public class TCPData 	{
	private Robot robot = null;
	public TCPData() {
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
		try {
			setRobot(new Robot(graphicsDevice));
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public TCPData(TCPClientCommandListener clientCommandListener, TCPClientScreenSender clientScreenSender) {
//		this.clientCommandListener = clientCommandListener;
//		this.clientScreenSender = clientScreenSender;
//	}
	private TCPClientCommandListener clientCommandListener = null;
	private TCPClientScreenSender clientScreenSender = null;
	public TCPClientCommandListener getClientCommandListener() {
		return clientCommandListener;
	}
	public void setClientCommandListener(TCPClientCommandListener clientCommandListener) {
		this.clientCommandListener = clientCommandListener;
	}
	public TCPClientScreenSender getClientScreenSender() {
		return clientScreenSender;
	}
	public void setClientScreenSender(TCPClientScreenSender clientScreenSender) {
		this.clientScreenSender = clientScreenSender;
	}
	/**
	 * @return the robot
	 */
	public Robot getRobot() {
		return robot;
	}
	/**
	 * @param robot the robot to set
	 */
	public void setRobot(Robot robot) {
		this.robot = robot;
	}
}
