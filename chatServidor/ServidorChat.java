package chatServidor;
import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorChat {
	private int port;
	private ArrayList<String> listaNombres = new ArrayList<>();
	private ArrayList<HiloServidor> listaHilos = new ArrayList<>();

	public ServidorChat(int port) {
		this.port = port;
	}

	public void execute() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Servidor de chat escuchando en el puerto : " + port);

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Nuevo usuario conectado.");

				HiloServidor newUser = new HiloServidor(socket, this);
				listaHilos.add(newUser);
				newUser.start();

			}

		} catch (IOException ex) {
			System.out.println("Error en el servidor : " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Sintaxis : java ChatServer <port-number>");
			System.exit(0);
		}

		int port = Integer.parseInt(args[0]);

		ServidorChat server = new ServidorChat(port);
		server.execute();
	}

	void broadcast(String message, HiloServidor excludeUser) {
		for (HiloServidor aUser : listaHilos) {
			{
				aUser.sendMessage(message);
			}
		}
	}

	void addUserName(String userName) {
		listaNombres.add(userName);
	}

	void removeUser(String userName, HiloServidor aUser) {
		boolean removed = listaNombres.remove(userName);
		if (removed) {
			listaHilos.remove(aUser);
			System.out.println("El usuario " + userName + " ha salido.");

		}
	}

	ArrayList<String> getlistaNombres() {
		return this.listaNombres;
	}

	boolean hasUsers() {
		return !this.listaNombres.isEmpty();
	}
}