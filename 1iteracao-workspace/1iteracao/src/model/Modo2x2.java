package model;

class Modo2x2{
	private Jogador playerA1, playerA2, playerB1, playerB2;
	private MovePecas jogo = null;
	private Jogador[] jogadores = null;
	
	public Modo2x2(String names[], int modoJogo) {
		this.playerA1 = new Jogador(names[0], ModoJogo.CORES[0], 1);
        this.playerA2 = new Jogador(names[1], ModoJogo.CORES[1], 2);
		this.playerB1 = new Jogador(names[2], ModoJogo.CORES[2], 3);
        this.playerB2 = new Jogador(names[3], ModoJogo.CORES[3], 4);
        this.jogadores = montaJogadores();
        this.jogo = new MovePecas(jogadores, modoJogo);
	}
	
    public Jogador getPlayerA1() {
		return playerA1;
	}

	public void setPlayerA1(Jogador playerA1) {
		this.playerA1 = playerA1;
	}

	public Jogador getPlayerA2() {
		return playerA2;
	}

	public void setPlayerA2(Jogador playerA2) {
		this.playerA2 = playerA2;
	}

	public Jogador getPlayerB1() {
		return playerB1;
	}

	public void setPlayerB1(Jogador playerB1) {
		this.playerB1 = playerB1;
	}

	public Jogador getPlayerB2() {
		return playerB2;
	}

	public void setPlayerB2(Jogador playerB2) {
		this.playerB2 = playerB2;
	}
	
	private Jogador[] montaJogadores() {
		Jogador[] listaDeJogadores = {this.playerA1, this.playerA2, this.playerB1, this.playerB2};
		return listaDeJogadores;
	}
	
	public void exibeModo() {
    	this.playerA1.exibeJogador();
    	this.playerA2.exibeJogador();
        this.playerB1.exibeJogador();
        this.playerB2.exibeJogador();
    }
}
