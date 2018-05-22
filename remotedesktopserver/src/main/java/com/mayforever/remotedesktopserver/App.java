package com.mayforever.remotedesktopserver;

import java.util.HashMap;

import com.mayforever.remotedesktopserver.connection.TCPServer;
import com.mayforever.remotedesktopserver.tcpdata.TCPData;

/**
 * Hello world!
 *
 */
public class App 
{
	public static String passwordcontrol = "loverboycontrol";
	public static String passwordview = "loverboyview";
	public static String username = "loverboy";
	public static HashMap<String, TCPData> controllerMap = null;  
	// public static ArrayList<TCPClient> clientList = null;
    public static void main( String[] args )
    {
    	// clientList =new ArrayList<TCPClient>();
    	controllerMap = new HashMap<String, TCPData>();
    	new TCPServer(4448, "192.168.0.139");
    	
    	while(true){
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}
