package controller;

import model.ModelFacade;
import util.Observavel;
import util.Observador;
import util.Operacoes;
import view.ViewFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;

public class ControllerFacade implements Observador, Observavel {
	private static ControllerFacade controller = null;
	private static ViewFacade viewFacade = null;
	private static ModelFacade modelFacade = null;
	
	private boolean canStartGame = false; //TODO: tirar isso dps
	private int contaPartida = 0;
	private boolean fimDeJogo = false;
	private int [][] carrierViewModel = new int[8][8];

	private JFileChooser fileChooser = new JFileChooser();
	//TODO: FileWriter e FileReader
	
	private List<Observador> observadores = new ArrayList<>();

	
	//Instanciando o Controller, View e Model//
	
	private ControllerFacade() {
		viewFacade = ViewFacade.getInstance();
		modelFacade = ModelFacade.getInstance();

		viewFacade.addObserverToTabView(this);
		this.adicionarObservador(viewFacade);
		modelFacade.adicionarObservador(this);
		
		viewFacade.startView();
		while (!canStartGame) {
			if (viewFacade.getCanStartGame()) {
				modelFacade.startModel(viewFacade.getGameMode(), viewFacade.getNomeJogadores());
				canStartGame = true;
			}
		}
		
		if ((viewFacade != null) && (modelFacade != null)) {
			startGame();
		}
	}
	
	public static ControllerFacade getController() {
		if(controller == null) {
			controller = new ControllerFacade();
		}
		return controller;
	}
	
	public static ViewFacade getViewFacade() {return viewFacade;}
	
	public static ModelFacade getModelFacade() {return modelFacade;}
	
	
	//Metodos: Partida//
	
	public void startGame() {
		if (modelFacade.isReadyToStart())
			jogo();
		else
			s("ModelFacade - boolean isReadyToStart falso. \n");
	}
	
	public void jogo() {
    	contaPartida = 0;
    	final int posicaoUltJogador = jogadoresOrdenados.size()-1;
    	int valorDadoDaVez = 0;
    	
    	while (!fimDeJogo) {
    		//Chegou no último jogador
    		if (contaPartida == posicaoUltJogador) {
    			jogadorDaVez = jogadoresOrdenados.get(contaPartida);
    			contaPartida = -1;
    		}
    		
    		//Ainda nao chegou no ultimo jogador
    		else {
    			jogadorDaVez = jogadoresOrdenados.get(contaPartida);
    		}
    		
    		System.out.println("---------- RODADA DO JOGADOR - " + jogadorDaVez.getName() +" ----------\n");
    		
	    	int valorDado1 = dado.lanca();
	    	int valorDado2 = dado.lanca();
	    	String corDadoColorido = dado.getDadoColorido(valorDado1, valorDado2);
	    	
	    	System.out.println("Jogada feita pelo jogador " + jogadorDaVez.getName() + ": Dado1: " + valorDado1 + ", Dado2: " + valorDado2 + "\n");
	    	
	    	//Pergunta no input sobre a jogada dos dados
	    	for (int i = 0; i < 2; i++) {
	    		
	    		if (i == 0)
	    			valorDadoDaVez = valorDado1;
	    		else
	    			valorDadoDaVez = valorDado2;
	    		
	    		jogadaTabuleiroPossivel = false;
	    		
	    		while (!jogadaTabuleiroPossivel) { //Verifica se é possível fazer essa jogada no tabuleiro
	    			moverExploradorPossivel = false;
	    			while (!moverExploradorPossivel) {
				    	System.out.printf(jogadorDaVez.getName() + ", qual explorador voce quer mover " + valorDadoDaVez + " casas? ");
				    	exploradorParaMover = s.nextInt();
		    			
				    	if (!jogadorDaVez.getExploradores()[exploradorParaMover - 1].getCasaFinal()) //Verifica se explorador nao está na casa final
			    			moverExploradorPossivel = true;
		    			else
			    			System.out.println("Explorador " + exploradorParaMover + " ja chegou no polo final, escolha outro!\n");
	    			}
	    			
	    			//Seleciona o tabuleiro da vez do explorador
	    			selecionaTabuleiroDaVez(jogadorDaVez.getExploradores()[exploradorParaMover - 1]);
	    			
	    			//Verificacao dado colorido
	    			if (corDadoColorido != null) {
			    		trataCasoDadoColorido(corDadoColorido);
		    		}
	    			
	    			//Verifica se o explorador esta na casa inicial
	    			boolean estaNaCasaInicial = (jogadorDaVez.getExploradores()[exploradorParaMover - 1].wasInPoloInicial()); 
			    	if (estaNaCasaInicial) {
			    		//Sai do polo inicial e entra no tabuleiro
			        	System.out.printf("Explorador entra no tabuleiro em qual longitude? ");
			        	longitudeInicial = s.nextInt() - 1;
			        	jogadaTabuleiroPossivel = verificaPossibilidadeInicial(jogadorDaVez.getExploradores()[exploradorParaMover - 1], valorDadoDaVez, longitudeInicial, jogadorDaVez);
			    	}
			    	
			    	else {
			    		//Ja saiu da casa inicial, está se movimento no tabuleiro
			    		System.out.printf("Explorador anda na latitude ou longitude? (Escreva 1 para latitude e 2 para longitude) ");
			    		opcaoDeMovimento = s.nextInt();
			    		jogadaTabuleiroPossivel = verificaPossibilidade(jogadorDaVez.getExploradores()[exploradorParaMover - 1], valorDadoDaVez, opcaoDeMovimento, jogadorDaVez);
			    	}
	    		}
	    		
	    		//Exibe status do jogo
		    	System.out.println("Explorador " + exploradorParaMover + " do jogador " + jogadorDaVez.getName() + " esta na posicao i = " + jogadorDaVez.getExploradores()[exploradorParaMover - 1].getIMatriz() + ", j = " + (jogadorDaVez.getExploradores()[exploradorParaMover - 1].getJMatriz()+1));
		    	exibe();
		    	
		    	//Verifica se a casa tem uma meta, se o jogador pode conquista-la e se ainda ha metas sobrando
		    	int posicaoI = jogadorDaVez.getExploradores()[exploradorParaMover - 1].getIMatriz();
		    	int posicaoJ = jogadorDaVez.getExploradores()[exploradorParaMover - 1].getJMatriz();
		    	Meta metaDaCasa = tabuleiroDaVez.getMatrix()[posicaoI][posicaoJ].getMeta();
		    	
		    	if (tabuleiroDaVez.getMatrix()[posicaoI][posicaoJ].getMeta().isMetaNoTabuleiro() 
		    			&& tabuleiroDaVez.getMatrix()[posicaoI][posicaoJ].getMeta().isCapturavel()) {
		    		tabuleiroDaVez.getMatrix()[posicaoI][posicaoJ].getMeta().setMetaNoTabuleiro(false);
		    		jogadorDaVez.setMeta(jogadorDaVez.getPontosMeta() + metaDaCasa.getPontoMeta());
		    		System.out.println("Jogador " + jogadorDaVez.getName() + " conquistou um ponto de meta! "
		    				+ "Pontos totais: " + jogadorDaVez.getPontosMeta() + "\n");
		    		metaCumprida = true;
		    		
		    		//Conquistou carta dinâmica
		    		cartaEscolhida = pegaCartaDoDeck();
		    		cartaEscolhida.exibeCartaEscolhida();
		    	}
	    	}
	    	
	    	//Se pelo menos uma meta foi capturada + todos os exploradores na casa final
	    	if (metaCumprida && (jogadorDaVez.getQtdExpCasaFinal()==6))
	    		fimDeJogo = true;
	    	
	    	contaPartida+=1;
    	}
    	
    	declaraVencedor();
    }
	
	
	//Metodos: Auxiliares//
	
	public static void s(Object o) {
		System.out.println(o);
	}
	
	
	//Metodos: Observador e Observavel //
	
	@Override
	public void update(Observavel o, Object... args) {
		s("De " + o.getClass().getSimpleName() + " Para " + this.getClass().getSimpleName());

		if (args == null) {
			return;
		}

		final int operacao = (int) args[0];
		String mensagem = null;

		try {
			mensagem = (String) args[1];
		}
		catch (ClassCastException ignored) {}

		if (operacao == Operacoes.OPERACAO_OLA) {
			s("Operacao 1. Msg: " + mensagem);
			notificarObservadores(1, "Tchau");
		}
		else if (operacao == Operacoes.DADO_LANCADO) {
			final int dado = (int) args[1];
			final double valor2 = (double) args[2];
			s("Recebemos via mensagem o dado " + dado);
		}
		else {
			s(o + " " + Arrays.asList(args).toString());
		}
	}

	public void adicionarObservador(Observador o) {
		if (!observadores.contains(o)) {
			this.observadores.add(o);
		}
	}

	public void clearChanged() {}

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
}
