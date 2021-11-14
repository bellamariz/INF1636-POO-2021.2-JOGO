package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

class Tabuleiro {
	private final int linhas = 6, colunas = 12;
	private ElementosTabuleiro[][] matrizCasas = new ElementosTabuleiro[linhas][colunas];
	private ElementosTabuleiro casaAtual = null;
	private ArrayList<Explorador> poloInicial;
	boolean podeMover = false;
	private final int gameMode;
	private final int MAX_SIZE;

    public Tabuleiro(int gameMode) {
    	this.gameMode = gameMode;
    	this.MAX_SIZE = 3*gameMode;
    	this.poloInicial = new ArrayList<Explorador>(MAX_SIZE);
    }
    
	public void montaTabuleiro() {
		for(int i = 0; i < linhas; i++) //casa latitude (i)
		    for(int j = 0; j < colunas; j++)  //casas longitude (j)
		    {
		        matrizCasas[i][j] = new ElementosTabuleiro(gameMode);
		    	if ((i == 0 & j == 1) || (i == 0 & j == 7) || (i == 1 & j == 2) || (i == 1 & j == 8) || (i == 2 & j == 4) || (i == 2 & j == 10)) 
		    		matrizCasas[i][j].getMeta().setMetaNoTabuleiro(true);
		    }
	}
	
	public void montaPoloInicial(Jogador jogador) {
		this.poloInicial.addAll(jogador.getListExploradores());
	}
	
	public ElementosTabuleiro[][] getMatrix() {
		return matrizCasas;
	}

	public boolean getPodeMover() { return podeMover; }

	public void setPodeMover(boolean podeMover) { 
		this.podeMover = podeMover; 
	}
	
	
	
	public ElementosTabuleiro getCasaAtual() {
		return casaAtual;
	}

	public void setCasaAtual(ElementosTabuleiro casaAtual) {
		this.casaAtual = casaAtual;
	}

	public ArrayList<Explorador> getPoloInicial() { return poloInicial; }

	public void setPoloInicial(ArrayList<Explorador> poloInicial) { 
		this.poloInicial = poloInicial; 
	}
	
	//Guarda o explorador no polo inicial
	public void setPosicaoPoloInicial (int numeroExplorador, Explorador explorador) {
		int numeroJogador = explorador.getNumeroDoJogador();
		int casa = numeroExplorador-1;
		if (numeroJogador == 1 || numeroJogador == 2) //Posições 0 -> 5 da lista polo inicial
			poloInicial.set(casa,explorador);
		else if (numeroJogador == 3 || numeroJogador == 4) //Posições 6 -> 11 da lista polo inicial
			poloInicial.set(casa+6,explorador);
	}
	
	//Seta o explorador no polo inicial como nulo
	public void setPosicaoPoloInicialAsNull (int numeroExplorador, Explorador explorador) {
		int numeroJogador = explorador.getNumeroDoJogador();
		int casa = numeroExplorador-1;
		if (numeroJogador == 1 || numeroJogador == 2) //Posições 0 -> 5 da lista polo inicial
			poloInicial.set(casa,null);
		else if (numeroJogador == 3 || numeroJogador == 4) //Posições 6 -> 11 da lista polo inicial
			poloInicial.set(casa+6,null);
	}
	
	//Verifica numero para longitude (j) quando sai do polo inicial
	public boolean verificaPossibilidadeMovimentoInicial (int longitude) {
		if (longitude >= 0 && longitude < 12)
			return true;
		return false;
	}
	
	//Verifica possibilidade de movimento na latitude (i)
	public boolean verificaPossibilidadeMovimentoLatitude (Explorador explorador, int dado) {
		// o jogador não pode trocar de polo longitudinalmente
		if ((!explorador.isInMatrizOposta() && (explorador.getIMatriz() - dado) > 0))
			return true;
		
		//explorador esta no polo oposto, mas não chegou no final do polo
		else if (explorador.isInMatrizOposta() && (explorador.getIMatriz() + dado) <= 6)
			return true;
		
		//explorador troca de polo nas longitudes (j) 0,5,6,11
		else {
			if (!explorador.isInMatrizOposta() && (explorador.getJMatriz() == 0 || explorador.getJMatriz() == 5 || 
					explorador.getJMatriz() == 6 || explorador.getJMatriz() == 11)) {
				explorador.setMatrizOposta(true);
				explorador.setMudandoDeMatriz(true);
				return true;
			}
		}
		
		return false;
	}
	
	//Verifica movimento no tabuleiro
	public boolean verificaPossibilidadeMovimento (Explorador explorador, int dado, int movimento) {
		// movimento na latitude, input = 1, max de casas = 6
		if (movimento == 1) 
			return verificaPossibilidadeMovimentoLatitude (explorador, dado);
		
		// movimento na longitude, input = 2 (ele sempre pode mover pro lado), max de casas = 12
		else 
			return true;
	}
	
	//Checa se o polo inicial está cheio
	public boolean poloInicialCheio() {
		int countCheio = 0;
		for(Explorador exp : poloInicial) {
			if (exp != null)
				countCheio+=1;
		}
		
		return (countCheio == MAX_SIZE);
	}
	
	public void exibeTabuleiro() {
		for(int i = 0; i < linhas; i++) {
		    for(int j = 0; j < colunas; j++) 
		        System.out.print(" [" + matrizCasas[i][j].getMeta().isMetaNoTabuleiro() + " " + (matrizCasas[i][j].getExploradores()) + "]  ");
		    System.out.println();
		}
	}
	
	public void exibePoloInicial() {
		for(int i = 0; i < poloInicial.size(); i++) {
			if (poloInicial.get(i) == null)
				System.out.print(" - ");
			else
				System.out.print("Jogador " + poloInicial.get(i).getNumeroDoJogador() + " - Exp " + poloInicial.get(i).getNumero() + "     ");
		}
	}
	
	

	
}