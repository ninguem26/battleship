package battleship_client;

public class AircraftCarrier extends Ship{

	public AircraftCarrier(){
		super();
		length = 5;
		name = "Porta-Aviões";
		mark = 'P';
	}

	@Override
	public String toString() {
		return "AircraftCarrier [name=" + name + ", length=" + length + "]";
	}
}