package model;

public class ModelFacade {
	public static MovePecas movePecas;

	public static void startModel (int gameMode, String[] nomesJogadores) {
		movePecas = new MovePecas(gameMode,nomesJogadores);
	}

}
