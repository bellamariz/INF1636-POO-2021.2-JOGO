package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class Modo1x1Test {
	private static final int TWO_PLAYERS = 2;
	ModoJogo modo = new ModoJogo();

	@Test
	public void testModo1x1() {
		assertEquals("Modo Jogo 1x1 possui 2 jogadores", modo.getGameMode(),TWO_PLAYERS);
	}

}
