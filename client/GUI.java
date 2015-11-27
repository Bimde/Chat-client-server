package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class GUI extends JPanel implements KeyListener {

	private JTextPane messageArea;
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
		this.frame.setResizable(false);
		this.client = client;
		this.messages = new ArrayList<Message>();
		this.messageArea = new JTextPane();
		this.messageArea.setBackground(Color.LIGHT_GRAY);
		this.messageArea.setEditable(false);
		this.messageArea.setFont(new Font("Garamond", Font.PLAIN, 24));

		this.scroll = new JScrollPane(this.messageArea);
		this.scroll.setPreferredSize(new Dimension(600, 600));
		this.add(this.scroll, BorderLayout.CENTER);

		JPanel bottom = new JPanel(new BorderLayout());
		bottom.setPreferredSize(new Dimension(600, 40));
		this.add(bottom, BorderLayout.SOUTH);
		this.input = new JTextField(40);
		this.input.setFont(new Font("Helvetica", Font.PLAIN, 32));
		this.input.setFocusable(true);
		this.input.addKeyListener(this);
		bottom.add(this.input, BorderLayout.CENTER);
		this.send = new JButton("Send message");
		this.send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUI.this.sendMessage();
			}
		});
		bottom.add(this.send, BorderLayout.EAST);
		this.frame.pack();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.input.requestFocusInWindow();
	}

	public String getName() {
		String temp = "";
		do {
			temp = this.name = JOptionPane.showInputDialog("Enter your name: ");
		} while (temp == null || temp.trim().equals(""));
		return temp;
	}

	public void addMessage(String name, String message) {
		System.out.println("Adding message to gui: " + name + ": " + message);
		Message temp = new Message(name, message);
		this.messages.add(temp);
		this.displayMessage(temp);
	}

	public void displayMessage(Message msg) {
		System.out.println("Adding message to text area: " + msg.name + ": "
				+ msg.message);
		StyledDocument temp = this.messageArea.getStyledDocument();
		SimpleAttributeSet keyWord = new SimpleAttributeSet();

		if (msg.name.equals(this.name)) {
			SimpleAttributeSet right = new SimpleAttributeSet();
			StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
			StyleConstants.setForeground(keyWord, Color.BLUE);
			msg.name = "You";
		} else {
			StyleConstants.setForeground(keyWord, Color.WHITE);
		}
		StyleConstants.setBold(keyWord, true);
		try {
			temp.insertString(temp.getLength(), ">> " + msg.name + ":\n "
					+ msg.message + "\n", keyWord);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		this.messageArea.setCaretPosition(temp.getLength());
	}

	private void sendMessage() {
		String message = this.input.getText().trim();
		this.input.setText("");
		System.out.println("Sending message from gui: " + message);
		if (!message.equals(""))
			this.client.sendMessage(message);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		return;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			this.sendMessage();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}
}
