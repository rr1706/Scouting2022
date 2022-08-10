package com.frc1706.scouting.bluetooth;

import java.io.File;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

public class DeviceWatcher extends Thread implements DiscoveryListener {

	private RemoteDevice remoteDevice = null;
	private boolean done = false;
	private DeviceMonitor monitor = null;
	private String connectionURL = "";
	private boolean isSearching = false;
	private final DiscoveryAgent agent;
	private String lastMessage = null;
	private File lastFile = null;

	private static int searchCount = 0;

	public DeviceWatcher(RemoteDevice remoteDevice, DiscoveryAgent agent) {
		this.remoteDevice = remoteDevice;
		this.agent = agent;
	}

	public void sendMessage(String message) {
		if (isOnline()) {
			monitor.sendMessage(message);
		} else {
			lastMessage = message;
		}
	}

	public void sendFile(File file) {
		if (isOnline()) {
			monitor.sendFile(file);
		} else {
			lastFile = file;
		}
	}

	public boolean isOnline() {
		return monitor != null && monitor.isAlive();
	}

	@Override
	public void run() {
		try {
			Thread.sleep((int) (Math.random() * 25));
		} catch (InterruptedException e) {
		}
		while (!done) {

			if (monitor == null || !monitor.isAlive()) {
				if (connectionURL == null || connectionURL.length() == 0) {
					if (!isSearching) {
						if (searchCount < 6) {
							try {
								// System.out.println("Searching for services on
								// " + getDeviceName());
								UUID[] uuidSet = new UUID[1];
								uuidSet[0] = App.uuid;
								agent.searchServices(null, uuidSet, remoteDevice, this);
								isSearching = true;
								searchCount++;
							} catch (BluetoothStateException e) {
								System.err.println("Unable to search for services on " + getDeviceName());
								e.printStackTrace();
								isSearching = false;
								searchCount--;
							} finally {
							}
						}
					}
				} else {
					try {
						StreamConnection streamConnection = (StreamConnection) Connector.open(connectionURL);
						monitor = new DeviceMonitor(streamConnection, remoteDevice);
						monitor.start();
						if (lastMessage != null) {
							monitor.sendMessage(lastMessage);
							lastMessage = null;
						}
						if (lastFile != null) {
							monitor.sendFile(lastFile);
							lastFile = null;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// Sleep a bit before continuing.
			try {
				sleep(12000);
			} catch (InterruptedException e) {

			}
		}
		if (monitor != null && monitor.isAlive()) {
			monitor.shutdown();
		}
	}

	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * @param done
	 *            the done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public void deviceDiscovered(RemoteDevice arg0, DeviceClass arg1) {
	}

	@Override
	public void inquiryCompleted(int arg0) {
	}

	@Override
	public void serviceSearchCompleted(int arg0, int arg1) {
		isSearching = false;
		searchCount--;
	}

	@Override
	public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
		if (servRecord != null && servRecord.length > 0) {
			connectionURL = servRecord[0].getConnectionURL(0, false);
			System.out.println("Found: " + connectionURL + " on device " + getDeviceName());
		}
	}

	public String getDeviceName() {
		String ret = "";
		try {
			ret = remoteDevice.getFriendlyName(false);
			if (ret == null || ret.trim().length() == 0) {
				ret = remoteDevice.getBluetoothAddress();
			}
		} catch (Exception e) {
			return remoteDevice.getBluetoothAddress();
		}
		return ret;
	}

}
