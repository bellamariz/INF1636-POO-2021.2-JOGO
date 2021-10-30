package model;

import java.util.Scanner;

class ModoJogo {

	private int GAME_MODE = 0;
	private boolean GAME_MODE_STATUS = false;
	private static final int MODE1X1 = 1;
	private static final int MODE2X2 = 2;
	public static final String[] CORES = {"Verde", "Laranja", "Azul", "Preto", "Vermelho", "Branco"};
	private Modo1x1 modo1 = null;
	private Modo2x2 modo2 = null;
	String jogadores[] = new String[4];
	
	public ModoJogo(int mode, String names[]) {
		GAME_MODE = mode;
		jogadores = names;
		
		initPlayers();
	}
	
	private void initPlayers() {		
		if (GAME_MODE == MODE1X1) {
			modo1 = new Modo1x1(jogadores, GAME_MODE);
			modo1.exibeModo();
		}
		
		else if (GAME_MODE == MODE2X2) {
			modo2 = new Modo2x2(jogadores, GAME_MODE);
			modo2.exibeModo();
		}
		
	}
	
	public boolean getGameStatus() {
		return this.GAME_MODE_STATUS;
	}
	
	public int getGameMode() {
		return this.GAME_MODE;
	}
}
