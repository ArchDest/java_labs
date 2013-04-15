package ru.spbstu.telematics.student_Nikitin.MulticastChat;

import javax.swing.*;
import javax.swing.text.Document;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;


public class MulticastChat implements Runnable, ActionListener {

	static final String CHAT_ADDRESS = "224.0.0.1";
	static final int CHAT_PORT = 1234;
	static final int MAX_MESSAGE_SIZE = 500;	
	static final int CHAT_HISTORY_SIZE = 100;
	
	InetAddress _chatAddress;
	int _chatPort;
	
	String _userName;

	MulticastSocket _multicastSocket;

	int _countOfLineInChatHistory = 0;

	JTextField _messageField;
	JTextArea _chatArea;

	public MulticastChat(String userName, JFrame frame) throws Exception {
		
		_chatAddress = InetAddress.getByName(CHAT_ADDRESS);
		_chatPort = CHAT_PORT;
		_userName = userName;
		
		JPanel pane = new JPanel(new GridBagLayout());
		pane.setPreferredSize(new Dimension(600, 600));
		
		GridBagConstraints c = new GridBagConstraints();

		_chatArea = new JTextArea(37,53);
		c.anchor = GridBagConstraints.NORTHWEST;
		_chatArea.setEditable(false);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;		
		c.insets = new Insets(5,5,0,0);
	    pane.add(new JScrollPane(_chatArea), c);
	    
	    _messageField = new JTextField(40);
	    c.anchor = GridBagConstraints.SOUTHWEST;
		c.insets = new Insets(5,5,7,5);
	    pane.add(_messageField, c);
	    
	    JButton sendButton = new JButton("Send Message");
	    c.anchor = GridBagConstraints.SOUTHEAST;
		c.insets = new Insets(4,5,5,9);
	    pane.add(sendButton, c);
	    sendButton.addActionListener(this);
	    
		frame.getContentPane().add(pane);
		frame.pack();
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				sendMessage("Внимание! Пользователь " + _userName + " вышел из чата.");
				System.exit(0);
			}
		});

		_messageField.addActionListener(this);
		_messageField.requestFocus();
	}

	public void sendMessage(String text) {
		byte[] data = new byte[MAX_MESSAGE_SIZE];
		DatagramPacket packet = new DatagramPacket(data, 0, 0, _chatAddress, _chatPort);
		data = text.getBytes();
		packet.setData(data, 0, data.length);
		try {
			_multicastSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent event) {
		sendMessage("" + _userName + ": " + _messageField.getText());
		_messageField.setText(null);
	}

	public void run() {
		
		byte[] massage = new byte[MAX_MESSAGE_SIZE];
		DatagramPacket packet = new DatagramPacket(massage, 0, massage.length);

			while (true) {
				packet.setData(massage, 0, massage.length);
				try {
					_multicastSocket.receive(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (_countOfLineInChatHistory >= CHAT_HISTORY_SIZE) {
					_chatArea.setText(null);
					_countOfLineInChatHistory = 0;
				}
				_chatArea.append(new String(massage, 0, packet.getLength()) + System.getProperty("line.separator"));
				Document doc = _chatArea.getDocument();
				_chatArea.select(doc.getLength(), doc.getLength());
				_countOfLineInChatHistory++;
			}
	}
}