package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JPanel {

	private JTextArea messageArea;
	private JScrollPane scroll;
	private JTextField input;
	private JButton send;
	private ArrayList<Message> messages;
	private Client client;
	private String name;

	public GUI(Client client) {
		super(new BorderLayout());
		this.client = client;
		this.messages = new ArrayList<Message>();
		this.messageArea = new JTextArea();
		this.messageArea.setEditable(false);

		this.scroll = new JScrollPane(this.messageArea);
		this.add(this.scroll, BorderLayout.CENTER);

		JPanel bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(600, 200));
		this.add(bottom, BorderLayout.CENTER);
		this.send = new JButton("Send message");
		this.send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUI.this.sendMessage();
			}
		});
		bottom.add(this.send);
		this.input = new JTextField(50);
		bottom.add(this.input);
	}

	public void addMessage(String name, String message) {
		Message temp = new Message(name, message);
		this.messages.add(temp);
		this.displayMessage(temp);
	}

	public void displayMessage(Message msg) {
		this.messageArea.append(msg.name + ":\n" + msg.message + "\n");
	}

	private void sendMessage() {
		String message = this.input.getText().trim();
		if (!message.equals(""))
			this.client.sendMessage(this.name, message);
	}
}
