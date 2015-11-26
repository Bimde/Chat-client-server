package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	private GUI gui;

	public Client() {
		this.gui = new GUI(this);
		try {
			this.socket = new Socket("127.0.0.1", 5000);
		} catch (Exception e) {
			System.out.println("Error conneciton to server");
			e.printStackTrace();
		}
		try {
			this.output = new PrintWriter(this.socket.getOutputStream());
		} catch (IOException e1) {
			System.out.println("Error getting server's output stream");
			e1.printStackTrace();
		}
		try {
			this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Error getting server's input stream");
			e.printStackTrace();
		}
		System.err.println("Connection Established");

		this.output.println(this.gui.getName());
		this.output.flush();

		while (true) {
			String name = "", message = "";
			try {
				name = this.input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				message = this.input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Pushing message to client: " + message);
			this.gui.addMessage(name, message);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void sendMessage(String message) {
		System.out.println("Sending message from client: " + message);
		this.output.println(message);
		this.output.flush();
	}

	public static void main(String[] args) {
		new Client();
	}
}
