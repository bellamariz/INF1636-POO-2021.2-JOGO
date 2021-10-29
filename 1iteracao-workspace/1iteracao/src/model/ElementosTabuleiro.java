package model;

import java.util.ArrayList;
import java.util.Scanner;

class ElementosTabuleiro {
	private Meta meta = new Meta();
	private ArrayList<Explorador> exploradoresNaCasa;
	private boolean casaFechada = false;
	private boolean casaCheia = false;
	private Explorador adversarioCapturado = null;
	private final int MAX_SIZE;
	
	public ElementosTabuleiro(int gameMode) {
		this.MAX_SIZE = gameMode*3; //Assume-se que o numero maximo de exploradores por casa é 6 no modo1x1 (individual) e 12 no modo2x2 (dupla)
		this.exploradoresNaCasa = new ArrayList<Explorador>(MAX_SIZE);
	}
	
	public Meta getMeta() { return meta; }
	
	public ArrayList<Explorador> getExploradores() { return exploradoresNaCasa; }
	
	public boolean isCasaFechada() {
		return casaFechada;
	}

	public void setCasaFechada(boolean casaFechada) {
		this.casaFechada = casaFechada;
	}
	
	public boolean isCasaCheia() {
		return casaCheia;
	}

	public void setCasaCheia(boolean casaCheia) {
		this.casaCheia = casaCheia;
	}
	
	public Explorador getAdversarioCapturado() {
		return adversarioCapturado;
	}

	public void setAdversarioCapturado(Explorador adversarioCapturado) {
		this.adversarioCapturado = adversarioCapturado;
	}
	
	//Conta a quantidade de posicoes disponiveis na casa
	private int contaPosicoesDisponiveis() {
		int size = MAX_SIZE - exploradoresNaCasa.size();
		return size;
	}
	
	//Conta quantos exploradores aliados desse jogador tem na casa
	private int containsAliado(Explorador exploradorDoJogador) { 
		int contaAliado = 0;
		
		for (int i=0; i<exploradoresNaCasa.size(); i++) {
			if (exploradoresNaCasa.get(i).isJogadorAliado(exploradorDoJogador))
				contaAliado+=1;
		}
		
		return contaAliado;
	}
	
	//Conta quantos exploradores desse jogador tem na casa
	private int containsExploradorDoJogador(Explorador exploradorDoJogador) { 
		int contaExpDoJogador = 0;
		
		for (int i=0; i<exploradoresNaCasa.size(); i++) {
			if (exploradoresNaCasa.get(i).isMesmoJogador(exploradorDoJogador))
				contaExpDoJogador+=1;
		}
		
		return contaExpDoJogador;
	}
	
	//Verifica as condições de movimentacao dos exploradores
	public boolean casaLiberada(Jogador jogador, int numDoExplorador) {
		final int qtdeExploradores = exploradoresNaCasa.size();
		int mesmoJogador,mesmaDupla,somaAliados;
		Explorador exploradorDoJogador = jogador.getExploradores()[numDoExplorador-1];
		
		//Casa vazia
		if (qtdeExploradores == 0) {
			this.setCasaFechada(false);
			this.setCasaCheia(false);
			return true;
		}
		
		//Casa ocupada
		else if (qtdeExploradores > 0) {
			
			//Casa cheia
			if(qtdeExploradores == MAX_SIZE) {
				this.setCasaCheia(true);
				System.out.println("Casa cheia no caminho.\n");
				return false;
			}
			
			//Casa disponivel
			mesmoJogador = containsExploradorDoJogador(exploradorDoJogador);
			mesmaDupla = containsAliado(exploradorDoJogador);
			somaAliados = mesmoJogador+mesmaDupla;
			
			//Tem apenas exploradores inimigos e casa esta fechada por adversarios
			if ((somaAliados == 0) && (qtdeExploradores >= 2)) {
				this.setCasaFechada(true);
				System.out.println("Casa fechada por adversários.\n");
				return false;
			}
		}
		
		this.setCasaCheia(false);
		this.setCasaFechada(false);
		
		return true;
	}
	

	//Faz a movimentacao dos exploradores
	public void moverExploradores(Jogador jogador, int numDoExplorador) {
		final int qtdeExploradores = exploradoresNaCasa.size();
		int mesmoJogador,mesmaDupla,somaAliados;
		Explorador exploradorDoJogador = jogador.getExploradores()[numDoExplorador-1];
		
		
		//Casa vazia
		if (qtdeExploradores == 0) {
			this.setCasaFechada(false);
			this.setCasaCheia(false);
			exploradoresNaCasa.add(exploradorDoJogador);
			return;
		}
		
		//Casa ocupada
		else if (qtdeExploradores > 0) {
			
			if(qtdeExploradores == MAX_SIZE) {
				this.setCasaCheia(true);
				System.out.println("Casa cheia no caminho.\n");
				return;
			}
			
			//Casa disponivel
			mesmoJogador = containsExploradorDoJogador(exploradorDoJogador);
			mesmaDupla = containsAliado(exploradorDoJogador);
			somaAliados = mesmoJogador+mesmaDupla;
			
			//Tem apenas exploradores inimigos
			if (somaAliados == 0) {
				
				//Captura explorador adversario na casa mandando-o pro polo inicial + insere novo explorador na casa
				if (qtdeExploradores == 1) {
					exploradoresNaCasa.get(0).setIMatriz(6);
					this.setAdversarioCapturado(exploradoresNaCasa.get(0));
					exploradoresNaCasa.set(0,exploradorDoJogador);
				}

				//Casa está fechada por adversarios
				else if (qtdeExploradores >= 2) {
					this.setCasaFechada(true);
					System.out.println("Casa fechada por adversários.\n");
					return;
				}
			}
			
			//Tem pelo menos um aliado ou um explorador do jogador + insere novo explorador na casa
			else if (somaAliados > 0) {
				exploradoresNaCasa.add(exploradorDoJogador);
			}
			
			//Checa conquista de meta pelo jogador
			mesmoJogador = containsExploradorDoJogador(exploradorDoJogador);
			if (mesmoJogador == 2) {
				this.meta.setCapturavel(true);
			}
			else
				this.meta.setCapturavel(false);
			
			this.setCasaCheia(false);
			this.setCasaFechada(false);
		}
	}
		
	//Remove um explorador da casa
	public Explorador removeExploradorDaCasa(Explorador explorador) {
		final int qtdeExploradores = exploradoresNaCasa.size();
		
		if (qtdeExploradores==0) {
			System.out.println("Casa está vazia. Não há explorador pra remover.\n");
			return null;
		}
		
		exploradoresNaCasa.remove(explorador);
		System.out.println("Explorador " + explorador.getNumero() + " do jogador foi removido da casa antiga.\n");
		return explorador;
	}

}
