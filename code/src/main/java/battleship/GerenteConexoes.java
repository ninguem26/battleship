package battleship;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Classe responsável por receber conexões socket e repassar para a classe Servidor
 */
public class GerenteConexoes {

	public static void main(String[] args) throws IOException {

		ServerSocket servidor = new ServerSocket(12345);

		while (true) {

			Socket cliente = servidor.accept();

			System.out.println("Estabelecida conexão com " + cliente.getInetAddress().getHostAddress());

			Servidor threadServidor = new Servidor(cliente);

			new Thread(threadServidor).start();
		}
	}

}
