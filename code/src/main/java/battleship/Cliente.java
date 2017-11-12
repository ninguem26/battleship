package battleship;

import java.net.Socket;

public class Cliente {
	private String nome;
	private int[] tabuleiro;
	private Socket socket;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int[] getTabuleiro() {
		return tabuleiro;
	}

	public void setTabuleiro(int[] tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public String toString() {
		return "Cliente [nome=" + nome + "]";
	}

}
