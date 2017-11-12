package battleship;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Sala implements Runnable {
	private String nome;
	private Cliente dono;
	private Cliente convidado;

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

		printStreamDono.println("O jogo contra " + convidado.getNome() + " começou!");
		printStreamConvidado.println("O jogo contra " + dono.getNome() + " começou!");

	}

}
