package model;
import java.util.Random;

class Dado {
	private int numero = 0;
	private String cor = null;
	private final String coresDadoColorido[] = ModoJogo.CORES;   
	
	public Dado() {}
	
	private int geraRandom() {
		int max = 6;
		int min = 1;
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	public int lanca() {
		this.numero = geraRandom();
		return this.numero;
	}
	
	public int getValorDado() {
		return numero;
	}
	
	public void setValorDado(int valor) {
		this.numero = valor;
	}
	
	public String getDadoColorido(int dado1, int dado2) {
		if (dado1 == dado2) {
    		this.cor = coresDadoColorido[geraRandom()-1];
			return cor;
		}
		return null;
	}
}
