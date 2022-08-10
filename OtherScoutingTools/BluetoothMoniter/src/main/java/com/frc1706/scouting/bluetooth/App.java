package com.frc1706.scouting.bluetooth;

import com.frc1706.scouting.bluetooth.DeviceMonitor;

import java.awt.EventQueue;
import java.io.File;
import java.util.Calendar;

import javax.bluetooth.UUID;
import javax.swing.JTextField;

/**
 * Bluetooth Device Monitor.
 *
 */
public class App {

	static final UUID uuid = new UUID("0000107300001000800000805F9B34F7", false);
	public static String eventID = Calendar.getInstance().get(Calendar.YEAR) + "-STL";
	static AppWindow window;
	public static JTextField baseDirField;
	public static JTextField eventNameField;
	private static Searcher searcher;
	
	public static void main(String[] args) {
		System.out.println("Starting...");
		if (args.length > 0) {
			eventID = args[0];
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					window = new AppWindow(eventID);
					window.setVisible(true);
					DeviceMonitor.combineFiles();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public static void startSearching() {
		if (searcher == null) {
			searcher = new Searcher();
		}
		if (!searcher.isAlive()) {
			searcher.start();
		}
	}

	public static void stopSearching() {
		if (searcher != null) {
			searcher.shutdown();
		}
	}

	public static void sendMessageToAll(String message) {
		if (searcher != null) {
			searcher.sendMessageToAll(message);
		}
	}

	public static void sendFileToAll(File f) {
		if (searcher != null) {
			searcher.sendFileToAll(f);
		}
	}

}
