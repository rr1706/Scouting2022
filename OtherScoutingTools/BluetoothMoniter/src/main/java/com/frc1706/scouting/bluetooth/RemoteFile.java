package com.frc1706.scouting.bluetooth;

import java.security.MessageDigest;

public class RemoteFile {
	private String name;
	private int length;
	private String checksum;
	private String contents;

	public RemoteFile(String name, int length, String checksum) {
		this.name = name;
		this.length = length;
		this.checksum = checksum;
	}

	private static String hex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String s = Integer.toHexString(0x0ff & bytes[i]);
			if (s.length() == 0) {
				sb.append("00");
			} else if (s.length() == 1) {
				sb.append("0").append(s);
			} else {
				sb.append(s);
			}
		}
		return sb.toString();
	}

	private String md5() {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(contents.getBytes());
			return hex(md5.digest());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public boolean isValid() {
		boolean ret = false;
		if (contents != null && checksum != null) {
			ret = checksum.equalsIgnoreCase(md5());
		}
		return ret;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}

	/**
	 * @param checksum
	 *            the checksum to set
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents
	 *            the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
}
