package chatCliente;
import java.io.*;
import java.net.*;

public class HiloLectura extends Thread {
	private BufferedReader reader;
	private MiChat2 cliente;

	public HiloLectura(Socket socket, MiChat2 cliente) {
		this.cliente = cliente;

		try {
			InputStream input = socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input));
		} catch (IOException ex) {
			System.out.println("Error en el input stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}


	public void run() {
		while (true) {
			try {
				String respuesta = reader.readLine();
				cliente.getCuadroTexto().append("\n"+respuesta);;

				/*if (cliente.getUserName() != null) {
					cliente.getCuadroTexto().append("<" + cliente.getUserName() + "> ");
				}*/
			} catch (IOException ex) {
				cliente.getCuadroTexto().append("Error leyendo del servidor : " + ex.getMessage());
				//System.out.println("Error reading from server: " + ex.getMessage());
				ex.printStackTrace();
				break;
			}
		}
	}
}