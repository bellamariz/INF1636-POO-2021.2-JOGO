package util;

public interface Observador {

	// esse método sera chamado ao receber uma mensagem
	public void update(Observavel o, Object ...args);
}

