package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	ArrayList<ConnectionHandler> list;

	public Server() {
		this.list = new ArrayList<ConnectionHandler>();
		ServerSocket socket = null;

		try {
			socket = new ServerSocket(5000);
		} catch (IOException e) {
			System.err.println("Error starting server.");
			e.printStackTrace();
		}
		while (true) {
			System.err.println("Waiting for client to connect...");
			try {
				Socket client = socket.accept();
				ConnectionHandler temp = new ConnectionHandler(client, this);
				new Thread(temp).start();
				this.list.add(temp);
			} catch (Exception e) {
				System.err.println("Error connecting to client " + this.list.size());
				e.printStackTrace();
			}
			System.err.println("Client " + this.list.size() + " connected.");
		}
	}

	protected synchronized void newMessage(String name, String message) {
		System.out.println("Pushing messages to handlers: " + name + ": " + message);
		for (int i = 0; i < this.list.size(); i++) {
			this.list.get(i).newMessage(name, message);
		}
	}

	public static void main(String[] args) {
		new Server();
	}
}
