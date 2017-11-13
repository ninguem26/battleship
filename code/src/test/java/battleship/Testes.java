package battleship;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class Testes {

	@Test
	public void teste() throws UnknownHostException, IOException, InterruptedException {
		Socket socket1 = new Socket("localhost", 12345);
		Socket socket2 = new Socket("localhost", 12345);

		PrintStream ps1 = new PrintStream(socket1.getOutputStream());
		PrintStream ps2 = new PrintStream(socket2.getOutputStream());

		Scanner scanner1 = new Scanner(socket1.getInputStream());
		Scanner scanner2 = new Scanner(socket2.getInputStream());

		ps1.println("SET_NAME nome1");
		ps1.flush();
		
		TimeUnit.MILLISECONDS.sleep(100);
		
		ps1.println("OPEN_ROOM sala");
		ps1.flush();

		ps2.println("SET_NAME nome2");
		ps2.flush();
		
		TimeUnit.MILLISECONDS.sleep(100);
		
		ps2.println("ENTER_ROOM 0");
		ps2.flush();

		TimeUnit.MILLISECONDS.sleep(100);

		ps1.println(
				"BOARD 0123456789901234567889012345677890123456678901234556789012344567890123345678901223456789011234567890");
		ps1.flush();
		ps2.println(
				"BOARD 0123456789901234567889012345677890123456678901234556789012344567890123345678901223456789011234567890");
		ps2.flush();
		scanner1.nextLine();

	}
}
