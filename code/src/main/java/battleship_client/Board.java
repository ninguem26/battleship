package battleship_client;

/**
 * Classe com métodos para manipular o tabuleiro e as peças do jogo.
 **/
public class Board {

	static int N_COLUMNS = 10;
	static int N_LINES = 10;

	private char[][] tabuleiro;

	private int maxSubmarines = 4;
	private int maxDestroyers = 3;
	private int maxTankers = 2;
	private int maxACarriers = 1;

	public Board() {
		tabuleiro = new char[10][10];
		for (int i = 0; i < N_LINES; i++) {
			for (int j = 0; j < N_COLUMNS; j++) {
				tabuleiro[i][j] = '~';
			}
		}
	}

	public void addShip(int x, int y, char dir, Ship ship) {
		// x = x - 1;
		// y = y - 1;

		if (canAdd(ship)) {
			if (dir == 'H') {
				if (x + ship.getLength() <= N_COLUMNS) {
					if (canPutInPosition(x, y, ship.getLength(), dir)) {
						for (int i = 0; i < ship.getLength(); i++) {
							tabuleiro[y][x + i] = ship.getMark();
						}
						decreaseShipCounters(ship);
					}
				} else {
					System.out.println("O barco não pode ser colocado nesta posição!");
				}
			} else if (dir == 'V') {
				if (y + ship.getLength() <= N_LINES) {
					if (canPutInPosition(x, y, ship.getLength(), dir)) {
						for (int i = 0; i < ship.getLength(); i++) {
							tabuleiro[y + i][x] = ship.getMark();
						}
						decreaseShipCounters(ship);
					}
				} else {
					System.out.println("O barco não pode ser colocado nesta posição!");
				}
			} else {
				System.out.println("A direção do barco tem que ser 0 ou 1!");
			}
		}
	}

	public void markBoard(int x, int y, char ship) {
		tabuleiro[x][y] = ship;
	}

	public boolean canAttack(int x, int y) {
		if (!(x >= 0 && x < N_COLUMNS) || !(y >= 0 && y < N_LINES)) {
			System.out.println("Posição (" + x + ", " + y + ") fora dos limites do tabuleiro!");
			return false;
		}

		if (tabuleiro[x][y] != '~') {
			System.out.println("Posição (" + x + ", " + y + ") já atacada");
			return false;
		}

		return true;
	}

	private void decreaseShipCounters(Ship ship) {
		if (ship.getClass().equals(Submarine.class)) {
			maxSubmarines--;
		}
		if (ship.getClass().equals(Destroyer.class)) {
			maxDestroyers--;
		}
		if (ship.getClass().equals(Tanker.class)) {
			maxTankers--;
		}
		if (ship.getClass().equals(AircraftCarrier.class)) {
			maxACarriers--;
		}
	}

	private boolean canAdd(Ship ship) {
		if (ship.getClass().equals(Submarine.class) && maxSubmarines > 0) {
			return true;
		}
		if (ship.getClass().equals(Destroyer.class) && maxDestroyers > 0) {
			return true;
		}
		if (ship.getClass().equals(Tanker.class) && maxTankers > 0) {
			return true;
		}
		if (ship.getClass().equals(AircraftCarrier.class) && maxACarriers > 0) {
			return true;
		}

		System.out.println("Muitos barcos deste tipo já foram adicionados!");
		return false;
	}

	private boolean canPutInPosition(int x, int y, int length, char dir) {
		if (dir == 'H') {
			for (int i = 0; i < length; i++) {
				if (tabuleiro[y][x + i] != '~') {
					System.out.println("Posição (" + x + ", " + y + ") já ocupada!");
					return false;
				}
			}
		} else if (dir == 'V') {
			for (int i = 0; i < length; i++) {
				if (tabuleiro[y + i][x] != '~') {
					System.out.println("Posição (" + x + ", " + y + ") já ocupada!");
					return false;
				}
			}
		}

		return true;
	}

	public void render() {
		System.out.println("****************************************");
		System.out.println("**********Monte seu tabuleiro!**********");
		System.out.println("****************************************");
		System.out.println();
		System.out.println("S - Submarinos: 		" + maxSubmarines);
		System.out.println("C - Contratorpedeiro: 		" + maxDestroyers);
		System.out.println("N - Navio-Tanque: 		" + maxTankers);
		System.out.println("P - Porta-Aviões: 		" + maxACarriers);
		System.out.println();
		System.out.println("  0 1 2 3 4 5 6 7 8 9");
		for (int i = 0; i < N_LINES; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < N_COLUMNS; j++) {
				System.out.print(tabuleiro[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println();
		System.out.println("coluna, linha, direção(H - horizontal, V - vertical), navio");
	}

	public void render(String mensagem) {
		System.out.println(mensagem);
		System.out.println();
		System.out.println("  0 1 2 3 4 5 6 7 8 9");
		for (int i = 0; i < N_LINES; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < N_COLUMNS; j++) {
				System.out.print(tabuleiro[i][j] + " ");
			}
			System.out.println("");
		}
	}

	public boolean confirmEdit() {
		return (maxSubmarines + maxACarriers + maxTankers + maxDestroyers) == 0;
	}

	public void clear() {
		for (int i = 0; i < N_LINES; i++) {
			for (int j = 0; j < N_COLUMNS; j++) {
				tabuleiro[i][j] = '~';
			}
		}

		maxSubmarines = 4;
		maxDestroyers = 3;
		maxTankers = 2;
		maxACarriers = 1;
	}

	public String matrixToString() {
		String boardString = new String();
		for (int i = 0; i < N_LINES; i++) {
			for (int j = 0; j < N_COLUMNS; j++) {
				boardString = boardString + tabuleiro[i][j];
			}
		}
		return boardString;
	}
}