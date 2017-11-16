package battleship_client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Pattern;

import utils.InputHandler;

public class App {

	private static Socket socket;
	private static PrintStream saida;
	private static Scanner scanner;
	private static InputHandler input = new InputHandler();
	private static String playerNome;
	private static String oponenteNome;
	private static Board tabuleiro;
	private static Board tabuleiroOponente;
	private static String turn;
	
	public static void main(String[] args) throws IOException {
		String startOpc;
		
		do{
			clearScreen();
			System.out.println("******************************");
			System.out.println("**********Battleship**********");
			System.out.println("******************************");
			System.out.println();
			System.out.println("1 - Start");
			System.out.println();

			startOpc = input.getTextInput("Selecione uma das opções:   ('exit' para sair)");
			
			if(startOpc.equals("1") || startOpc.equals("start")){
				ipScreen();
				startOpc = "exit";
			}
		}while(!startOpc.equals("exit"));
	}
	
	public static void ipScreen() throws IOException{
		System.out.println();
		String ipAddress = input.getIpAddress();
		System.out.println();
		
		if(!ipAddress.equals("exit")){
			try{
				socket = new Socket(ipAddress, 12345);
				saida = new PrintStream(socket.getOutputStream());
				scanner = new Scanner(socket.getInputStream());
				clearScreen();
				System.out.println("Você está conectado!!!");
				System.out.println();
				optionsScreen();
			}catch(UnknownHostException e){
				System.out.println("O servidor não responde.");
			}
		}
	}
	
	public static void optionsScreen(){
		Pattern nomePattern = Pattern.compile("[a-z|A-Z]*");
		boolean matches;
		
		String optionsOpt;
		
		do{
			playerNome = input.getTextInput("Informe seu nome:   (Nomes compostos e caracteres especiais são inválidos)");
			
			matches = nomePattern.matcher(playerNome).matches();
			if(!matches){
				System.out.println("Nome inválido!");
				System.out.println();
			}
		}while(!matches);
		
		clearScreen();
		
		saida.println("SET_NAME " + playerNome);
		scanner.nextLine();
		do{
			System.out.println("******************************");
			System.out.println("*************Menu*************");
			System.out.println("******************************");
			System.out.println();
			System.out.println("1 - Entrar em uma sala");
			System.out.println("2 - Criar uma sala");
			System.out.println();
			
			optionsOpt = input.getTextInput("Selecione uma das opções:   ('exit' para sair)");
			
			if(optionsOpt.equals("1") || optionsOpt.equals("entrar")){
				clearScreen();
				roomsScreen();
			}else if(optionsOpt.equals("2") || optionsOpt.equals("criar")){
				clearScreen();
				createRoomScreen();
			}
		}while(!optionsOpt.equals("exit"));
	}
	
	public static void roomsScreen(){
		String rooms;
		String roomOpt;
		
		saida.println("GET_ROOMS");
		rooms = scanner.nextLine();
		
		String[] roomsArray = rooms.split("\n");
		System.out.println(roomsArray.length);
		if(!rooms.isEmpty()){
			clearScreen();
			System.out.println(rooms);
				
			roomOpt = input.getTextInput("Selecione uma das salas disponíveis:   ('exit' para sair)");
			if(!roomOpt.equals("exit")){
				try{
					if(Integer.parseInt(roomOpt) >= 0 && Integer.parseInt(roomOpt) < rooms.split("\n").length){
						saida.println("ENTER_ROOM " + roomOpt);
						System.out.println("Entrando na sala...");
						oponenteNome = scanner.nextLine();	//Não remover essas linhas
						oponenteNome = scanner.nextLine();	//XGH forte aqui
						clearScreen();
						boardRoom();
					}else{
						clearScreen();
						System.out.println("Opção inválida!");
					}
				}catch(Exception e){
					clearScreen();
					System.out.println("Opção inválida!");
					System.out.println();
				}
			}
		}else{
			clearScreen();
			System.out.println("Nenhuma sala disponível!");
			System.out.println();
		}
	}
	
	public static void createRoomScreen(){
		Pattern nomePattern = Pattern.compile("[a-z|A-Z]*");
		boolean matches;
		String nome;
		
		do{
			nome = input.getTextInput("Informe o nome da sala:   (Nomes compostos e caracteres especiais são inválidos)");
			
			matches = nomePattern.matcher(nome).matches();
			if(!matches){
				System.out.println("Nome inválido!");
				System.out.println();
			}
		}while(!matches);
		
		saida.println("OPEN_ROOM " + nome);
		
		clearScreen();
		
		System.out.println("Sala criada! Aguardando oponente...");
		
		oponenteNome = scanner.nextLine();
		clearScreen();
		boardRoom();
	}
	
	public static void boardRoom(){
		tabuleiro = new Board();
		String command;
		
		do{
			tabuleiro.render();
			command = input.getAddShipInput();
			
			if(command.equals("clear")){
				clearScreen();
				tabuleiro.clear();
			}else{
				clearScreen();
				tabuleiro.addShip(input.getCoordX(command), 
						input.getCoordY(command), 
						input.getDir(command), 
						input.getShip(command));
			}
		}while(!command.equals("confirm") && !tabuleiro.confirmEdit());
		
		saida.println("BOARD " + tabuleiro.matrixToString());
		
		clearScreen();
		System.out.println("Aguardando adversário...");
		
		turn = scanner.nextLine();
		clearScreen();
		gameRoom();
	}
	
	public static void gameRoom(){
		tabuleiroOponente = new Board();
		String attack;
		String hit;
		
		do{
			if(turn.equals("TURN")){
				do{
					System.out.println("Sua vez!!!");
					System.out.println();
					tabuleiroOponente.render();
					attack = input.getAttackInput();
					
				}while(!tabuleiroOponente.canAttack(input.getCoordY(attack), input.getCoordY(attack)));
				
				String[] coord = attack.split(" ");
				saida.println("ATTACK " + coord[0] + coord[1]);
				hit = scanner.nextLine();
				System.out.println(hit);
				
				if(hit.split(" ")[0].equals("HIT")){
					clearScreen();
					tabuleiroOponente.markBoard(
							Integer.parseInt(coord[1]), 
							Integer.parseInt(coord[0]), 
							hit.split(" ")[2].charAt(0));
					System.out.println("Acertou embarcação em: (" + coord[1] + ", " + coord[2] + ")");
					System.out.println();
				}else{
					tabuleiroOponente.markBoard(
							Integer.parseInt(coord[1]), 
							Integer.parseInt(coord[0]), 
							'X');
					System.out.println("Tiro na água em: (" + coord[1] + ", " + coord[0] + ")");
					System.out.println();
				}
				
				turn = scanner.nextLine();
			}else if(turn.equals("WAIT")){
				tabuleiroOponente.render();
				System.out.println("Aguardando turno de " + oponenteNome.split(" ")[1]);
				
				hit = scanner.nextLine();
				System.out.println(hit);
				
				String wasHit = hit.split(" ")[0];
				String coords = hit.split(" ")[1];
				
				tabuleiro.markBoard(
						Integer.parseInt("" + coords.charAt(1)), 
						Integer.parseInt("" + coords.charAt(0)), 
						'X');
				
				if(wasHit.equals("HIT")){
					clearScreen();
					System.out.println("Oponente acertou embarcação em: (" + coords.charAt(1) + ", " + coords.charAt(0) + ")");
					System.out.println();
				}else{
					clearScreen();
					System.out.println("Oponente atirou na água em: (" + coords.charAt(1) + ", " + coords.charAt(0) + ")");
					System.out.println();
				}
				
				turn = scanner.nextLine();
			}
		}while(!turn.equals("WIN") || !turn.equals("LOSE"));
		
		if(turn.equals("WIN")){
			System.out.println("PARABÉNS, VOCÊ DERROTOU " + oponenteNome.split(" ")[1] + "!!!!!");
		}else{
			System.out.println(oponenteNome.split(" ")[1] + "Te derrotou. Mais sorte na próxima!");
		}
	}
	
	public static void clearScreen(){
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}