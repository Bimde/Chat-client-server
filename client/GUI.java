package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	private JFrame frame;

	public GUI(Client client) {
		super(new BorderLayout());
		this.frame = new JFrame("MSN Messenger++");
		this.frame.add(this);
		this.frame.setVisible(true);
		this.client = client;
		this.messages = new ArrayList<Message>();
		this.messageArea = new JTextArea(50, 50);
		this.messageArea.setEditable(false);

		this.scroll = new JScrollPane(this.messageArea);
		this.add(this.scroll, BorderLayout.CENTER);

		JPanel bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(600, 38));
		this.add(bottom, BorderLayout.SOUTH);
		this.input = new JTextField(40);
		bottom.add(this.input);
		this.send = new JButton("Send message");
		this.send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUI.this.sendMessage();
			}
		});
		bottom.add(this.send);
		this.frame.pack();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public String getName() {
		return this.name = JOptionPane.showInputDialog("Enter your name: ");
	}

	public void addMessage(String name, String message) {
		System.out.println("Adding message to gui: " + name + ": " + message);
		Message temp = new Message(name, message);
		this.messages.add(temp);
		this.displayMessage(temp);
	}

	public void displayMessage(Message msg) {
		System.out.println("Adding message to text area: " + msg.name + ": " + msg.message);
		this.messageArea.append(msg.name + ":\n" + msg.message + "\n");
	}

	private void sendMessage() {
		String message = this.input.getText().trim();
		System.out.println("Sending message from gui: " + message);
		if (!message.equals(""))
			this.client.sendMessage(message);
	}
}
