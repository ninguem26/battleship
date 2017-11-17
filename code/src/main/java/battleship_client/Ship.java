package battleship_client;

/**
 * Classe base para os demais tipos de navios.
 **/
public class Ship {

	protected String name;
	protected int length;
	protected char mark;

	public String getName() {
		return name;
	}

	public int getLength() {
		return length;
	}

	public char getMark() {
		return mark;
	}
}