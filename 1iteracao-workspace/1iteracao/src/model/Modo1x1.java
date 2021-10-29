package model;

class Modo1x1 {
	private Jogador playerA, playerB = null;
    private MovePecas jogo = null;
    private Jogador[] jogadores = null;
    
    public Modo1x1 () {}

    public Modo1x1(String[] names, int modoJogo) {
        this.playerA = new Jogador(names[0], ModoJogo.CORES[0], 1);
        this.playerB = new Jogador(names[1], ModoJogo.CORES[1], 2);
        this.jogadores = montaJogadores();
        this.jogo = new MovePecas(jogadores, modoJogo);
    }

    public Jogador getPlayerA() {
		return playerA;
	}

	public void setPlayerA(Jogador playerA) {
		this.playerA = playerA;
	}

	public Jogador getPlayerB() {
		return playerB;
	}

	public void setPlayerB(Jogador playerB) {
		this.playerB = playerB;
	}

	public MovePecas getJogo() {
		return jogo;
	}

	public void setJogo(MovePecas jogo) {
		this.jogo = jogo;
	}
	
	private Jogador[] montaJogadores() {
		Jogador[] listaDeJogadores = {this.playerA, this.playerB};
		return listaDeJogadores;
	}

	public void exibeModo() {
        this.playerA.exibeJogador();
        this.playerB.exibeJogador();
    }
    
    
}