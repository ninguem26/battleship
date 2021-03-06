package battleship_server;
/*
 * Enum de mensagens tratadas pelo servidor.
 * Cada mensagem tem um atributo regex usado na sua validação. 
 */
public enum CategoriaMensagem {
	SET_NAME("SET_NAME (\\w+)"),
	OPEN_ROOM("OPEN_ROOM (\\w+)"),
	GET_ROOMS("GET_ROOMS"),
	ENTER_ROOM("ENTER_ROOM (\\w+)"),
	EXIT("EXIT"),
	BOARD("BOARD (~|S|N|P|C){100}"),
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
