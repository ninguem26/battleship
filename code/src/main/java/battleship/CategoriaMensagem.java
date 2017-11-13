package battleship;

public enum CategoriaMensagem {
	SET_NAME("SET_NAME (\\w+)"),
	OPEN_ROOM("OPEN_ROOM (\\w+)"),
	GET_ROOMS("GET_ROOMS"),
	ENTER_ROOM("ENTER_ROOM (\\w+)"),
	EXIT("EXIT"),
	BOARD("BOARD \\d{100}\\b"),
	ATTACK("ATTACK \\d{2}\\b"),
	INVALIDA("");

	private String regex;

	public String getRegex() {
		return regex;
	}

	public void setCode(String regex) {
		this.regex = regex;
	}

	private CategoriaMensagem(String regex) {
		this.regex = regex;
	}
}
