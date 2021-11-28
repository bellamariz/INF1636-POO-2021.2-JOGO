package model;

import util.Observador;
import util.Observavel;
import util.Operacoes;

import java.util.ArrayList;
import java.util.List;

public class ModelFacade implements Observavel {
	private static ModelFacade model = null;
	private List<Observador> observadores = new ArrayList<>();
	private ModoJogo jogo = null;
	private MovePecas movePecas = null;
	private boolean readyToStart = false;
	
	private ModelFacade() {}
	
	public static ModelFacade getInstance() {
		if(model == null) {
			model = new ModelFacade();
		}
		return model;
	}
	
	
	//Metodos: Inicializacao dos componentes da partida//

	public void startModel (int gameMode, String[] nomesJogadores) {
		this.jogo = ModoJogo.getInstance(gameMode, nomesJogadores);
		this.movePecas = MovePecas.getInstance();
		
		if ((this.jogo != null) && (this.movePecas != null))
			initGame();
	}
	
	private void initGame() {
		this.movePecas.setTabuleiroPoloSul(new Tabuleiro(ModoJogo.NUM_JOGADORES));
		this.movePecas.setTabuleiroPoloNorte(new Tabuleiro(ModoJogo.NUM_JOGADORES));
		//this.movePecas.setDeckCartaDinamica(jogo.getDeckCartas());
		this.movePecas.setJogadoresOrdenados(jogo.getJogadoresOrdenados());
		this.movePecas.setValoresDados(jogo.getDadosJogadores());
        initTabuleiro(jogo.getJogadores());
    }
	
    private void initTabuleiro(Jogador[] listaDeJogadores) {
    	//Jogador impar inicia no polo sul, jogador par no polo norte
    	this.movePecas.getTabuleiroPoloSul().montaTabuleiro();
    	this.movePecas.getTabuleiroPoloNorte().montaTabuleiro();
        
        for (int i=0; i<listaDeJogadores.length; i++) {
        	
        	if (listaDeJogadores[i].getNumeroDoJogador()%2 != 0) {
        		listaDeJogadores[i].setPoloInicial(this.movePecas.getPOLO_INICIAL_SUL());
        		this.movePecas.getTabuleiroPoloSul().montaPoloInicial(listaDeJogadores[i]);
        	}
        	else {
        		listaDeJogadores[i].setPoloInicial(this.movePecas.getPOLO_INICIAL_NORTE());
        		this.movePecas.getTabuleiroPoloNorte().montaPoloInicial(listaDeJogadores[i]);
        	}
        	
        	this.movePecas.getTabuleiroPoloSul().setPodeMover(false);
        	this.movePecas.getTabuleiroPoloNorte().setPodeMover(false);
        }
        
        checkIfGameReady();
    }
    
    private void checkIfGameReady() {
    	if ((this.movePecas.getJogadoresOrdenados() != null) &&
    			(this.movePecas.getTabuleiroPoloSul() != null) && 
    			(this.movePecas.getTabuleiroPoloNorte() != null) && 
    			(this.movePecas.getValoresDados() != null) &&
    			(this.movePecas.getDado() != null)) 
    		this.readyToStart = true;
    	
    	else {
    		this.readyToStart = false;
    		s("ModelFacade - boolean isReadyToStart falso. \n");
    	}
    }

    
    //Metodos: Auxiliares//
	
  	public static void s(Object o) {
  		System.out.println(o);
  	}
  	
  	
	
	//Metodos: Observador e Observavel//
	
	public void adicionarObservador(Observador o) {
		if (!observadores.contains(o)) {
			this.observadores.add(o);
		}
	}

	public int contarObservadores() {
		return this.observadores.size();
	}

	public void removerObservador(Observador o) {
		if (this.observadores.contains(o)) {
			this.observadores.remove(o);
		}
	}

	public void notificarObservadores(Object ...args) {
		for (Observador o : this.observadores) {
			o.update(this, args);
		}
	}

	
	
	//Metodos: Getter e Setter//
	
	public ModoJogo getJogo() {
		return jogo;
	}

	public void setJogo(ModoJogo jogo) {
		this.jogo = jogo;
	}

	public MovePecas getMovePecas() {
		return movePecas;
	}

	public void setMovePecas(MovePecas movePecas) {
		this.movePecas = movePecas;
	}

	public boolean isReadyToStart() {
		return readyToStart;
	}

	public void setReadyToStart(boolean readyToStart) {
		this.readyToStart = readyToStart;
	}
	
	
}
