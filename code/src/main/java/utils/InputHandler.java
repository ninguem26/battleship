package utils;

import java.util.Scanner;
import java.util.regex.Pattern;

import battleship_client.AircraftCarrier;
import battleship_client.Destroyer;
import battleship_client.Ship;
import battleship_client.Submarine;
import battleship_client.Tanker;

public class InputHandler {

	private static final Pattern IPV4_PATTERN = Pattern.compile(
	        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	private Scanner scanner;
	
	public InputHandler(){
		scanner = new Scanner(System.in);
	}
	
	public String getIpAddress(){
		String ipAddress;
		
		do{
			System.out.println("Informe o endereço IP do servidor: ('exit' para sair)");
			ipAddress = getTextInput();
			
			if(validateIp(ipAddress)){
				break;
			}
		}while(!ipAddress.equals("exit"));
		
		return ipAddress;
	}
	
	public String getAddShipInput(){
		String command;
		String[] data;
		
		do{
			System.out.println("Informe a posição, direção e tipo do navio no tabuleiro:");
			command = getTextInput();
			
			if(command.equals("confirm") || command.equals("clear")){
				break;
			}
			
			data = command.split(" ");
			
		}while(!validateAddShip(data));
		
		return command;
	}
	
	public String getAttackInput(){
		String command;
		String[] data;
		
		do{
			System.out.println("Informe a posição do ataque:   (linha, coluna)");
			command = getTextInput();
			
			data = command.split(" ");
			
		}while(!validateAttack(data));
		
		return command;
	}
	
	public String getTextInput(){
		return scanner.nextLine();
	}
	
	public String getTextInput(String message){
		System.out.println(message);
		return scanner.nextLine();
	}
	
	public int getCoordX(String command){
		return Integer.parseInt(command.split(" ")[0]);
	}
	
	public int getCoordY(String command){
		return Integer.parseInt(command.split(" ")[1]);
	}
	
	public int getDir(String command){
		return Integer.parseInt(command.split(" ")[2]);
	}
	
	public Ship getShip(String command){
		String ship = command.split(" ")[3];
		
		if(ship.charAt(0) == 'S'){
			return new Submarine();
		}
		if(ship.charAt(0) == 'C'){
			return new Destroyer();
		}
		if(ship.charAt(0) == 'N'){
			return new Tanker();
		}
		if(ship.charAt(0) == 'P'){
			return new AircraftCarrier();
		}
		
		return null;
	}
	
	private static boolean validateIp(final String ip) {
	    return IPV4_PATTERN.matcher(ip).matches();
	}
	
	private static boolean validateAddShip(final String[] commands){
		if(commands.length > 4){
			System.out.println("Foram informados dados demais!");
			return false;
		}else if(commands.length < 4){
			System.out.println("Dados necessários não informados!");
			return false;
		}else{
			try{
				int i = Integer.parseInt(commands[0]);
				int j = Integer.parseInt(commands[1]);
				int k = Integer.parseInt(commands[2]);
			}catch(Exception e){
				System.out.println("Um dos dados está incorreto!");
				return false;
			}
			
			String ship = commands[3];
			
			if(ship.length() > 1 || (ship.charAt(0) != 'S' && ship.charAt(0) != 'P' && 
					ship.charAt(0) != 'C' && ship.charAt(0) != 'N')){
				System.out.println(ship.charAt(0) != 'S');
				System.out.println("Tipo de navio não definido!");
				return false;
			}
		}
		
		return true;
	}
	
	public boolean validateAttack(final String[] commands){
		if(commands.length > 2){
			System.out.println("Foram informados dados demais!");
			return false;
		}else if(commands.length < 2){
			System.out.println("Dados necessários não informados!");
			return false;
		}else{
			try{
				int i = Integer.parseInt(commands[0]);
				int j = Integer.parseInt(commands[1]);
			}catch(Exception e){
				System.out.println("Um dos dados está incorreto!");
				return false;
			}
		}
		
		return true;
	}
}