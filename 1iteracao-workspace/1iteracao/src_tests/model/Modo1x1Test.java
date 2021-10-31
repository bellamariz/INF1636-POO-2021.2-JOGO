package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class Modo1x1Test {
	private static final int MODO1X1 = 1;
	private static final String[] JOGADORES = {"Jogador1","Jogador2"};
	ModoJogo modo = new ModoJogo(MODO1X1,JOGADORES);

	@Test
	public void testModo1x1() {
		assertEquals("Modo Jogo 1x1 possui 2 jogadores", modo.getGameMode(),MODO1X1);
	}

}
