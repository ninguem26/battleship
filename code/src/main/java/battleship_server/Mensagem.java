package battleship_server;

public class Mensagem {
	private CategoriaMensagem categoria;
	private String value;

	public Mensagem(CategoriaMensagem categoria) {
		this.categoria = categoria;
	}

	public Mensagem(CategoriaMensagem categoria, String value) {
		this.categoria = categoria;
		this.value = value;
	}

	public CategoriaMensagem getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaMensagem categoria) {
		this.categoria = categoria;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		if (value == null)
			return categoria.name();
		else
			return categoria.name() + " " + this.value;
	}

	public static Mensagem trataMensagem(String texto) {
		System.out.println("Mensagem para tratar: " + texto);

		Mensagem mensagem = null;
		if (texto.matches(CategoriaMensagem.SET_NAME.getRegex())) {
			String[] tokens = texto.split(" ");
			mensagem = new Mensagem(CategoriaMensagem.SET_NAME, tokens[1]);
		} else if (texto.matches(CategoriaMensagem.OPEN_ROOM.getRegex())) {
			String[] tokens = texto.split(" ");
			mensagem = new Mensagem(CategoriaMensagem.OPEN_ROOM, tokens[1]);
		} else if (texto.matches(CategoriaMensagem.ENTER_ROOM.getRegex())) {
			String[] tokens = texto.split(" ");
			mensagem = new Mensagem(CategoriaMensagem.ENTER_ROOM, tokens[1]);
		} else if (texto.matches(CategoriaMensagem.GET_ROOMS.getRegex())) {
			mensagem = new Mensagem(CategoriaMensagem.GET_ROOMS);
		} else if (texto.matches(CategoriaMensagem.EXIT.getRegex())) {
			mensagem = new Mensagem(CategoriaMensagem.EXIT);
		} else if (texto.matches(CategoriaMensagem.BOARD.getRegex())) {
			String[] tokens = texto.split(" ");
			mensagem = new Mensagem(CategoriaMensagem.BOARD, tokens[1]);
		} else if (texto.matches(CategoriaMensagem.ATTACK.getRegex())) {
			String[] tokens = texto.split(" ");
			mensagem = new Mensagem(CategoriaMensagem.ATTACK, tokens[1]);
		} else {
			mensagem = new Mensagem(CategoriaMensagem.INVALIDA);
		}

		return mensagem;
	}

	
}
