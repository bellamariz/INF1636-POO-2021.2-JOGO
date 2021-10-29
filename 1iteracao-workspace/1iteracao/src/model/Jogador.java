package model;

import java.util.ArrayList;

class Jogador implements Comparable<Jogador> {
	private String name = null;
	private String cor = null;
	private Explorador[] listaDeExploradores;
	private Explorador[] poloFinal = {null, null, null, null, null, null};
	private int poloInicial = 0;
	private int numeroDoJogador, qtdExpCasaFinal, pontosMeta;
	private int dadoInicio;
	private int pontuacaoFinal;
	
	public Jogador() {}

	public Jogador(String nomeDoJogador, String corDoJogador, int numeroDoJogador) {
		this.name = nomeDoJogador;
		this.cor = corDoJogador;
		this.numeroDoJogador = numeroDoJogador;
		this.dadoInicio = 0;
		this.listaDeExploradores = initExploradores();
		this.qtdExpCasaFinal = 0;
		this.pontosMeta = 0;
		this.pontuacaoFinal = 0;
	}

	public String getName() { return name; }
	
	public String getCor() { return cor; }
	
	public void setDado(int valorDado) { this.dadoInicio = valorDado; }
	
	public int getDado() {return this.dadoInicio; }
	
	public int getPontosMeta() { return pontosMeta; }

	public void setMeta(int meta) { this.pontosMeta = meta; }
	
	public int getNumeroDoJogador() { return numeroDoJogador; }

	public int getPoloInicial() { return poloInicial; }

	public void setPoloInicial(int poloInicial) { this.poloInicial = poloInicial; }
	
	public int getPontuacaoFinal() {
		return this.pontuacaoFinal;
	}
	
	public void somaPontuacao() {
		this.pontuacaoFinal = this.qtdExpCasaFinal + this.pontosMeta;
	}

	public Explorador[] getExploradores() { return listaDeExploradores; }

	public void setExploradores(Explorador[] exploradores) { this.listaDeExploradores = exploradores; }
	
	public int getQtdExpCasaFinal() { return qtdExpCasaFinal; }

	public void setQtdExpCasaFinal(int qtdExpCasaFinal) { this.qtdExpCasaFinal = qtdExpCasaFinal; }
	
	//Inicializa a lista de exploradores do Jogador
	private Explorador[] initExploradores() {
		Explorador[] exps = new Explorador[6];

		for (int i = 0; i < exps.length; i++) {
			exps[i] = new Explorador(i + 1, this.cor, this.numeroDoJogador);
		}

		return exps;
	}
	
	//Gera ArrayList de Exploradores
	public ArrayList<Explorador> getListExploradores(){
		ArrayList<Explorador> exploradores = new ArrayList<Explorador>(6);

		for(int i = 0; i < listaDeExploradores.length; i++) {
			exploradores.add(listaDeExploradores[i]);
		}

		return exploradores;
	}
	
	//Seta explorador no polo final do jogador
	public void setPosicaoPoloFinal (int numeroExplorador, Explorador explorador) {
		poloFinal[numeroExplorador] = explorador;
	}
	
	
	//Compara os dados para organizar ordem de jogada
	@Override
	public int compareTo(Jogador jogador) {
    	if(dadoInicio == jogador.dadoInicio)
    		return 0;
    	else if (dadoInicio < jogador.dadoInicio)
    		return 1;
    	else
    		return -1;
    }
	
	//Retorna o jogador aliado
	public Jogador getAliado(ArrayList<Jogador> jogadores) {
		Jogador aliado = null;
		for (int k=0; k<jogadores.size();k++) {
			if ((!jogadores.get(k).getCor().equals(this.cor)) && (jogadores.get(k).poloInicial == this.poloInicial)) {
				aliado = jogadores.get(k);
			}
    	}
		return aliado;
	}
	
	//Retorna o index do jogador aliado na lista de jogadores
	public int getAliadoIndex(ArrayList<Jogador> jogadores) {
		int indexAliado = -1;
		for (int k=0; k<jogadores.size();k++) {
			if ((!jogadores.get(k).getCor().equals(this.cor)) && (jogadores.get(k).poloInicial == this.poloInicial)) {
				indexAliado = k;
			}
		}
		return indexAliado;
	}
	
	public void exibePoloFinal() {
		for(int i = 0; i < poloFinal.length; i++) {
			if (poloFinal[i] == null)
				System.out.print(" - ");
			else
				System.out.print(this.getName() + " Exp" + poloFinal[i].getNumero() + "     ");
		}
	}
	
	public void exibeJogador() {
		System.out.println(this.toString());
		for (int i = 0; i < this.listaDeExploradores.length; i++) {
			listaDeExploradores[i].exibeExplorador();
		}
	}

	@Override
	public String toString() {
		return "Jogador " + numeroDoJogador + " [Nome: " + name + " - Cor: " + cor + "]";
	}
}
