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
			this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Error getting server's input stream");
			e.printStackTrace();
		}
		System.err.println("Connection Established");

		this.output.println("myname");
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
			this.gui.addMessage(name, message);
		}

	}

	public void sendMessage(String name, String message) {
		this.output.println(name);
		this.output.println(message);
	}

	public static void main(String[] args) {
		new Client();
	}
}
