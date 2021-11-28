package controller;

import model.ModelFacade;
import util.Observavel;
import util.Observador;
import util.Operacoes;
import view.ViewFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class ControllerFacade implements Observador, Observavel {
	private static ControllerFacade controller = null;
	private static ViewFacade viewFacade = null;
	private static ModelFacade modelFacade = null;
	private boolean canStartGame = false; // TODO: tirar isso dps que implementar o observer
	private int contaVez = 0;
	private int valorDadoDaVez = 0;
	private boolean fimDeJogo = false;
	private boolean metaCumprida = false;
	private int[][] carrierViewModel = new int[8][8];
	private Scanner s = new Scanner(System.in);

	private JFileChooser fileChooser = new JFileChooser();
	// TODO: FileWriter e FileReader

	private List<Observador> observadores = new ArrayList<>();

	//Instanciando o Controller, View e Model//

	private ControllerFacade() {
		viewFacade = ViewFacade.getInstance();
		modelFacade = ModelFacade.getInstance();

		viewFacade.startView();
		viewFacade.addObserverToTabView(this);
		this.adicionarObservador(viewFacade);

		while (!canStartGame) {
			if (viewFacade.getCanStartGame()) {
				modelFacade.startModel(viewFacade.getGameMode(), viewFacade.getNomeJogadores());
				canStartGame = true;
			}
		}

		if ((viewFacade != null) && (modelFacade != null)) {
			startGame();
		}
		modelFacade.adicionarObservador(this);
	}

	public static ControllerFacade getController() {
		if (controller == null) {
			controller = new ControllerFacade();
		}
		return controller;
	}

	public static ViewFacade getViewFacade() {
		return viewFacade;
	}

	public static ModelFacade getModelFacade() {
		return modelFacade;
	}

	
	
	//Metodos: Partida

	public void startGame() {
		if (modelFacade.isReadyToStart())
			jogo();
		else
			s("ModelFacade - boolean isReadyToStart falso. \n");
	}

	public void jogo() {
    	contaVez = 0;
    	valorDadoDaVez = 0;
    	final int posicaoUltJogador = modelFacade.getJogadoresOrdenados().size()-1;
    	
    	while (!fimDeJogo) {
    		//Chegou no último jogador
    		if (contaVez == posicaoUltJogador) {
    			modelFacade.setJogadorDaVez(modelFacade.getJogadoresOrdenados(),contaVez);
    			contaVez = -1;
    		}
    		
    		//Ainda nao chegou no ultimo jogador
    		else {
    			modelFacade.setJogadorDaVez(modelFacade.getJogadoresOrdenados(),contaVez);
    		}
    		
    		s("---------- RODADA DO JOGADOR - " + modelFacade.getJogadorDaVezNome() +" ----------\n");
    		
	    	int valorDado1 = modelFacade.getValorDadoLancado();
	    	int valorDado2 = modelFacade.getValorDadoLancado();
	    	String corDadoColorido = modelFacade.getValorDadoColorido(valorDado1, valorDado2);
	    	
	    	s("Jogada feita pelo jogador " + modelFacade.getJogadorDaVezNome() + ": Dado1: " + valorDado1 + ", Dado2: " + valorDado2 + "\n");
	    	
	    	//Pergunta no input sobre a jogada dos dados
	    	for (int i = 0; i < 2; i++) {
	    		
	    		if (i == 0)
	    			valorDadoDaVez = valorDado1;
	    		else
	    			valorDadoDaVez = valorDado2;
	    		
	    		modelFacade.setJogadaTabuleiroPossivel(false);
	    		
	    		while (!modelFacade.isJogadaTabuleiroPossivel()) { //Verifica se é possível fazer essa jogada no tabuleiro
	    			modelFacade.setMoverExploradorPossivel(false);
	    			while (!modelFacade.isMoverExploradorPossivel()) {
				    	s(modelFacade.getJogadorDaVezNome()+ ", qual explorador voce quer mover " + valorDadoDaVez + " casas? ");
				    	modelFacade.setExploradorParaMover(s.nextInt());
		    			
				    	if (!modelFacade.isExpCasaInicial(modelFacade.getExploradorParaMover()-1)) //Verifica se explorador nao está na casa final
				    		modelFacade.setMoverExploradorPossivel(true);
		    			else
			    			s("Explorador " + modelFacade.getExploradorParaMover() + " ja chegou no polo final, escolha outro!\n");
	    			}
	    			
	    			//Seleciona o tabuleiro da vez do explorador
	    			modelFacade.selecionaTabuleiroDaVez(modelFacade.getExploradorParaMover()-1);
	    			
	    			//Verificacao dado colorido
	    			if (corDadoColorido != null) {
			    		modelFacade.trataCasoDadoColorido(corDadoColorido);
		    		}
	    			
	    			//Verifica se o explorador esta na casa inicial
	    			boolean estaNaCasaInicial = modelFacade.wasExpInPoloInicial(modelFacade.getExploradorParaMover()-1); 
			    	if (estaNaCasaInicial) {
			    		//Sai do polo inicial e entra no tabuleiro
			        	s("Explorador entra no tabuleiro em qual longitude? ");
			        	modelFacade.setLongitudeInicial(s.nextInt()-1);
			        	modelFacade.setJogadaTabuleiroPossivel(modelFacade.verificaPossibilidadeInicial(
			        			modelFacade.getExploradorParaMover()-1, valorDadoDaVez, modelFacade.getLongitudeInicial(), modelFacade.getJogadorDaVez()
			        	));
			        	//jogadaTabuleiroPossivel = verificaPossibilidadeInicial(jogadorDaVez.getExploradores()[exploradorParaMover - 1], valorDadoDaVez, longitudeInicial, jogadorDaVez);
			    	}
			    	
			    	else {
			    		//Ja saiu da casa inicial, está se movimento no tabuleiro
			    		s("Explorador anda na latitude ou longitude? (Escreva 1 para latitude e 2 para longitude) ");
			    		modelFacade.setOpcaoDeMovimento(s.nextInt());
			    		modelFacade.setJogadaTabuleiroPossivel(modelFacade.verificaPossibilidade(
			        			modelFacade.getExploradorParaMover()-1, valorDadoDaVez, modelFacade.getOpcaoDeMovimento(), modelFacade.getJogadorDaVez()
			        	));
			    		//jogadaTabuleiroPossivel = verificaPossibilidade(jogadorDaVez.getExploradores()[exploradorParaMover - 1], valorDadoDaVez, opcaoDeMovimento, jogadorDaVez);
			    	}
	    		}
	    		
	    		//Exibe status do jogo
		    	modelFacade.exibe();
		    	
		    	//Verifica se a casa tem uma meta, se o jogador pode conquista-la e se ainda ha metas sobrando
		    	int posicaoI = modelFacade.getExploradorDaVezIMatriz(modelFacade.getExploradorParaMover()-1);
		    	int posicaoJ = modelFacade.getExploradorDaVezJMatriz(modelFacade.getExploradorParaMover()-1);
		    	
		    	if (modelFacade.isMetaNaCasa(posicaoI, posicaoJ) && modelFacade.isMetaCapturavel(posicaoI, posicaoJ)) {
		    		modelFacade.setMetaNoTabuleiro(posicaoI, posicaoJ, false);
		    		modelFacade.contaPontoMetaJogador();
		    		s("Jogador " + modelFacade.getJogadorDaVezNome() + " conquistou um ponto de meta! "
		    				+ "Pontos totais: " + modelFacade.getJogadorPontosMeta() + "\n");
		    		metaCumprida = true;
		    		
		    		//Conquistou carta dinâmica
		    		//cartaEscolhida = pegaCartaDoDeck();
		    		//cartaEscolhida.exibeCartaEscolhida();
		    	}
	    	}
	    	
	    	//Se pelo menos uma meta foi capturada + todos os exploradores na casa final
	    	if (metaCumprida && (modelFacade.getJogadorDaVezExpCasaFinal()==6))
	    		fimDeJogo = true;
	    	
	    	contaVez+=1;
	    	//TODO: soh ao final da vez de um jogador que vai poder salvar o jogo
    	}
    	
    	modelFacade.declaraVencedor();
    }

	// Metodos: Auxiliares//

	// public void salvaJogo() {
	// ModelFacade.codificaTabuleiro(codeTab);
	// String s = "";
	// s = s.concat(Integer.toString(rodada));
	// for(int i = 0; i < 8; i++) {
	// for(int j = 0;j < 8; j++) {
	// s = s.concat(" " + Integer.toString(codeTab[i][j]));
	// }
	// }
	//
	// JFileChooser chooser = new JFileChooser();
	// int retval = chooser.showSaveDialog(null);
	// try{
	// FileWriter fw = new FileWriter(chooser.getSelectedFile()+".txt");
	// fw.write(s);
	// fw.flush();
	// fw.close();
	// } catch(Exception IOException) {} finally {}
	// }
	//
	// public void carregaJogo() {
	//
	// JFileChooser chooser = new JFileChooser();
	// int retval = chooser.showOpenDialog(null);
	// String out="";
	// try{
	// Scanner scan = new Scanner(chooser.getSelectedFile());
	//
	// while(scan.hasNextLine()) {
	// out= out + scan.nextLine();
	// }
	// } catch(FileNotFoundException e) {} finally {}
	//
	// String[] result = out.split(" ");
	//
	// rodada = Integer.parseInt(result[0]) - 1;
	//
	// for(int i = 0; i < 8; i++) {
	// for(int j = 0;j < 8; j++) {
	// int index = (i*8)+j + 1;
	//
	// codeTab[i][j] = Integer.parseInt(result[index]);
	// }
	// }
	//
	// ModelFacade.carregaTabuleiro(codeTab);
	// proxRodada();
	// }

	public static void s(Object o) {
		System.out.println(o);
	}

	// Metodos: Observador e Observavel //

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
		} catch (ClassCastException ignored) {
		}

		if (operacao == Operacoes.OPERACAO_OLA) {
			s("Operacao 1. Msg: " + mensagem);
			notificarObservadores(1, "Tchau");
		} else if (operacao == Operacoes.DADO_LANCADO) {
			final int dado = (int) args[1];
			final double valor2 = (double) args[2];
			s("Recebemos via mensagem o dado " + dado);
		} else {
			s(o + " " + Arrays.asList(args).toString());
		}
	}

	public void adicionarObservador(Observador o) {
		if (!observadores.contains(o)) {
			this.observadores.add(o);
		}
	}

	public void clearChanged() {
	}

	public int contarObservadores() {
		return this.observadores.size();
	}

	public void removerObservador(Observador o) {
		if (this.observadores.contains(o)) {
			this.observadores.remove(o);
		}
	}

	public void notificarObservadores(Object... args) {
		for (Observador o : this.observadores) {
			o.update(this, args);
		}
	}
}
