package model;

import java.util.Scanner;

class ModoJogo {
	private int GAME_MODE = 0;
	private boolean GAME_MODE_STATUS = false;
	private static final int TWO_PLAYERS = 2;
	private static final int FOUR_PLAYERS = 4;
	public static final String[] CORES = {"Verde", "Laranja", "Azul", "Preto", "Vermelho", "Branco"};
	private Modo1x1 modo1 = null;
	private Modo2x2 modo2 = null;
	
	public ModoJogo() {		
		initModo();
	}
	
	private void initModo() {
		Scanner input = new Scanner(System.in);
		
		while (!GAME_MODE_STATUS) {
			System.out.printf("Digite o número de jogadores - 2 ou 4: ");
			
			try {
				GAME_MODE = Integer.parseInt(input.nextLine());
			} 
			catch (NumberFormatException e) {
				GAME_MODE = 0;
			}
			
			if ((GAME_MODE == TWO_PLAYERS) || (GAME_MODE == FOUR_PLAYERS)) {
				System.out.printf("\nModo de Jogo Escolhido: %d VS %d!\n",GAME_MODE/2,GAME_MODE/2);
				GAME_MODE_STATUS = true;
			}
			
			else System.out.println("\nNúmero de jogadores não compatível com o jogo!\nTente novamente!\n");
		}	
		initPlayers();
		input.close();
	}
	
	private void initPlayers() {
		String jogadores[] = new String[GAME_MODE];
		String nome;
		Scanner input = new Scanner(System.in);
		
		for (int i = 0; i < GAME_MODE; i++) {
			System.out.printf("\nJogador %d, escolha seu nick: ",i+1);
			nome = input.nextLine(); // Read user input
			jogadores[i] = nome;
		}
		
		if (GAME_MODE == TWO_PLAYERS) {
			modo1 = new Modo1x1(jogadores, GAME_MODE);
			modo1.exibeModo();
		}
		
		else if (GAME_MODE == FOUR_PLAYERS) {
			modo2 = new Modo2x2(jogadores, GAME_MODE);
			modo2.exibeModo();
		}
		
		input.close();
		
	}
	
	public boolean getGameStatus() {
		return this.GAME_MODE_STATUS;
	}
	
	public int getGameMode() {
		return this.GAME_MODE;
	}
}
