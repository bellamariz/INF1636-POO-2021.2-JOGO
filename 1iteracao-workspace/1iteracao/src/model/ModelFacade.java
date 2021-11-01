package model;

public class ModelFacade {
	public static MovePecas movePecas;
	public static Dado dado = new Dado();

	public static void startModel (int gameMode, String[] nomesJogadores) {
		movePecas = new MovePecas(gameMode,nomesJogadores,dado);
	}
	
	public static int getValorDado() {
		return dado.lanca();
	}
	
	public static String getDadoColorido(int dado1, int dado2) {
		return dado.getDadoColorido(dado1, dado2);
	}

}
