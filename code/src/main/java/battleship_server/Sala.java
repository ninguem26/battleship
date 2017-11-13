package battleship_server;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Sala implements Runnable {
	private String nome;
	private Cliente dono;
	private Cliente convidado;
	private Cliente vez;
	private Cliente espera;
	private static final int pontosGanha = 25;

	public Sala(String nome, Cliente dono) {
		this.dono = dono;
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cliente getDono() {
		return dono;
	}

	public void setDono(Cliente dono) {
		this.dono = dono;
	}

	public Cliente getConvidado() {
		return convidado;
	}

	public void setConvidado(Cliente convidado) {
		this.convidado = convidado;
	}

	public boolean cheia() {
		return this.getConvidado() != null;
	}

	@Override
	public String toString() {
		return "Sala " + nome + ", dono=" + dono.getNome();
	}

	public void run() {
		PrintStream printStreamDono = null;
		PrintStream printStreamConvidado = null;

		Scanner scannerDono = null;
		Scanner scannerConvidado = null;
		try {
			printStreamDono = new PrintStream(dono.getSocket().getOutputStream());
			printStreamConvidado = new PrintStream(convidado.getSocket().getOutputStream());
			scannerDono = new Scanner(dono.getSocket().getInputStream());
			scannerConvidado = new Scanner(convidado.getSocket().getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		printStreamDono.println("GAME_STARTED " + convidado.getNome());
		printStreamConvidado.println("GAME_STARTED " + dono.getNome());

		Thread thread1 = new Thread(new RecebeTabuleiro(dono));
		thread1.start();
		Thread thread2 = new Thread(new RecebeTabuleiro(convidado));
		thread2.start();

		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Pronto pra começar :)");

		vez = null;
		espera = null;

		PrintStream printStreamVez = null;
		PrintStream printStreamEspera = null;

		Scanner scannerVez = null;
		Scanner scannerEspera = null;

		while (dono.getPontuacao() != pontosGanha && dono.getPontuacao() != pontosGanha) {

			System.out.println("Dono: " + dono.getPontuacao());
			System.out.println("Convidado: " + convidado.getPontuacao());

			if (vez == dono) {
				vez = convidado;
				espera = dono;

				printStreamVez = printStreamConvidado;
				printStreamEspera = printStreamDono;

				scannerVez = scannerConvidado;
				scannerEspera = scannerDono;

				printStreamEspera.println("WAIT");
				printStreamVez.println("TURN");
			} else {
				vez = dono;
				espera = convidado;

				printStreamVez = printStreamDono;
				printStreamEspera = printStreamConvidado;

				scannerVez = scannerDono;
				scannerEspera = scannerConvidado;

				printStreamEspera.println("WAIT");
				printStreamVez.println("TURN");
			}

			String texto = scannerVez.nextLine();

			Mensagem msg = Mensagem.trataMensagem(texto);

			if (msg.getCategoria() == CategoriaMensagem.ATTACK) {
				int num = Integer.parseInt(msg.getValue());
				int linha = num / 10;
				int col = num % 10;

				int tiro = espera.getTabuleiro()[linha][col];

				if (tiro != 0) {
					printStreamVez.println("HIT " + msg.getValue() + " " + tiro);
					printStreamEspera.println("HIT " + msg.getValue() + " " + tiro);
					vez.setPontuacao(vez.getPontuacao() + 1);
				} else {
					printStreamVez.println("WATER " + msg.getValue());
					printStreamEspera.println("WATER " + msg.getValue());
				}
			}

		}

		if (dono.getPontuacao() == pontosGanha) {
			printStreamDono.println("WIN");
			printStreamConvidado.println("LOSE");
		} else {
			printStreamConvidado.println("WIN");
			printStreamDono.println("LOSE");
		}

	}

}
