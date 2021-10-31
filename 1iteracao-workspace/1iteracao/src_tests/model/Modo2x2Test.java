package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class Modo2x2Test {
	private static final int MODO2X2 = 2;
	private static final String[] JOGADORES = {"Jogador1","Jogador2","Jogador3","Jogador4"};
	ModoJogo modo = new ModoJogo(MODO2X2,JOGADORES);

	@Test
	public void testModo2x2() {
		assertEquals("Modo Jogo 2x2 possui 4 jogadores", modo.getGameMode(),MODO2X2);
	}

}
