package com.mayforever.remotedesktopserver.data;

import java.nio.ByteOrder;

import com.mayforever.tools.BitConverter;

public class ImageResponse extends BaseClass{
	private byte[] bufferImage = null;
	
	public byte[] getBufferImage() {
		return bufferImage;
	}

	public void setBufferImage(byte[] bufferImage) {
		this.bufferImage = bufferImage;
	}

	public byte[] toBytes() {
		// TODO Auto-generated method stub
		byte[] data = new byte[bufferImage.length+5];
		int index = 0;
		data[index] = this.getProtocol();
		index++;
		System.arraycopy(BitConverter.intToBytes(bufferImage.length+5, ByteOrder.BIG_ENDIAN),
				0, data, index, 4);
		index+=4;
		System.arraycopy(bufferImage, 0, data, index,bufferImage.length);
		index+=bufferImage.length;
		return data;
	}
	private int size = 0;
	public int getSize() {
		return size;
	}

	public void setSize(int sizeOfImages) {
		this.size = sizeOfImages;
	}
	public void fromBytes(byte[] data) {
		// TODO Auto-generated method stub
		int index = 0;
		this.setProtocol(data[index]);
		index++;
		this.setSize(data.length);
		index+=4;
        bufferImage  = new byte[data.length-5];
		System.arraycopy(data, index, bufferImage, 0, data.length-5);
		index+=bufferImage.length;
	}


	
}
