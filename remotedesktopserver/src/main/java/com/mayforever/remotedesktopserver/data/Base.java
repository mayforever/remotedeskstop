package com.mayforever.remotedesktopserver.data;

public interface Base {
	public byte[] toBytes();
	public void fromBytes(byte[] data);
}
