package controller;

import model.ModelFacade;
import util.Observavel;
import util.Observador;
import util.Operacoes;
import view.ViewFacade;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class ControllerFacade implements Observador, Observavel, Serializable {
	private static ControllerFacade controller = null;
	private static ViewFacade viewFacade = null;
	private static ModelFacade modelFacade = null;
	private boolean metaCumprida = false;
	private Scanner s = new Scanner(System.in);
	private List<Observador> observadores = new ArrayList<>();

	private ControllerFacade() {
		viewFacade = ViewFacade.getInstance();
		modelFacade = ModelFacade.getInstance();

		viewFacade.startView();
		viewFacade.addObserverToTabView(this);
		viewFacade.addObserverToJanelaView(this);
		this.adicionarObservador(viewFacade);
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

	public void jogo(int jogadorDaVez, int valorDadoDaVez, int numExploradorDaVez, int iAntigo, int jAntigo, int iNovo, int jNovo) {    	
		modelFacade.setJogadorDaVez(jogadorDaVez); 
		modelFacade.setExploradorParaMover(numExploradorDaVez);
		modelFacade.selecionaTabuleiroDaVez(modelFacade.getExploradorParaMover()-1);

		s("---------- RODADA DO JOGADOR - " + modelFacade.getJogadorDaVezNome() +" ----------\n");

		s("Jogada feita pelo jogador " + modelFacade.getJogadorDaVezNome() + ": Dado: " + valorDadoDaVez + "\n");


		//TODO: Verificacao dado colorido
		//if (corDadoColorido != null) {
		//	  modelFacade.trataCasoDadoColorido(corDadoColorido);
		//}

		//Verifica se o explorador esta na casa inicial
		boolean estaNaCasaInicial = modelFacade.wasExpInPoloInicial(modelFacade.getExploradorParaMover()-1); 
		if (estaNaCasaInicial) {
			//Sai do polo inicial e entra no tabuleiro
			modelFacade.setLongitudeInicial(jNovo);
			modelFacade.setJogadaTabuleiroPossivel(modelFacade.verificaPossibilidadeInicial(
					modelFacade.getExploradorParaMover()-1, valorDadoDaVez, modelFacade.getLongitudeInicial(), modelFacade.getJogadorDaVez()
					));
		}

		else {
			//Ja saiu da casa inicial, est? se movimento no tabuleiro
			modelFacade.setJogadaTabuleiroPossivel(modelFacade.verificaPossibilidade(
					modelFacade.getExploradorParaMover()-1, valorDadoDaVez, iAntigo, iNovo, jAntigo, jNovo, modelFacade.getJogadorDaVez()
					));
		}

		//Verifica se a movimentacao no tabuleiro foi possivel
		boolean jogadaTabuleiroPossivel = modelFacade.isJogadaTabuleiroPossivel();
		s("jogada possivel: "+jogadaTabuleiroPossivel);
		if (!jogadaTabuleiroPossivel) {
			notificarObservadores(Operacoes.VALIDADE_MOVIMENTACAO, 0);
			return;
		}
		
		else
			notificarObservadores(Operacoes.VALIDADE_MOVIMENTACAO, 1);

		//Verifica se a casa tem uma meta, se o jogador pode conquista-la e se ainda ha metas sobrando
		int posicaoI = modelFacade.getExploradorDaVezIMatriz(modelFacade.getExploradorParaMover()-1);
		int posicaoJ = modelFacade.getExploradorDaVezJMatriz(modelFacade.getExploradorParaMover()-1);

		if (modelFacade.isMetaNaCasa(posicaoI, posicaoJ) && modelFacade.isMetaCapturavel(posicaoI, posicaoJ)) {
			modelFacade.setMetaNoTabuleiro(posicaoI, posicaoJ, false);
			modelFacade.contaPontoMetaJogador();
			s("Jogador " + modelFacade.getJogadorDaVezNome() + " conquistou um ponto de meta! "
					+ "Pontos totais: " + modelFacade.getJogadorPontosMeta() + "\n");
			metaCumprida = true;

			//TODO: Conquistou carta dinamica
			//cartaEscolhida = pegaCartaDoDeck();
			//cartaEscolhida.exibeCartaEscolhida();
		}
    	 

    	 //Se pelo menos uma meta foi capturada + todos os exploradores na casa final
    	 if (metaCumprida && (modelFacade.getJogadorDaVezExpCasaFinal()==6)) {
    		 String[] pontuacaoFinal = modelFacade.montaPontuacaoFinal();
    		 notificarObservadores(Operacoes.TERMINOU_JOGO, pontuacaoFinal);
    		 return;
    	 }
    		 
    	 //Exibe status do jogo
    	 modelFacade.exibe();
    }
	
	public void salvarJogo() {
		JFileChooser fileChooser  = new JFileChooser();
        fileChooser.showSaveDialog(null);
        try{
        	FileOutputStream fileOutput = new FileOutputStream(fileChooser.getSelectedFile()+".txt");
        	ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
        	objectOutput.writeObject(this);
        	objectOutput.close();
        	fileOutput.close();
            notificarObservadores(Operacoes.EXIT);
        } 
        catch(Exception e) {
        	s("Erro na gravacao do arquivo.\n");
        	e.printStackTrace();
        }
	}
	
	public void carregarJogo() {
		JFileChooser fileChooser  = new JFileChooser();
        fileChooser.showSaveDialog(null);
        try{
        	FileInputStream fileInput = new FileInputStream(fileChooser.getSelectedFile()+".txt");
        	ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			controller = (ControllerFacade) objectInput.readObject();
			objectInput.close();
			fileInput.close();
        } 
        catch(FileNotFoundException e) {
        	s("Erro na leitura do arquivo.\n");
        	e.printStackTrace();
        }
        catch(ClassNotFoundException e1) {
        	s("Carregar Jogo - Classe ControllerFacade nao encontrada.\n");
        	e1.printStackTrace();
        }
        catch(IOException e2) {
        	s("Erro no carregamento do jogo.\n");
        	e2.printStackTrace();
        }
	}
	
	
	//Metodos: Auxiliares
	
	public static void s(Object o) {
		System.out.println(o);
	}
	
	public void exibeArray(int gameMode, String[] nomeJogadores) {
		s("gameMode: " + gameMode);
		s("Recebemos via mensagem os nomes: ");
		for (int i = 0; i < gameMode * 2; i++)
			s(nomeJogadores[i]);
	}
	
	

	// Metodos: Observador e Observavel //

	@Override
	public void update(Observavel o, Object... args) {
		s("De " + o.getClass().getSimpleName() + " Para " + this.getClass().getSimpleName());

		if (args == null) {
			return;
		}

		final int operacao = (int) args[0];
		
		if (operacao == Operacoes.INICIALIZA_MODEL) {
			s("Operacao Inicializa Model.\n");
			final int gameMode = (int) args[1];
			String[] nomeJogadores = new String[gameMode*2];

			try {
				System.arraycopy(args[2], 0, nomeJogadores, 0, gameMode*2);
			}
			catch (ClassCastException ignored) {}
			
			exibeArray(gameMode, nomeJogadores);
			modelFacade.startModel(gameMode, nomeJogadores);
			notificarObservadores(Operacoes.ORDENA_JOGADORES, modelFacade.getNumeroJogadoresOrdenados());
		} 
		
		else if (operacao == Operacoes.DADO_LANCADO) {
			s("Operacao Lanca Dado.\n");
			String mensagem = null;
			try {
				mensagem = (String) args[1];
			} 
			catch (ClassCastException ignored) {}

			final int dado = (int) args[1];
			final double valor2 = (double) args[2];
			notificarObservadores(1, "Tchau");
		}
		
		else if (operacao == Operacoes.MOVE_EXPLORADOR) {
			s("Operacao Move Explorador.\n");
			int iAntigo = (int) args[1];
			int jAntigo = (int) args[2];
			int iNovo = (int) args[3];
			int jNovo = (int) args[4];
			int dado = (int) args[5];
			int jogadorDaVez = (int) args[6];
			int exploradorDaVez = (int) args[7];
			
			if (jAntigo >= 12)
				jAntigo -= 12;
			if (jNovo >= 12)
				jNovo -= 12;

			if (iAntigo == -1 && jAntigo == -1) {
				s("casa inicial");
			}
			jogo(jogadorDaVez, dado, exploradorDaVez, iAntigo, jAntigo, iNovo, jNovo);
			notificarObservadores(1, "Tchau");
		}
		
		else if (operacao == Operacoes.CARREGAR_JOGO) {
			s("Operacao Carrega Jogo.\n");
			carregarJogo();
		}
		
		else if (operacao == Operacoes.SALVAR_JOGO) {
			s("Operacao Salva Jogo.\n");
			salvarJogo();
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
