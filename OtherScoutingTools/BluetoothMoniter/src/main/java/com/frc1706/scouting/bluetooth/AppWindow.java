package com.frc1706.scouting.bluetooth;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AppWindow {

	private JFrame frmBluetoothDeviceMonitor;
	private JTextField textField;
	private JTextField eventField;
	private JTextField baseDirField;

	private class DeviceIndex {
		String name;
		DeviceWatcher watcher;
		JLabel statusLabel;
		JButton sendButton;
	}

	private int lastRowUsed = 5;
	private final List<DeviceIndex> deviceList = new ArrayList<AppWindow.DeviceIndex>();
	private JLabel lblDataToSave;
	private JLabel lblEventName;
	private JLabel lblMessage;
	private JButton btnStartMonitoring;
	private JButton btnSendFile;

	// Create a file chooser
	final JFileChooser fc = new JFileChooser();

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public AppWindow(String eventName) {
		initialize(eventName);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String eventName) {
		frmBluetoothDeviceMonitor = new JFrame();
		frmBluetoothDeviceMonitor.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				frmBluetoothDeviceMonitor.setTitle("Shutting down...");
				App.stopSearching();
				try {
					Thread.sleep(30000);
				} catch (InterruptedException ee) {
				}
				System.exit(0);
			}
		});
		frmBluetoothDeviceMonitor.setMinimumSize(new Dimension(500, 300));
		frmBluetoothDeviceMonitor.setTitle("Bluetooth Device Monitor");
		frmBluetoothDeviceMonitor.setBounds(100, 100, 450, 300);
		frmBluetoothDeviceMonitor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		frmBluetoothDeviceMonitor.getContentPane().setLayout(gridBagLayout);

		lblDataToSave = new JLabel("Data to save files:");
		GridBagConstraints gbc_lblDataToSave = new GridBagConstraints();
		gbc_lblDataToSave.anchor = GridBagConstraints.WEST;
		gbc_lblDataToSave.insets = new Insets(0, 0, 5, 5);
		gbc_lblDataToSave.gridx = 0;
		gbc_lblDataToSave.gridy = 0;
		frmBluetoothDeviceMonitor.getContentPane().add(lblDataToSave, gbc_lblDataToSave);

		lblEventName = new JLabel("Event Name");
		GridBagConstraints gbc_lblEventName = new GridBagConstraints();
		gbc_lblEventName.anchor = GridBagConstraints.WEST;
		gbc_lblEventName.insets = new Insets(0, 0, 5, 0);
		gbc_lblEventName.gridx = 1;
		gbc_lblEventName.gridy = 0;
		frmBluetoothDeviceMonitor.getContentPane().add(lblEventName, gbc_lblEventName);

		baseDirField = new JTextField();
		GridBagConstraints gbc_baseDirField = new GridBagConstraints();
		gbc_baseDirField.insets = new Insets(0, 10, 5, 5);
		gbc_baseDirField.fill = GridBagConstraints.HORIZONTAL;
		gbc_baseDirField.gridx = 0;
		gbc_baseDirField.gridy = 1;
		frmBluetoothDeviceMonitor.getContentPane().add(baseDirField, gbc_baseDirField);
		baseDirField.setColumns(10);
		baseDirField.setText((new File(System.getProperty("user.home"), "ScoutingData")).getAbsolutePath());
		App.baseDirField = baseDirField;

		eventField = new JTextField();
		GridBagConstraints gbc_eventField = new GridBagConstraints();
		gbc_eventField.insets = new Insets(0, 10, 5, 0);
		gbc_eventField.fill = GridBagConstraints.HORIZONTAL;
		gbc_eventField.gridx = 1;
		gbc_eventField.gridy = 1;
		frmBluetoothDeviceMonitor.getContentPane().add(eventField, gbc_eventField);
		eventField.setColumns(10);
		eventField.setText(eventName);
		App.eventNameField = eventField;

		btnStartMonitoring = new JButton("Start Monitoring");
		btnStartMonitoring.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.startSearching();
				btnSendFile.setEnabled(true);
				btnStartMonitoring.setEnabled(false);
			}
		});

		btnSendFile = new JButton("Send File to All...");
		btnSendFile.setEnabled(false);
		btnSendFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(btnSendFile);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					if (file != null) {
						App.sendFileToAll(file);
					}
				} else {
					// log.append("Open command cancelled by user." + newline);
				}
			}
		});
		GridBagConstraints gbc_btnSendFile = new GridBagConstraints();
		gbc_btnSendFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnSendFile.gridx = 0;
		gbc_btnSendFile.gridy = 2;
		frmBluetoothDeviceMonitor.getContentPane().add(btnSendFile, gbc_btnSendFile);
		GridBagConstraints gbc_btnStartMonitoring = new GridBagConstraints();
		gbc_btnStartMonitoring.insets = new Insets(0, 0, 5, 0);
		gbc_btnStartMonitoring.gridx = 1;
		gbc_btnStartMonitoring.gridy = 2;
		frmBluetoothDeviceMonitor.getContentPane().add(btnStartMonitoring, gbc_btnStartMonitoring);

		lblMessage = new JLabel("Message:");
		lblMessage.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblMessage = new GridBagConstraints();
		gbc_lblMessage.anchor = GridBagConstraints.WEST;
		gbc_lblMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblMessage.gridx = 0;
		gbc_lblMessage.gridy = 3;
		frmBluetoothDeviceMonitor.getContentPane().add(lblMessage, gbc_lblMessage);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 10, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 4;
		gbc_textField.anchor = GridBagConstraints.PAGE_START;
		frmBluetoothDeviceMonitor.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);

		JButton btnSendMessage = new JButton("Send Message");
		btnSendMessage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.sendMessageToAll(textField.getText());
				textField.setText("");
			}
		});
		GridBagConstraints gbc_btnSendMessage = new GridBagConstraints();
		gbc_btnSendMessage.insets = new Insets(0, 0, 5, 0);
		gbc_btnSendMessage.gridx = 1;
		gbc_btnSendMessage.gridy = 4;
		gbc_btnSendMessage.anchor = GridBagConstraints.PAGE_START;
		frmBluetoothDeviceMonitor.getContentPane().add(btnSendMessage, gbc_btnSendMessage);

	}

	public void setVisible(boolean v) {
		frmBluetoothDeviceMonitor.setVisible(v);
	}

	public void addDeviceWatcher(DeviceWatcher watcher) {
		JLabel nameLabel = new JLabel(watcher.getDeviceName());
		GridBagConstraints gbc_name = new GridBagConstraints();
		gbc_name.insets = new Insets(0, 0, 5, 5);
		gbc_name.gridx = 0;
		gbc_name.gridy = ++lastRowUsed;
		frmBluetoothDeviceMonitor.getContentPane().add(nameLabel, gbc_name);

		DeviceIndex index = new DeviceIndex();
		index.name = watcher.getDeviceName();
		index.watcher = watcher;
		index.statusLabel = new JLabel("Offline");
		index.statusLabel.setForeground(Color.RED);
		GridBagConstraints gbc_status = new GridBagConstraints();
		gbc_status.insets = new Insets(0, 0, 0, 5);
		gbc_status.gridx = 1;
		gbc_status.gridy = lastRowUsed;
		frmBluetoothDeviceMonitor.getContentPane().add(index.statusLabel, gbc_status);

		index.sendButton = new JButton("...");
		index.sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				for (DeviceIndex idx : deviceList) {
					if (idx.sendButton == btn) {
						int returnVal = fc.showOpenDialog(btnSendFile);

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File file = fc.getSelectedFile();
							if (file != null) {
								idx.watcher.sendFile(file);
							}
						} else {
							// log.append("Open command cancelled by user." +
							// newline);
						}
					}
				}
			}
		});
		GridBagConstraints gbc_btnSendFile = new GridBagConstraints();
		gbc_btnSendFile.insets = new Insets(0, 0, 0, 5);
		gbc_btnSendFile.gridx = 2;
		gbc_btnSendFile.gridy = lastRowUsed;
		frmBluetoothDeviceMonitor.getContentPane().add(index.sendButton, gbc_btnSendFile);

		deviceList.add(index);
		frmBluetoothDeviceMonitor.pack();
	}

	public void updateDeviceStatus() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					for (DeviceIndex idx : deviceList) {
						idx.statusLabel.setText(idx.watcher.isOnline() ? "Online" : "Offline");
						idx.statusLabel.setForeground(idx.watcher.isOnline() ? Color.GREEN : Color.RED);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
