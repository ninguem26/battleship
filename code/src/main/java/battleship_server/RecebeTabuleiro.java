package battleship_server;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/*
 * Classe responsável por receber e montar os tabuleiros dos jogadores. 
 * Foi feita uma classe separada porque existe apenas uma thread de sala para os clientes, 
 * o que não permitiria que os clientes mandassem seus tabuleiros em paralelo.
 */
public class RecebeTabuleiro implements Runnable {
	private Cliente cliente;

	public RecebeTabuleiro(Cliente cliente) {
		this.cliente = cliente;
	}

	public void run() {
		PrintStream printStream = null;

		Scanner scanner = null;

		try {
			printStream = new PrintStream(cliente.getSocket().getOutputStream());
			scanner = new Scanner(cliente.getSocket().getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

		String texto = scanner.nextLine();

		Mensagem mensagem = Mensagem.trataMensagem(texto);
		System.out.println(mensagem);

		char[][] tabuleiroInt = new char[10][10];

		for (char i = 0; i < 100; i++) {
			char j = mensagem.getValue().charAt(i);
			tabuleiroInt[i / 10][i % 10] = j;
		}

		for (char i = 0; i < 10; i++) {
			for (char j = 0; j < 10; j++) {
				System.out.print(tabuleiroInt[i][j]);
			}
			System.out.println();
		}

		this.cliente.setTabuleiro(tabuleiroInt);

	}

}
