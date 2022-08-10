package com.frc1706.scouting.bluetooth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;

public class DeviceMonitor extends Thread {
	private final StreamConnection connection;
	private boolean done = false;
	private String message = null;
	private RemoteDevice device = null;
	private String deviceName = null;
	private final ArrayList<File> files = new ArrayList<File>();

	public void shutdown() {
		done = true;
	}

	public DeviceMonitor(StreamConnection conn, RemoteDevice remoteDevice) {
		connection = conn;
		device = remoteDevice;
		try {
			deviceName = device.getFriendlyName(false);
		} catch (IOException ioe) {
			deviceName = device.getBluetoothAddress();
		}
	}

	public void sendMessage(String message) {
		System.err.println("Message queued for device " + message);
		this.message = message;
	}

	public void sendFile(File file) {
		System.err.println("File " + file.getName() + " queued for device " + file.getName());
		this.files.add(file);
	}

	public static void combineFiles() {
		System.out.println("Preparing to combine data files.");
		File dataDir = new File(App.baseDirField.getText(), App.eventNameField.getText());
		if (!dataDir.exists()) {
			return;
		}
		File catPy = new File(dataDir, "cat.py");
		if (!catPy.exists()) {
			File catPySrc = new File(Paths.get(".").toAbsolutePath().normalize().toFile(), "cat.py");
			if (!catPySrc.exists()) {
				System.err.println("Unable to locate cat.py at " + catPySrc.getAbsolutePath());
				return;
			}
			try {
				Files.copy(catPySrc.toPath(), catPy.toPath());
			} catch (IOException e) {
				System.err.println("Unable to copy cat.py to " + catPy.getAbsolutePath());
				e.printStackTrace();
				return;
			}
		}
		File blankCSV = new File(dataDir, ";.csv");
		if (!blankCSV.exists()) {
			File blankCSVSrc = new File(Paths.get(".").toAbsolutePath().normalize().toFile(), ";.csv");
			if (!blankCSVSrc.exists()) {
				return;
			}
			try {
				Files.copy(blankCSVSrc.toPath(), blankCSV.toPath());
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		ProcessBuilder pb = new ProcessBuilder("python", "cat.py", App.eventNameField.getText() + "_cat.csv");
		pb.directory(dataDir);
		/*try {
			System.out.println("Running cat.py");
			Process p = pb.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String inStr;
			while ((inStr = in.readLine()) != null) {
				System.out.println(inStr);
			}
			p.waitFor();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("Finished combining files.");
		}*/
	}

	@Override
	public void run() {
		OutputStream outStream = null;
		InputStream inStream = null;
		try {
			System.out.println("Device " + device.getBluetoothAddress() + ":" + deviceName + " has come online.");
			outStream = connection.openOutputStream();
			inStream = connection.openInputStream();
			PrintWriter pWriter = new PrintWriter(new OutputStreamWriter(outStream));
			BufferedReader bReader2 = new BufferedReader(new InputStreamReader(inStream));
			while (!done) {
				int extraBytesAvailable = inStream.available();
				if (extraBytesAvailable > 0) {
					byte[] buf = new byte[extraBytesAvailable];
					int exBytesRead = inStream.read(buf, 0, extraBytesAvailable);
					System.err.println("Extra input found: " + new String(buf, 0, exBytesRead));
				}
				if (message != null) {
					System.err.println("Sending toast message: " + message);
					pWriter.println("toast " + message);
					pWriter.flush();
					message = null;
				}
				if (files.size() > 0) {
					File f = files.remove(0);
					if (f != null) {
						try {
							System.err.println("Sending file " + f.getName() + " to device " + deviceName);
							String contents = readFile(f);
							if ((contents != null) && (contents.length() > 0)) {
								pWriter.println("put " + f.getName() + " " + contents.length());
								pWriter.write(contents);
								pWriter.flush();
							}
						} catch (Exception e) {
							files.add(f);
						}
					}
				}
				pWriter.println("list");
				pWriter.flush();

				// read response
				String lineRead;
				Collection<RemoteFile> remoteFiles = new ArrayList<RemoteFile>();
				while ((lineRead = bReader2.readLine()) != null) {
					System.out.println(deviceName + ":" + lineRead);
					if ("---".equalsIgnoreCase(lineRead)) {
						break;
					} else {
						String[] parts = lineRead.split(" ");
						if (parts.length == 3) {
							int fileLength = Integer.parseInt(parts[0]);
							String checksum = parts[1];
							String name = parts[2];
							RemoteFile file = new RemoteFile(name, fileLength, checksum);
							remoteFiles.add(file);
						}
					}
				}

				if (remoteFiles.size() > 0) {
					for (RemoteFile file : remoteFiles) {
						File dataDir = new File(App.baseDirField.getText(), App.eventNameField.getText());
						if (!dataDir.exists()) {
							dataDir.mkdirs();
						}
						File f = new File(dataDir, file.getName());
						if (!f.exists() || f.length() != file.getLength()) {
							// Need to download file from device
							pWriter.println("get " + f.getName());
							pWriter.flush();

							char[] cbuf = new char[file.getLength()];
							int bytesRead = 0;
							StringBuffer sb = new StringBuffer();
							while (sb.length() < file.getLength()) {
								bytesRead = bReader2.read(cbuf);
								if (bytesRead > 0) {
									sb.append(cbuf, 0, bytesRead);
								}
							}
							file.setContents(sb.toString());

							// If the file came correctly, then we write it to
							// our file system
							if (file.isValid()) {
								FileWriter out = new FileWriter(f);
								out.write(file.getContents());
								out.flush();
								out.close();

								pWriter.println("delete " + file.getName());
								pWriter.flush();
							}
						} else {
							System.out.println("File " + file.getName() + " from tablet " + deviceName
									+ " already exists locally.");
						}
					}
				}
				try {
					sleep(12000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException ioe) {
			if (ioe.getMessage().equals("")) {
				ioe.printStackTrace();
			}
		} finally {
			System.out.println("Device " + device.getBluetoothAddress() + ":" + deviceName + " has gone offline.");
			combineFiles();
			if (inStream != null) {
				try {
					inStream.close();
				} catch (Exception e) {
				}
			}
			if (outStream != null) {
				try {
					outStream.close();
				} catch (Exception e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
				}
			}
		}

	}

	private static String readFile(File f) {
		try {
			FileReader reader = new FileReader(f);
			char[] buffer = new char[(int) f.length()];
			reader.read(buffer, 0, (int) f.length());
			reader.close();
			return new String(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
