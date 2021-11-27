package util;

import java.util.ArrayList;
import java.util.List;

public interface Observavel {
	void adicionarObservador(Observador o);

	public int contarObservadores();

	public void removerObservador(Observador o);

	// quantidade indeterminada de parametros, 0 a n, deve ser feito cast para a classe destino, exemplo String mensagem = (String) args[1];
	public void notificarObservadores(Object ...args);
}
