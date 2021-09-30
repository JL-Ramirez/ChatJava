package chatServidor;
import java.io.*;
import java.net.*;

public class HiloServidor extends Thread {
	private Socket socket;
	private ServidorChat server;
	private PrintWriter writer;

	public HiloServidor(Socket socket, ServidorChat server) {
		this.socket = socket;
		this.server = server;
	}

	public void run() {
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);

			printUsers();

			String userName = reader.readLine();
			server.addUserName(userName);
			String basura=reader.readLine();
			String serverMessage = "Nuevo usuario conectado : " + userName;
			server.broadcast(serverMessage, this);
			server.broadcast("<"+userName+"> "+basura,this);
			String clientMessage,clientMessage2,serverMessage2;

			do {
				clientMessage = reader.readLine();
				serverMessage = "<" + clientMessage + "> ";
				clientMessage2 = reader.readLine();
				serverMessage2 = serverMessage+clientMessage2;
				System.out.println(serverMessage2);
				server.broadcast(serverMessage2, this);

			} while (!clientMessage2.equals("Adios"));

			server.removeUser(userName, this);
			socket.close();

			serverMessage = userName + " ha salido.";
			server.broadcast(serverMessage, this);

		} catch (IOException ex) {
			System.out.println("Error en HiloServidor : " + ex.getMessage());
			ex.printStackTrace();
		}
	}


	void printUsers() {
		if (server.hasUsers()) {
			writer.println("Usuarios conectados : " + server.getlistaNombres());
		} else {
			writer.println("No hay usuarios conectados.");
		}
	}


	void sendMessage(String message) {
		writer.println(message);
	}
}