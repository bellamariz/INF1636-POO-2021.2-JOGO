package model;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

public class MetaTest {
	private final int QTDE_FICHAS = 12;
	
	public int getMetaByScanner() {
		
		Scanner input = new Scanner(System.in);
		System.out.println("Digite a quantidade de metas conquistadas: ");
		int meta = input.nextInt();
		
//		input.close();
		
		return meta;	
	}

	@Test
	public void testCapturaMeta() {
		Meta meta = new Meta();
		int metasCapturadas = getMetaByScanner();
		int temp = metasCapturadas;
		
		while (temp>0) {
			meta.capturaMeta();
			temp--;
		}
		
		int metasSobrando = QTDE_FICHAS - metasCapturadas;
		
		assertEquals("Quando a meta é capturada, diminui a qtde de metas restantes",
				metasSobrando,meta.getMetasRestantes());
		
	}
	
	
	@Test
	public void testConfereObjetivoMeta () {
		Meta meta = new Meta();
		int metasCapturadas = getMetaByScanner();
		
		while (metasCapturadas>0) {
			meta.capturaMeta();
			metasCapturadas--;
		}
		
		assertTrue("Pelo menos uma meta capturada ao decorrer do jogo", meta.confereObjetivoMeta());
	}
	
	@Test
	public void testMetasEsgotadas() {
		Meta meta = new Meta();
		int metasCapturadas = getMetaByScanner();
		
		while (metasCapturadas>0) {
			meta.capturaMeta();
			metasCapturadas--;
		}
		
		if (meta.getMetasRestantes() <= 0)
			assertTrue("Não há metas restantes no tabuleiro.",meta.metasEsgotadas());
		else
			assertFalse("Ainda há metas no tabuleiro.",meta.metasEsgotadas());
	}
}
