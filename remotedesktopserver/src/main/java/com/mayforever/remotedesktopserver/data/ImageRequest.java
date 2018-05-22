package com.mayforever.remotedesktopserver.data;

public class ImageRequest extends BaseClass{
	private boolean ready = false;
	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public byte[] toBytes() {
		// TODO Auto-generated method stub
		byte[] data =new byte[2];
		int index = 0;
		data[index] = this.getProtocol();
		index++;
		if(ready) {
			data[index] = (byte)0;
		}else {
			data[index] = (byte)1;
		}
		
		return data;
	}

	public void fromBytes(byte[] data) {
		// TODO Auto-generated method stub
		int index = 0;
		this.setProtocol(data[index]);
		index++;
		if(data[index]==(byte)0) {
			this.setReady(true);
		}else {
			this.setReady(false);
		}
		index++;	
	}


	
}
