package com.mayforever.remotedesktopserver.data;

import java.nio.ByteOrder;
import java.util.ArrayList;

import com.mayforever.tools.BitConverter;

public class CommandRequest extends BaseClass{
	public byte getCommand() {
		return command;
	}

	public void setCommand(byte command) {
		this.command = command;
	}
	public int[] getParams() {
		return params;
	}

	public void setParams(int[] params) {
		this.params = params;
	}
	private int[] params = null;
	
	private byte command = 0; 
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		byte[] data = new byte[(params.length*4) + 2];
		int index = 0;
		data[index] = this.getProtocol();
		index++;
		data[index] = this.getCommand();
		index++;
		for(int param: params) {
			System.arraycopy(BitConverter.intToBytes(param, ByteOrder.BIG_ENDIAN), 0, data, index, 4);
			index+=4;
		}
		return data;
	}

	public void fromBytes(byte[] data) {
		// TODO Auto-generated method stub
		int index = 0;
		ArrayList<Integer> al= new ArrayList<Integer>();
		this.setProtocol(data[index]);
		index++;
		this.setCommand(data[index]);
		index++;
		do {
			al.add(BitConverter.bytesToInt(data, index, ByteOrder.BIG_ENDIAN));
			index+=4;
		}while(index!=data.length);
//		this.params = al.toArray();
		this.params = new int[al.size()];
		for(int i = 0;i < al.size();i++) {
			this.params[i] = al.get(i);
		}
	}

}
