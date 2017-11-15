package battleship_client;

public class Submarine extends Ship{
	
	public Submarine(){
		super();
		length = 2;
		name = "Submarino";
		mark = 'S';
	}

	@Override
	public String toString() {
		return "Submarine [name=" + name + ", length=" + length + "]";
	}	
}