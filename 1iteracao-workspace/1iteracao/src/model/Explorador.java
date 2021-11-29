package model;

import java.util.Random;

class Explorador { 
	private String cor = null;
	private int numero = 0;
	private int numeroDoJogador = 0;
	private int iInicial, jInicial, iMatriz, jMatriz, iFinal, jFinal;
	private boolean casaFinal, matrizOposta, mudandoDeMatriz;
	
	public Explorador(int numDoPeao, String corDoPeao, int numeroDoJogador) {
		this.cor = corDoPeao;
		this.numero = numDoPeao;
		this.numeroDoJogador = numeroDoJogador;
		this.casaFinal = false;
		this.matrizOposta = false;
		this.mudandoDeMatriz = true; 
		this.iInicial = 6;
		this.iMatriz = 6;
		this.iFinal = 6;
		this.jInicial = 0;
		this.jMatriz = 0;
		this.jFinal = 12;
		
	}
	
	public String getCor() { return cor; }

	public int getNumero() { return numero; }
	
	public int getNumeroDoJogador() { return numeroDoJogador; }
	
	public int getIMatriz() { return iMatriz; }

	public void setIMatriz(int iMatriz) { this.iMatriz = iMatriz; }
	
	public int getJMatriz() { return jMatriz; }

	public void setJMatriz(int jMatriz) { this.jMatriz = jMatriz; }
	
	public int getjInicial() { return jInicial; }

	public void setjInicial(int jInicial) { this.jInicial = jInicial; }
	
	public int getiInicial() { return iInicial; }

	public void setiInicial(int iInicial) { this.iInicial = iInicial; }
	
	public int getjFinal() { return jFinal; }

	public void setjFinal(int jFinal) { this.jFinal = jFinal; }
	
	public int getiFinal() { return iFinal; }

	public void setiFinal(int iFinal) { this.iFinal = iFinal; }
	
	public boolean getCasaFinal() { return casaFinal; }

	public void setCasaFinal(boolean casaFinal) { this.casaFinal = casaFinal; }

	public boolean wasInPoloInicial(int coordenada) {
		return coordenada == this.iInicial;
	}
	
	public boolean wasInPoloInicial() {
		return this.iMatriz == this.iInicial;
	}
	
	public boolean isInMatrizOposta() { return matrizOposta; }

	public void setMatrizOposta(boolean matrizOposta) { this.matrizOposta = matrizOposta; }
	
	public boolean isMudandoDeMatriz() { return mudandoDeMatriz; }

	public void setMudandoDeMatriz(boolean mudou) { this.mudandoDeMatriz = mudou; }
	
	public boolean isJogadorAliado(Explorador explorador) {
		if ((this.numeroDoJogador%2 != 0) && (explorador.getNumeroDoJogador()%2 != 0)) //Jogadores inicializados no polo sul
			return true;
		else if ((this.numeroDoJogador%2 == 0) && (explorador.getNumeroDoJogador()%2 == 0)) //Jogadores inicializados no polo norte
			return true;
		
		return false;
	}
	
	public boolean isMesmoJogador(Explorador explorador) {
		return this.cor == explorador.getCor();
	}
	
	@Override
	public String toString() {
		return "Explorador [cor=" + cor + ", numero=" + numero + "]";
	}
	
	public void exibeExplorador() {
		System.out.println(this.toString());
	}
}