package battleship_client;

public class Tanker extends Ship{

	public Tanker(){
		super();
		length = 4;
		name = "Navio-Tanque";
		mark = 'N';
	}

	@Override
	public String toString() {
		return "Tanker [name=" + name + ", length=" + length + "]";
	}
}