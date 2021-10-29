package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class ModoJogoTest {
	
	@Test
	public void testInitModo() {
		ModoJogo modo = new ModoJogo();
		assertTrue("Verifica se o modo de jogo é válido (1x1 ou 2x2)", modo.getGameStatus());
	}

}
