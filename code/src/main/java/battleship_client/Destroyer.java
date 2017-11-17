package battleship_client;

public class Destroyer extends Ship {

	public Destroyer() {
		super();
		length = 3;
		name = "Contratorpedeiro";
		mark = 'C';
	}

	@Override
	public String toString() {
		return "Destroyer [name=" + name + ", length=" + length + "]";
	}
}