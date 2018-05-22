package com.mayforever.remotedesktopserver.connection;

import java.awt.Robot;
import java.nio.channels.AsynchronousSocketChannel;

import com.mayforever.remotedesktopserver.data.Command;
import com.mayforever.remotedesktopserver.data.CommandRequest;

public class TCPClientCommandListener  implements com.mayforever.network.newtcp.ClientListener{
	public Robot robot = null;
	public com.mayforever.network.newtcp.TCPClient tcpClient = null;
	
	public TCPClientCommandListener (AsynchronousSocketChannel asc,Robot robot) {
		this.robot = robot;
		this.tcpClient = new com.mayforever.network.newtcp.TCPClient(asc);
		this.tcpClient.addListener(this);
	}

	public void packetData(byte[] data) {
		// TODO Auto-generated method stub
//		System.out.println(data.length);
		// if(d)
		
		if(data[0] == (byte)2){
			int dataLengthProcess = 0;
			do{
				if (data[dataLengthProcess+1] == Command.MOUSE_MOVE){
					byte[] command = new byte[10];
					System.arraycopy(data, dataLengthProcess, command, 0, 10);
					doEvent(command);
					dataLengthProcess += 10;
				}else{
					byte[] command = new byte[6];
					System.arraycopy(data, dataLengthProcess, command, 0, 6);
					doEvent(command);
					dataLengthProcess += 6;
				}
			}while(data.length > dataLengthProcess);
			
		}
	}

	public void socketError(Exception e) {
		// TODO Auto-generated method stub
		
	}
	
	private void doEvent(byte[] data){
		CommandRequest commandRequest = new CommandRequest();
		
		byte command = (byte)999;
		try{
			commandRequest.fromBytes(data);
			command = commandRequest.getCommand();
		}catch (Exception e){
			System.out.println("failed to catch real command please repeat");
//			return;
		}
		
		if(command == Command.MOUSE_PRESSED){
//			System.out.println(commandRequest.getParams()[0]);
			robot.mousePress(commandRequest.getParams()[0]);
//			robot.mouseMove(commandRequest.getParams()[1],
//			commandRequest.getParams()[2]);
			
		}else if (command == Command.MOUSE_RELEASED){
			
			robot.mouseRelease(commandRequest.getParams()[0]);
		}else if (command == Command.MOUSE_MOVE){
			robot.mouseMove(commandRequest.getParams()[0],
					commandRequest.getParams()[1]);
		}else if (command == Command.KEY_PRESSED){
			robot.keyPress(commandRequest.getParams()[0]);
		}else if (command == Command.KEY_RELEASED){
			robot.keyRelease(commandRequest.getParams()[0]);
		}
	}
}
