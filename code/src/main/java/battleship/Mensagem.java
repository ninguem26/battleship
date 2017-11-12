package battleship;

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

}
