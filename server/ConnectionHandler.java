package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable {

	private Socket client;
	private String name;
	private PrintWriter output;
	private BufferedReader input;
	private Server main;

	public ConnectionHandler(Socket client, Server main) {
		this.client = client;
		this.main = main;
	}

	@Override
	public void run() {
		try {
			this.output = new PrintWriter(this.client.getOutputStream());
		} catch (IOException e1) {
			System.out.println("Error getting client's output stream");
			e1.printStackTrace();
		}
		try {
			this.input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
		} catch (IOException e) {
			System.out.println("Error getting client's input stream");
			e.printStackTrace();
		}
		try {
			this.name = this.input.readLine();
		} catch (IOException e) {
			System.err.println("Error getting client's name");
			e.printStackTrace();
		}

		while (true) {
			String message = "";
			try {
				message = this.input.readLine();
			} catch (IOException e) {
				System.err.println("Error getting new message from client");
				e.printStackTrace();
			}
			System.out.println("Sending message from handler: " + message);
			this.main.newMessage(this.name, message);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void newMessage(String name, String message) {
		System.out.println("Pushing messages to client " + this.name + ": From:" + name + " : " + message);
		this.output.println(name);
		this.output.println(message);
		this.output.flush();
	}
}
