package com.mayforever.remotedesktopclient.data;

import java.nio.ByteOrder;

import com.mayforever.tools.BitConverter;

public class ScreenSize extends BaseClass {
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	private int width = 0;
	private int height = 0;
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		byte[] data = new byte[9];
		int index = 0;
		data[0] = this.getProtocol();
		index++;
		System.arraycopy(BitConverter.intToBytes(width, ByteOrder.BIG_ENDIAN), 0, data, index, 4);
		index+=4;
		System.arraycopy(BitConverter.intToBytes(height, ByteOrder.BIG_ENDIAN), 0, data, index, 4);
		index+=4;
		return null;
	}

	public void fromBytes(byte[] data) {
		// TODO Auto-generated method stub
		int index = 0;
		this.setProtocol(data[index]);
		index++;
		this.setWidth(BitConverter.bytesToInt(data, index, ByteOrder.BIG_ENDIAN));
		index+=4;
		this.setHeight(BitConverter.bytesToInt(data, index, ByteOrder.BIG_ENDIAN));
		index+=4;
	}

}
