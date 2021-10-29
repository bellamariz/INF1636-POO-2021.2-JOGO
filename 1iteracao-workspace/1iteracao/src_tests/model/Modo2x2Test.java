package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class Modo2x2Test {
	private static final int FOUR_PLAYERS = 4;
	ModoJogo modo = new ModoJogo();

	@Test
	public void testModo2x2() {
		assertEquals("Modo Jogo 2x2 possui 4 jogadores", modo.getGameMode(),FOUR_PLAYERS);
	}

}
