package com.mayforever.remotedesktopserver.data;

import java.nio.ByteOrder;

import com.mayforever.tools.BitConverter;

public class Authenticate extends BaseClass {
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String password = "";
	private String username = "";
	private int usernameSize = 0 ;
	private int passwordSize = 0;
	public byte[] toBytes() {
		// TODO Auto-generated method stub
		byte[] data = new byte[password.length()+username.length()+1+4+4];
		int index = 0;
		data[index] = (byte)0;
		index++;
		System.arraycopy(BitConverter.intToBytes(password.length(), ByteOrder.BIG_ENDIAN),
				0, data, index, 4);
		index+=4;
		System.arraycopy(password.getBytes(), 0, data, index, password.length());
		index+=password.length();
		System.arraycopy(BitConverter.intToBytes(username.length(), ByteOrder.BIG_ENDIAN),
				0, data, index, 4);
		index+=4;
		System.arraycopy(username.getBytes(), 0, data, index, username.length());
		index+=username.length();
		return data;
	}

	public void fromBytes(byte[] data) {
		// TODO Auto-generated method stub
		int index = 0;
		this.setProtocol(data[index]);
		index++;
		this.passwordSize = BitConverter.bytesToInt(data, index, ByteOrder.BIG_ENDIAN);
		index+=4;
		this.setPassword(new java.lang.String(data, index, passwordSize));
		index+=passwordSize;
//		this.setPasswordSize(data.length-9);
		this.usernameSize = BitConverter.bytesToInt(data, index, ByteOrder.BIG_ENDIAN);
		index+=4;
		this.setUsername(new java.lang.String(data, index, usernameSize));
		index+=usernameSize;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPasswordSize() {
		return passwordSize;
	}

	public void setPasswordSize(int passwordSize) {
		this.passwordSize = passwordSize;
	}

	/**
	 * @return the usernameSize
	 */
	public int getUsernameSize() {
		return usernameSize;
	}

	/**
	 * @param usernameSize the usernameSize to set
	 */
	public void setUsernameSize(int usernameSize) {
		this.usernameSize = usernameSize;
	}

}
