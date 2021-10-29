package model;
import static org.junit.Assert.*;
import java.util.Scanner;
import org.junit.Test;

public class JogadorTest {

	
// Funções auxiliares para o uso do Scanner
	
	public int getQtdPlayersByScanner() {
		
		Scanner input = new Scanner(System.in);
		System.out.println("Digite a quantidade de jogadores: ");
		int qtd = input.nextInt();
		
		input.close();
		
		return qtd;	
	}
	
	public int getPontosMetaByScanner() {
		
		Scanner input = new Scanner(System.in);
		System.out.println("Digite a quantidade de metas conquistadas: ");
		int meta = input.nextInt();
		
		input.close();
		
		return meta;	
	}
	
	public String[] getNamePlayersByScanner() {
		
		Scanner input = new Scanner(System.in);
		int qtd = getQtdPlayersByScanner();
		
		String[] nome = new String[qtd];
		
		for (int i = 0; i < qtd; i++) {
			System.out.println("Digite o seu nickname: ");
			nome[i] = input.nextLine();
		}
		
		input.close();
		
		return nome;
	}

// Testes
	
	@Test
	public void testGetName() {
		
		int qtd = getQtdPlayersByScanner();
		
		Jogador[] players = new Jogador[qtd];
		
		for (int i = 0; i < qtd; i++) {
			assertNotEquals(null, players[i].getName());
		}
	}
	
	@Test
	public void testGetColor() {

		int qtd = getQtdPlayersByScanner();
		
		Jogador[] players = new Jogador[qtd];
		String[] cor = ModoJogo.CORES;
		
		for (int i = 0; i < qtd; i++) {
			assertEquals(cor[i], players[i].getCor());
		}
	}
	
	@Test
	public void testGetNumeroDoJogador() {

		int qtd = getQtdPlayersByScanner();
		
		Jogador[] players = new Jogador[qtd];
		
		for (int i = 0; i < qtd; i++) {
			assertEquals(i, players[i].getNumeroDoJogador());
		}
	}
	
	@Test
	public void testGetExploradores() {

		int qtd = getQtdPlayersByScanner();
		
		Jogador[] players = new Jogador[qtd];
		
		for (int i = 0; i < qtd; i++) {
			assertNotEquals(null, players[i].getExploradores());
		}
	}
	
	@Test
	public void testSetExploradores() {

		int qtd = getQtdPlayersByScanner();
		
		Jogador[] players = new Jogador[qtd];
		Explorador[] explorers = new Explorador[6];
		
	}
	
/*	@Test
	public void testInitExploradores() {

		int qtd = getQtdPlayersByScanner();
		
		Jogador[] players = new Jogador[qtd];
		
		for (int i = 0; i < qtd; i++) {
			assertNotEquals(null, players[i].initExploradores());
		}
	}
*/

	@Test
	public void testVitoriaGetQtdExpCasaFinal() {

		int qtd = getQtdPlayersByScanner();
		
		Jogador[] players = new Jogador[qtd];
		
		for (int i = 0; i < qtd; i++) {
			assertEquals(6, players[i].getQtdExpCasaFinal());	
		}
	}
	
	@Test
	public void testVitoriaSetQtdExpCasaFinal() {

		int qtd = getQtdPlayersByScanner();
		
		Jogador[] players = new Jogador[qtd];
		
	}
	
	@Test
	public void testDerrotaGetQtdExpCasaFinal() {

		int qtd = getQtdPlayersByScanner();
		
		Jogador[] players = new Jogador[qtd];
		
		for (int i = 0; i < qtd; i++) {
			assertNotEquals(6, players[i].getQtdExpCasaFinal());	
		}
	}
	
	@Test
	public void testDerrotaSetQtdExpCasaFinal() {

		int qtd = getQtdPlayersByScanner();
		
		Jogador[] players = new Jogador[qtd];
		
	}
	
	@Test
	public void testPlayersGetPontosMeta() {
		
		int qtd = getQtdPlayersByScanner();
		int meta = getPontosMetaByScanner();
		
		Jogador[] players = new Jogador[qtd];
		
		for (int i = 0; i < qtd; i++) {
			assertEquals(meta, players[i].getPontosMeta());
		}
	}
	
	@Test
	public void testPlayersSetMeta() {
		
		int qtd = getQtdPlayersByScanner();
		int meta = getPontosMetaByScanner();
		
		Jogador[] players = new Jogador[qtd];
		
	}
}
