package ru.spbstu.telematics.student_Nikitin.MulticastChat;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MulticastSocket;
import javax.swing.*;

public class ChatUserAuthorization implements ActionListener {
	
	TextField _userName;
	Label _lable;
	JButton _enterToChat;
	JFrame _frame = new JFrame("Chat");
	JPanel _pane = new JPanel(new GridBagLayout());
	
	public void init() {
		
		_pane.setPreferredSize(new Dimension(400, 50));
		
		GridBagConstraints c = new GridBagConstraints();
		
		_userName = new TextField(20);
		_enterToChat = new JButton("Enter");
		_lable = new Label("Enter your name:");

		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(5,5,5,5);
		_pane.add(_lable, c);
		
	    c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(5,5,5,5);
		_pane.add(_userName, c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(5,5,5,5);
	    _pane.add(_enterToChat, c);
	    _enterToChat.addActionListener(this);
		
		_frame.getContentPane().add(_pane);
		_frame.pack();
		_frame.setVisible(true);

		_userName.addActionListener(this);
	}

	public void actionPerformed(ActionEvent action) {
		
		String userName = _userName.getText();
		_frame.remove(_pane);
		try {
			MulticastChat chat = new MulticastChat(userName, _frame);
			chat._multicastSocket = new MulticastSocket(chat._chatPort);
			chat._multicastSocket.joinGroup(chat._chatAddress);
			chat.sendMessage("Внимание! Пользователь " + chat._userName + " вошел в чат.");
			Thread multicastChat = new Thread(chat);
			multicastChat.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	public static void main(String[] args) throws Exception{
			ChatUserAuthorization autentification = new ChatUserAuthorization();
			autentification.init();
	}
}
