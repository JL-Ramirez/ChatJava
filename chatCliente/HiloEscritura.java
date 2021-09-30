package chatCliente;
import java.io.*;
import java.net.*;

public class HiloEscritura extends Thread {
	private PrintWriter writer;
	private Socket socket;
	private MiChat2 cliente;

	public HiloEscritura(Socket socket, MiChat2 cliente) {
		this.socket = socket;
		this.cliente = cliente;

		try {
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
		} catch (IOException ex) {
			cliente.getCuadroTexto().append("Error en el output stream : " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void run() {

			String userName = cliente.getNombre().getText();
			cliente.setUserName(userName);
			writer.println(userName);

			String text;
			
			text = cliente.getTexto().getText();
			writer.println(text);
			if(text.equals("Adios")) {
				cliente.dispose();
			}

	}
}