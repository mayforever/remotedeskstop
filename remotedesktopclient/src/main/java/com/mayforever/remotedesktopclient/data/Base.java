package com.mayforever.remotedesktopclient.data;

public interface Base {
	public byte[] toBytes();
	public void fromBytes(byte[] data);
}
