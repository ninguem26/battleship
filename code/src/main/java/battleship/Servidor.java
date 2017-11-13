package battleship;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Servidor implements Runnable {

	private static List<Sala> salas = new ArrayList<Sala>();
	private Socket socket;
	private Cliente cliente;
	private Scanner scanner;
	private PrintStream printStream;

	public Servidor(Socket socket) {
		this.socket = socket;

		this.cliente = new Cliente();
		this.cliente.setSocket(socket);

		this.scanner = null;
		this.printStream = null;

		try {
			printStream = new PrintStream(socket.getOutputStream());
			scanner = new Scanner(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		String mensagemTexto;
		Mensagem mensagem;

		// Nome do cliente

		mensagemTexto = scanner.nextLine();
		mensagem = Mensagem.trataMensagem(mensagemTexto);

		if (mensagem.getCategoria() == CategoriaMensagem.SET_NAME) {
			cliente.setNome(mensagem.getValue());
		}

		System.out.println("Cliente " + cliente.getNome());

		// Sala

		while (mensagem.getCategoria() != CategoriaMensagem.EXIT) {
			mensagemTexto = scanner.nextLine();
			mensagem = Mensagem.trataMensagem(mensagemTexto);

			System.out.println(mensagem);

			switch (mensagem.getCategoria()) {
			case OPEN_ROOM: {

				Sala sala = new Sala(mensagem.getValue(), cliente);

				salas.add(sala);

				System.out.println("Sala " + sala.getNome() + " criada");

				return;

				// Thread.currentThread().interrupt();
				// break;
			}
			case GET_ROOMS: {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < salas.size(); i++) {
					Sala sala = salas.get(i);
					if (sala != null && !sala.cheia()) {
						sb.append(i + " " + sala.getNome() + " " + sala.getDono().getNome());
						sb.append("#");
					}

				}

				printStream.println(sb.toString());
				break;
			}
			case ENTER_ROOM: {
				int n = Integer.parseInt(mensagem.getValue());

				Sala sala = salas.get(n);

				salas.remove(sala);

				sala.setConvidado(cliente);

				new Thread(sala).start();

				return;
				// break;
			}

			case EXIT: {
				break;
			}
			default:
				System.err.println("Mensagem inválida");
				break;
			}

		}

	}

}
