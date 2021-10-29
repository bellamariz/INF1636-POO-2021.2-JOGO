package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExploradorTest {
	Explorador expJogador1 = new Explorador(1,"Verde",1);
	Explorador expJogador2 = new Explorador(1,"Laranja",2);
	Explorador expJogador3 = new Explorador(1,"Azul",3);
	Explorador expJogador4 = new Explorador(1,"Preto",4);

	@Test
	public void testIsJogadorAliadoTrue() {
		assertTrue("Jogadores impares sao aliados",expJogador1.isJogadorAliado(expJogador3));
		assertTrue("Jogadores pares sao aliados",expJogador2.isJogadorAliado(expJogador4));
	}
	
	@Test
	public void testIsJogadorAliadoFalse() {
		assertFalse("Modo1x1: não são aliados OU Modo2x2: par e impar não são aliados",expJogador1.isJogadorAliado(expJogador2));
	}
	
	@Test
	public void testIsMesmoJogadorTrue() {
		assertTrue("Exploradores de mesma cor são do mesmo jogador.",expJogador1.isMesmoJogador(expJogador1));
	}
	
	@Test
	public void testIsMesmoJogadorFalse() {
		assertFalse("Cada Jogador tem uma cor única de explorador.",expJogador1.isMesmoJogador(expJogador2));
	}
	
	

}
