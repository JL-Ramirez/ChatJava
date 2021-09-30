package chatCliente;

import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MiChat2 extends JFrame {

	private JPanel contentPane;
	private JTextField nombre;
	private JTextField texto;
	
	private String hostname;
	private int port;
	private String userName;
	
	static Socket socket;
	

	void setUserName(String userName) {
		this.userName = userName;
	}

	String getUserName() {
		return this.userName;
	}

	
	/**
	 * Launch the application.
	 */

	
	public static void main(String[] args) {
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MiChat2 frame = new MiChat2(hostname,port);
					frame.setVisible(true);
					try {
						socket = new Socket(hostname, port);
						System.out.println("Conectado al servidor de chat.");
						new HiloLectura(socket, frame).start();
					} catch (UnknownHostException ex) {
						System.out.println("Servidor no encontrado : " + ex.getMessage());
					} catch (IOException ex) {
						System.out.println("Error I/O : " + ex.getMessage());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	JTextArea cuadroTexto;
	
	public MiChat2(String hostname,int port) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
					texto.setText("Adios");
					new HiloEscritura(socket, MiChat2.this).start();
					System.exit(0);
					
				}
				catch (Exception e2){
					e2.printStackTrace();
				}
				
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Chat");
		lblNewLabel.setBounds(34, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
	    cuadroTexto = new JTextArea();
		cuadroTexto.setEditable(false);
		cuadroTexto.setBounds(34, 36, 500, 273);
		contentPane.add(cuadroTexto);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre :");
		lblNewLabel_1.setBounds(34, 347, 178, 14);
		contentPane.add(lblNewLabel_1);
		
		nombre = new JTextField();
		nombre.setBounds(255, 347, 279, 20);
		contentPane.add(nombre);
		nombre.setColumns(10);
		
		texto = new JTextField();
		texto.setColumns(10);
		texto.setBounds(255, 378, 279, 20);
		contentPane.add(texto);
		
		JLabel lblNewLabel_1_1 = new JLabel("Introduzca texto :");
		lblNewLabel_1_1.setBounds(34, 382, 178, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nombre.setEditable(false);
				new HiloEscritura(socket, MiChat2.this).start();
				
			/*	if(!nombre.getText().equals("") && !texto.getText().equals("")) {
					String textoAEnviar="<"+nombre.getText()+"> - "+texto.getText()+"\n";
					cuadroTexto.append(textoAEnviar);
					texto.setText("");
				}*/
					
			}
		});
		btnNewButton.setBounds(227, 417, 89, 23);
		contentPane.add(btnNewButton);
		
		this.hostname = hostname;
		this.port = port;
	}
	
	public JTextArea getCuadroTexto() {
		return cuadroTexto;
	}
	
	public JTextField getTexto() {
		return texto;
	}
	public JTextField getNombre() {
		return nombre;
	}
	
}
