package model;

import util.Observador;
import util.Observavel;
import util.Operacoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ModelFacade implements Observavel {
	private static ModelFacade model = null;
	private List<Observador> observadores = new ArrayList<>();
	private ModoJogo jogo = null;
	private boolean readyToStart = false;
	private int exploradorParaMover, longitudeInicial, opcaoDeMovimento; //Variaveis de input
	private boolean jogadaTabuleiroPossivel = false, moverExploradorPossivel = false, casaLiberada = false;
	private Jogador jogadorDaVez = null, jogadorAux = null;
	private CartaDinamica cartaEscolhida = null;
    private Tabuleiro tabuleiroPoloSul = null;
    private Tabuleiro tabuleiroPoloNorte = null;
    private Tabuleiro tabuleiroDaVez = null, tabuleiroAux = null; 
    private final int POLO_INICIAL_SUL = 1, POLO_INICIAL_NORTE = 2;
    //private ArrayList<CartaDinamica> deckCartaDinamica = null;
    private ArrayList<Jogador> jogadoresOrdenados = null;
    private ArrayList<Integer> valoresDados = null;
	private Dado dado = new Dado();
    private Scanner s = new Scanner(System.in);
	
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
		
		if (this.jogo != null)
			initGame();
	}
	
	private void initGame() {
		this.tabuleiroPoloSul = new Tabuleiro(ModoJogo.NUM_JOGADORES);
		this.tabuleiroPoloNorte = new Tabuleiro(ModoJogo.NUM_JOGADORES);
		//this.movePecas.setDeckCartaDinamica(jogo.getDeckCartas());
		this.jogadoresOrdenados = jogo.getJogadoresOrdenados();
		this.valoresDados = jogo.getDadosJogadores();
        initTabuleiro(jogo.getJogadores());
    }
	
    private void initTabuleiro(Jogador[] listaDeJogadores) {
    	this.tabuleiroPoloSul.montaTabuleiro();
    	this.tabuleiroPoloNorte.montaTabuleiro();
        
        for (int i=0; i<listaDeJogadores.length; i++) {
        	
        	if (listaDeJogadores[i].getNumeroDoJogador()%2 != 0) {
        		listaDeJogadores[i].setPoloInicial(POLO_INICIAL_SUL);
        		this.tabuleiroPoloSul.montaPoloInicial(listaDeJogadores[i]);
        	}
        	else {
        		listaDeJogadores[i].setPoloInicial(POLO_INICIAL_NORTE);
        		this.tabuleiroPoloNorte.montaPoloInicial(listaDeJogadores[i]);
        	}
        	
        	this.tabuleiroPoloSul.setPodeMover(false);
        	this.tabuleiroPoloNorte.setPodeMover(false);
        }
        
        checkIfGameReady();
    }
    
    private void checkIfGameReady() {
    	if ((this.jogadoresOrdenados != null) &&
    			(this.tabuleiroPoloSul != null) && 
    			(this.tabuleiroPoloNorte != null) && 
    			(this.valoresDados != null) &&
    			(this.dado != null)) 
    		this.readyToStart = true;
    	
    	else {
    		this.readyToStart = false;
    		s("ModelFacade - boolean isReadyToStart falso. \n");
    	}
    }

    
    //Metodos: Movimentacao dos Exploradores
    
    	//Seleciona qual vai ser o tabuleiro que vai ser utilizado na rodada atual
    public void selecionaTabuleiroDaVez(int numExp) {
    	if (getExploradorDaVez(numExp).getNumeroDoJogador()%2 != 0 && !getExploradorDaVez(numExp).isInMatrizOposta())
			tabuleiroDaVez = tabuleiroPoloSul;
    	else if (getExploradorDaVez(numExp).getNumeroDoJogador()%2 != 0 && getExploradorDaVez(numExp).isInMatrizOposta())
			tabuleiroDaVez = tabuleiroPoloNorte;
    	else if (getExploradorDaVez(numExp).getNumeroDoJogador()%2 == 0 && !getExploradorDaVez(numExp).isInMatrizOposta())
			tabuleiroDaVez = tabuleiroPoloNorte;
    	else if (getExploradorDaVez(numExp).getNumeroDoJogador()%2 == 0 && getExploradorDaVez(numExp).isInMatrizOposta())
			tabuleiroDaVez = tabuleiroPoloSul;
    }
    
    	//Atualiza o tabuleiro
    public void movimentaTabuleiro (Tabuleiro tabuleiro, Explorador explorador, int[] coordenadasAntigas) {
    	//Remove o explorador da posicao antiga (se ele nao estava no polo inicial antes)
    	if (!explorador.wasInPoloInicial(coordenadasAntigas[0]))
    		tabuleiro.getMatrix()[coordenadasAntigas[0]][coordenadasAntigas[1]].removeExploradorDaCasa(explorador);
    	
    	//Verifica se o explorador ja chegou na casa final
    	if (!explorador.getCasaFinal()) {
    		tabuleiro.getMatrix()[explorador.getIMatriz()][explorador.getJMatriz()].moverExploradores(jogadorDaVez, explorador.getNumero());

    		//Verifica se foi capturado um adversario
    		if (tabuleiro.getMatrix()[explorador.getIMatriz()][explorador.getJMatriz()].getAdversarioCapturado() != null) {

    			System.out.println("Explorador capturado: " 
    					+ tabuleiro.getMatrix()[explorador.getIMatriz()][explorador.getJMatriz()].getAdversarioCapturado().getNumero() 
    					+"!\nEle sera retornado ao seu polo de origem!\n");

    			//Manda o adversario de volta pro polo inicial
    			tabuleiro.setPosicaoPoloInicial(
    					tabuleiro.getMatrix()[explorador.getIMatriz()][explorador.getJMatriz()].getAdversarioCapturado().getNumero(), 
    					tabuleiro.getMatrix()[explorador.getIMatriz()][explorador.getJMatriz()].getAdversarioCapturado()
    					);

    			tabuleiro.getMatrix()[explorador.getIMatriz()][explorador.getJMatriz()].setAdversarioCapturado(null);
    		}
    	}
    	
    	//Se ele ja chegou no polo final
    	else
    		jogadorDaVez.setPosicaoPoloFinal(explorador.getNumero()-1, explorador);
    }
    
    
    	//Atualiza a latitude (i) do explorador
    public void movimentaLatitude(Explorador explorador, int dado) {
    	int[] coordenadasAntigas = {explorador.getIMatriz(),explorador.getJMatriz()};
    	
    	//Verifica se o explorador vai trocar de polo
		if (explorador.isInMatrizOposta()) {
			
			//Atualiza o tabuleiro sob o qual ele opera
			if (tabuleiroDaVez == tabuleiroPoloSul)
				tabuleiroAux = tabuleiroPoloNorte;
			else
				tabuleiroAux = tabuleiroPoloSul;
			
			//Se ele esta trocando de polo, atualiza as posicoes que ele consegue atravessar de tabuleiro
			if (explorador.isMudandoDeMatriz()) {
				int novoI = dado - explorador.getIMatriz() - 1;
				explorador.setIMatriz(novoI);
				if (explorador.getJMatriz() == 0)
					explorador.setJMatriz(5);
				else if (explorador.getJMatriz() == 5)
					explorador.setJMatriz(0);
				else if (explorador.getJMatriz() == 6)
					explorador.setJMatriz(11);
				else if (explorador.getJMatriz() == 11)
					explorador.setJMatriz(6);

				System.out.println(explorador.getJMatriz() + " " + explorador.getIMatriz());
				movimentaTabuleiro(tabuleiroAux, explorador, coordenadasAntigas);
				explorador.setMudandoDeMatriz(false);
			}
			
			//Se ele ja trocou de polo
			else {				
				//Chegou na casa final do polo oposto
				if ((explorador.getIMatriz() + dado) == 6 && explorador.isInMatrizOposta()) {
					explorador.setCasaFinal(true);
					jogadorDaVez.setQtdExpCasaFinal(jogadorDaVez.getQtdExpCasaFinal() + 1);
					jogadorDaVez.setPosicaoPoloFinal(explorador.getNumero()-1, explorador);
				}
				
				//Ultrapassou a casa final do polo oposto
				else if (((explorador.getIMatriz() + dado) > 6) && explorador.isInMatrizOposta()) {
					int novoI = explorador.getIMatriz() + dado - 6;
					explorador.setIMatriz(novoI);
				}
				
				//Nao chegou na casa final ainda
				else
					explorador.setIMatriz(explorador.getIMatriz() + dado);
				
				System.out.println(explorador.getJMatriz() + " " + explorador.getIMatriz());
				movimentaTabuleiro(tabuleiroAux, explorador, coordenadasAntigas);
			}

		}
		
		//Se ele ainda nao trocou de polo
		else {
    		explorador.setIMatriz(explorador.getIMatriz() - dado);
    		movimentaTabuleiro (tabuleiroDaVez, explorador, coordenadasAntigas);
		}
    }
    
    
    	//Atualiza a longitude (j) do explorador
    public void movimentaLongitude(Explorador explorador, int dado) {
    	int[] coordenadasAntigas = {explorador.getIMatriz(),explorador.getJMatriz()};
    	
		if (explorador.getJMatriz() + dado < 12) 
	    	explorador.setJMatriz(explorador.getJMatriz() + dado);
		else 
	    	explorador.setJMatriz(explorador.getJMatriz() - 12);

    	movimentaTabuleiro(tabuleiroDaVez, explorador, coordenadasAntigas);
    }
    
    	//Verifica se o explorador pode sair do polo inicial
    public boolean verificaPossibilidadeInicial(int numExp, int dado, int longitude, Jogador jogador) {
    	int[] coordenadasAntigas = {getExploradorDaVez(numExp).getiInicial(),getExploradorDaVez(numExp).getjInicial()};
    	
    	int coordenadaI = getExploradorDaVez(numExp).getiInicial() - dado;
    	int coordenadaJ = getExploradorDaVez(numExp).getjInicial() + longitude;
    	int coordenadaEsperada, start=1, end=dado;
    	
    	//Nao pode mover o tabuleiro
    	tabuleiroDaVez.setPodeMover(tabuleiroDaVez.verificaPossibilidadeMovimentoInicial(longitude));
    	if (!tabuleiroDaVez.getPodeMover()) {
    		System.out.println("Nao foi possivel fazer essa jogada inicial, tente novamente.\n");
    		return false;
    	}
    	
    	//Verifica casa fechada/cheia
    	for (int percorre = start; percorre <= end; percorre++) {
    		coordenadaEsperada = 6 - percorre;
    		casaLiberada = tabuleiroDaVez.getMatrix()[coordenadaEsperada][longitude].casaLiberada(jogador, getExploradorDaVez(numExp).getNumero());
			
			//Casa esta fechada ou cheia
        	if (!casaLiberada) {
        		System.out.println("Casa não está liberada! Escolha outra.\n");
        		return false;
        	}
    	}
    	
    	//Casa esta liberada
    	
    	//Coloca null na posicao que o explorador ocupava no vetor polo inicial
    	tabuleiroDaVez.setPosicaoPoloInicialAsNull(getExploradorDaVez(numExp).getNumero(), getExploradorDaVez(numExp));
    	
    	//Define o i e j do explorador na matriz e coloca ele na matriz
    	getExploradorDaVez(numExp).setIMatriz(coordenadaI);
    	getExploradorDaVez(numExp).setJMatriz(coordenadaJ);
		movimentaTabuleiro(tabuleiroDaVez, getExploradorDaVez(numExp), coordenadasAntigas);
    	return true;
    }

    	//Verifica se o explorador fazer o movimento (nao sendo a jogada inicial)
    public boolean verificaPossibilidade(int numExp, int dado, int opcaoDeMovimento, Jogador jogador) {
    	int coordenadaInicialI, coordenadaInicialJ;
    	int coordenadaEsperada;
    	int start, end;
    	
    	//Nao pode mover o tabuleiro
    	tabuleiroDaVez.setPodeMover(tabuleiroDaVez.verificaPossibilidadeMovimento(getExploradorDaVez(numExp), dado, opcaoDeMovimento));
    	if (!tabuleiroDaVez.getPodeMover()) {
    		System.out.println("Nao foi possivel fazer essa jogada, tente novamente.\n");
    		return false;
    	}
    	
    	
    	//Verifica casa fechada/cheia
    	coordenadaInicialI = getExploradorDaVez(numExp).getIMatriz();
    	coordenadaInicialJ = getExploradorDaVez(numExp).getJMatriz();
    	tabuleiroAux = tabuleiroDaVez;
    	
    	//Opcao de movimento 1 --> latitude    	
    	if (opcaoDeMovimento == 1) {
    		
    		//Se o explorador vai mudar de matriz
    		if (getExploradorDaVez(numExp).isMudandoDeMatriz()) {    			
    			//Atualiza o tabuleiro sob o qual ele opera
    			if (tabuleiroDaVez == tabuleiroPoloSul)
    				tabuleiroAux = tabuleiroPoloNorte;
    			else
    				tabuleiroAux = tabuleiroPoloSul;
    			
    			coordenadaEsperada = 6 - coordenadaInicialI;
    			start = coordenadaEsperada;
    			end = coordenadaInicialI;
    			
    		}
    		
    		else {
    			
    			//Se ele mudou de polo
    			if (getExploradorDaVez(numExp).isInMatrizOposta()) {
    				
    				//Atualiza o tabuleiro sob o qual ele opera
        			if (tabuleiroDaVez == tabuleiroPoloSul)
        				tabuleiroAux = tabuleiroPoloNorte;
        			else
        				tabuleiroAux = tabuleiroPoloSul;
    				
        			coordenadaEsperada = coordenadaInicialI + dado;
        			start = coordenadaInicialI;
        			end = coordenadaEsperada;
        		}
    	    	
    			//Se ele nao mudou de polo
        		else {
        			coordenadaEsperada = coordenadaInicialI - dado;
        			start = coordenadaEsperada;
        			end = coordenadaInicialI;
        		}
    		}
	    		
    		for (int percorre = start; percorre < end; percorre++) {
    			casaLiberada = tabuleiroAux.getMatrix()[percorre][coordenadaInicialJ].casaLiberada(jogador, getExploradorDaVez(numExp).getNumero());
    			
    			//Casa esta fechada ou cheia
	        	if (!casaLiberada) {
	        		System.out.println("Casa não está liberada! Escolha outra.\n");
	        		return false;
	        	}
	        }
    		
    		movimentaLatitude(getExploradorDaVez(numExp), dado);
    	}
    	
    	//Opcao de movimento 2 --> longitude
    	else if (opcaoDeMovimento == 2) {
    		coordenadaEsperada = coordenadaInicialJ + dado;
    		for (int percorre = coordenadaInicialJ; percorre < coordenadaEsperada; percorre++) {
    			casaLiberada = tabuleiroDaVez.getMatrix()[coordenadaInicialI][percorre].casaLiberada(jogador, getExploradorDaVez(numExp).getNumero());
    			
    			//Casa esta fechada ou cheia
	        	if (!casaLiberada) {
	        		System.out.println("Casa não está liberada! Escolha outra.\n");
	        		return false;
	        	}
        	}

    		movimentaLongitude(getExploradorDaVez(numExp), dado);    		
    	}
		
    	return true;
    }
    
    	//Trata dado colorido
    public void trataCasoDadoColorido(String corDadoColorido) {
    	System.out.println("Cor do dado colorido: " + corDadoColorido +"\n");
    	boolean dadoColoridoIsValid = false;
    	
    	//Se nao for a cor de nenhum dos jogadores
    	for (int i=0; i<jogadoresOrdenados.size(); i++) {
    		if (jogadoresOrdenados.get(i).getCor().equals(corDadoColorido)) {
    			dadoColoridoIsValid = true;
    			break;
    		}
    	}
    	
    	if (!dadoColoridoIsValid) {
    		System.out.println("Cor do dado não é de nenhum jogador.\n");
    		return;
    	}
    	
    	moverExploradorPossivel = false;
    	boolean isJogador = corDadoColorido.equals(jogadorDaVez.getCor());
    	Jogador aliado = jogadorDaVez.getAliado(jogadoresOrdenados);
    	boolean isAliado = ((aliado != null) && (corDadoColorido.equals(aliado.getCor())));
    	boolean isOponente = !isAliado && !isJogador;
    	
    	//Se for a cor do jogador da vez
		if (isJogador) {
			
			//Escolhe o explorador dele para conquistar o polo oposto
			while (!moverExploradorPossivel) {
    			System.out.println(jogadorDaVez.getName() + ", qual explorador vc quer mandar para o polo final? ");
    			exploradorParaMover = s.nextInt();
    			
    			//Se o explorador não está no polo final
    			if (!jogadorDaVez.getExploradores()[exploradorParaMover - 1].getCasaFinal()) {
    				moverExploradorPossivel = true;
    			}
	    			
    			else
	    			System.out.println("\nExplorador " + exploradorParaMover + " ja chegou no polo final, escolha outro!\n");
			}
			
			//Se ele estiver no polo inicial, setar posicao no polo inicial como nula
			if (jogadorDaVez.getExploradores()[exploradorParaMover-1].wasInPoloInicial()) {
				tabuleiroDaVez.setPosicaoPoloInicialAsNull(jogadorDaVez.getExploradores()[exploradorParaMover-1].getNumero(), 
															jogadorDaVez.getExploradores()[exploradorParaMover-1]);
			}
			//Atualiza o tabuleiro
			else {
				int posI = jogadorDaVez.getExploradores()[exploradorParaMover - 1].getIMatriz();
				int posJ = jogadorDaVez.getExploradores()[exploradorParaMover - 1].getJMatriz();
				tabuleiroDaVez.getMatrix()[posI][posJ].removeExploradorDaCasa(jogadorDaVez.getExploradores()[exploradorParaMover - 1]);
			}

			//Manda-o para o polo final
			jogadorDaVez.getExploradores()[exploradorParaMover - 1].setCasaFinal(true);
			jogadorDaVez.setQtdExpCasaFinal(jogadorDaVez.getQtdExpCasaFinal() + 1);
	    	jogadorDaVez.setPosicaoPoloFinal(jogadorDaVez.getExploradores()[exploradorParaMover - 1].getNumero()-1,
	    										jogadorDaVez.getExploradores()[exploradorParaMover - 1]);
		}
		
		//Se tiver aliado e a cor for dele
		else if (isAliado){
			jogadorAux = jogadoresOrdenados.get(jogadorDaVez.getAliadoIndex(jogadoresOrdenados));
			
			//Escolhe o explorador dele para conquistar o polo oposto
			while (!moverExploradorPossivel) {
    			System.out.println(jogadorDaVez.getName() + ", qual explorador do seu aliado vc quer mandar para o polo final? ");
    			exploradorParaMover = s.nextInt();
    			
    			//Se o explorador não está no polo final
    			if (!jogadorAux.getExploradores()[exploradorParaMover - 1].getCasaFinal()) {
    				moverExploradorPossivel = true;
    			}
	    			
    			else
	    			System.out.println("\nExplorador " + exploradorParaMover + " ja chegou no polo final, escolha outro!\n");
			}
			
			//Se ele estiver no polo inicial, setar posicao no polo inicial como nula
			if (jogadorAux.getExploradores()[exploradorParaMover-1].wasInPoloInicial()) {
				tabuleiroDaVez.setPosicaoPoloInicialAsNull(jogadorAux.getExploradores()[exploradorParaMover-1].getNumero(),
																jogadorAux.getExploradores()[exploradorParaMover-1]);
			}
			//Atualiza o tabuleiro
			else {
				int posI = jogadorAux.getExploradores()[exploradorParaMover - 1].getIMatriz();
				int posJ = jogadorAux.getExploradores()[exploradorParaMover - 1].getJMatriz();
				tabuleiroDaVez.getMatrix()[posI][posJ].removeExploradorDaCasa(jogadorAux.getExploradores()[exploradorParaMover - 1]);
			}
			
			//Manda-o para o polo final
			jogadorAux.getExploradores()[exploradorParaMover - 1].setCasaFinal(true);
			jogadorAux.setQtdExpCasaFinal(jogadorAux.getQtdExpCasaFinal() + 1);
			jogadorAux.setPosicaoPoloFinal(jogadorAux.getExploradores()[exploradorParaMover - 1].getNumero()-1,
											jogadorAux.getExploradores()[exploradorParaMover - 1]);
		}
		
		//Se for a cor do oponente
		else {
			int indexOponente = -1;
			
			//Pega o index do oponente
			for (int i=0; i<jogadoresOrdenados.size(); i++) {
	    		if (isOponente && (jogadoresOrdenados.get(i).getCor().equals(corDadoColorido))) {
	    			indexOponente = i;
	    		}
	    	}
			
			//Verifica se encontrou mesmo o oponente
			if (indexOponente < 0) {
				System.out.println("Cor do dado não foi encontrada.\n");
				return;
			}
			
			jogadorAux = jogadoresOrdenados.get(indexOponente);
			
			//Escolhe o explorador do oponente para levar de volta pro polo de origem
			while (!moverExploradorPossivel) {
    			System.out.println(jogadorDaVez.getName() + ", qual explorador do seu oponente vc quer mandar para o polo inicial? ");
    			exploradorParaMover = s.nextInt();
    			
    			//Polo inicial ainda cheio
    			if(tabuleiroDaVez.poloInicialCheio()) {
    				System.out.println("\nTodos os exploradores do oponente ainda estao no polo inicial!\n");
					return;
    			}
    			
    			//Se o explorador já está no polo inicial
    			else {
    				if (jogadorAux.getExploradores()[exploradorParaMover-1].wasInPoloInicial()) {
    					System.out.println("\nExplorador " + exploradorParaMover + " ainda está no polo inicial, escolha outro!\n");
    				}
    				
    				else {
    					moverExploradorPossivel = true;
    				}
    				
    			}
			}
			
			//Se ele estiver no polo final, setar posicao no polo final como nula
			if (jogadorAux.getExploradores()[exploradorParaMover - 1].getCasaFinal()) {
				jogadorAux.getExploradores()[exploradorParaMover - 1].setCasaFinal(false);
				jogadorAux.setQtdExpCasaFinal(jogadorAux.getQtdExpCasaFinal() - 1);
				jogadorAux.setPosicaoPoloFinal(jogadorAux.getExploradores()[exploradorParaMover-1].getNumero()-1,null);
			}
			
			//Atualiza o tabuleiro
			else {
				int posI = jogadorAux.getExploradores()[exploradorParaMover - 1].getIMatriz();
				int posJ = jogadorAux.getExploradores()[exploradorParaMover - 1].getJMatriz();
				tabuleiroDaVez.getMatrix()[posI][posJ].removeExploradorDaCasa(jogadorAux.getExploradores()[exploradorParaMover - 1]);
				
				//Manda-o para o polo inicial
				tabuleiroDaVez.setPosicaoPoloInicial(jogadorAux.getExploradores()[exploradorParaMover-1].getNumero(),
														jogadorAux.getExploradores()[exploradorParaMover-1]);
			}
			
		}
		
    	exibe();
    }
    
    	//Faz o ranking dos jogadores
    public void declaraVencedor() {
    	final int tam = jogadoresOrdenados.size();
    	ArrayList<Jogador> ranking = new ArrayList<Jogador>(tam);
    	int vencedor = 0, vencedorTemp = 0;
    	
    	//Soma os pontos de cada jogador
    	//TODO: Implementar Comparator na classe Jogador para fazer o sort tanto por dado quanto por pontuacao
    	for (int i=0; i<tam; i++) {
    		jogadoresOrdenados.get(i).somaPontuacao();
    		vencedorTemp = jogadoresOrdenados.get(i).getPontuacaoFinal();
    		
    		if (vencedorTemp >= vencedor) {
    			vencedor = vencedorTemp;
    			ranking.add(0,jogadoresOrdenados.get(i));
    		}
    		else
    			ranking.add(jogadoresOrdenados.get(i));
    	}
    	
    	//Modo1x1
    	if (tam == 2) {
    		if (ranking.get(0).getPontuacaoFinal() == ranking.get(1).getPontuacaoFinal())
    			System.out.println("Jogadores empatados!\n");
    		else
    			System.out.println("PARABENS "+ ranking.get(0).getName()+ "! Você é o vencedor!\n");
    	}
    	
    	//Modo2x2
    	else if (tam == 4) {
    		int somaPoloNorte = 0, somaPoloSul = 0;
    		
    		for(int j=0; j<tam; j++) {
    			if (ranking.get(j).getPoloInicial() == POLO_INICIAL_SUL)
    				somaPoloSul+=ranking.get(j).getPontuacaoFinal();
    			else if (ranking.get(j).getPoloInicial() == POLO_INICIAL_NORTE)
    				somaPoloNorte+=ranking.get(j).getPontuacaoFinal();
    		}
    		
    		if (somaPoloNorte > somaPoloSul)
    			System.out.println("PARABENS DUPLA DO POLO NORTE! Voces são os vencedores!\n");
    		else if (somaPoloNorte < somaPoloSul)
        		System.out.println("PARABENS DUPLA DO POLO SUL! Voces são os vencedores!\n");
    		else
    			System.out.println("Pontuacao das duas duplas empatadas!\n");
    		
    	}
    	
    	System.out.println("O ranking final do jogo é:\n");
    	for(int j=0; j<tam; j++) {
    		System.out.println("LUGAR " + j + " - " + ranking.get(j).getName() 
    				+ " - " + ranking.get(j).getPontuacaoFinal() + " PONTOS.\n");
    	}
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

	
	
	
    //Metodos: Auxiliares//
	
  	public static void s(Object o) {
  		System.out.println(o);
  	}
    
    public void exibe() {
    	System.out.println(" -- Polo Sul --");
    	tabuleiroPoloSul.exibeTabuleiro();
		System.out.println("Polo Inicial");
    	tabuleiroPoloSul.exibePoloInicial();
		System.out.println("\nPolo Final");
    	jogadorDaVez.exibePoloFinal();

		System.out.println();
		System.out.println("\n -- Polo Norte -- ");
    	tabuleiroPoloNorte.exibeTabuleiro();
		System.out.println("Polo Inicial");
		tabuleiroPoloNorte.exibePoloInicial();
		System.out.println("\nPolo Final");
		jogadorDaVez.exibePoloFinal();
		System.out.println();
    }
    
    public int[] getNumeroJogadoresOrdenados() {
    	int[] numeroDosJogadoresOrdenados = new int[4];
    	for (int i = 0; i < jogadoresOrdenados.size(); i++) {
    		numeroDosJogadoresOrdenados[i] = jogadoresOrdenados.get(i).getNumeroDoJogador();
    	}
    	return numeroDosJogadoresOrdenados;
    }
    
    public String getJogadorDaVezNome() {
    	return jogadorDaVez.getName();
    }
    
    public String getJogadorDaVezCor() {
    	return jogadorDaVez.getCor();
    }
    
    public Explorador getExploradorDaVez(int numExp) {
    	return jogadorDaVez.getExploradores()[numExp];
    }
    
    public boolean isExpCasaInicial(int numExp) {
    	return getExploradorDaVez(numExp).getCasaFinal();
    }
    
    public boolean wasExpInPoloInicial(int numExp) {
    	return getExploradorDaVez(numExp).wasInPoloInicial();
    }
    
    public int getJogadorDaVezExpCasaFinal() {
    	return jogadorDaVez.getQtdExpCasaFinal();
    }
    
    public int getJogadorPontosMeta() {
    	return jogadorDaVez.getPontosMeta();
    }
    
    public int getExploradorDaVezIMatriz(int numExp) {
    	return getExploradorDaVez(numExp).getIMatriz();
    }
    
    public int getExploradorDaVezJMatriz(int numExp) {
    	return getExploradorDaVez(numExp).getJMatriz();
    }
    
    public Meta getMetaTabuleiro(int posicaoI, int posicaoJ) {
    	return tabuleiroDaVez.getMatrix()[posicaoI][posicaoJ].getMeta();
    }
    
    public boolean isMetaNaCasa(int posicaoI, int posicaoJ) {
    	return getMetaTabuleiro(posicaoI, posicaoJ).isMetaNoTabuleiro();
    }
    
    public boolean isMetaCapturavel(int posicaoI, int posicaoJ) {
    	return getMetaTabuleiro(posicaoI, posicaoJ).isCapturavel();
    }
    
    public void setMetaNoTabuleiro(int posicaoI, int posicaoJ, boolean bool) {
    	tabuleiroDaVez.getMatrix()[posicaoI][posicaoJ].getMeta().setMetaNoTabuleiro(bool);
    }
    
    public void setMetaCapturavel(int posicaoI, int posicaoJ, boolean bool) {
    	tabuleiroDaVez.getMatrix()[posicaoI][posicaoJ].getMeta().setCapturavel(bool);
    }
    
    public void contaPontoMetaJogador() {
    	jogadorDaVez.setMeta(jogadorDaVez.getPontosMeta() + 1);
    }
    

    
    
	//Metodos: Getter e Setter//
	
	public ModoJogo getJogo() {
		return jogo;
	}

	public void setJogo(ModoJogo jogo) {
		this.jogo = jogo;
	}

	public boolean isReadyToStart() {
		return readyToStart;
	}

	public void setReadyToStart(boolean readyToStart) {
		this.readyToStart = readyToStart;
	}
	
	public int getExploradorParaMover() {
		return exploradorParaMover;
	}

	public void setExploradorParaMover(int exploradorParaMover) {
		this.exploradorParaMover = exploradorParaMover;
	}

	public int getLongitudeInicial() {
		return longitudeInicial;
	}

	public void setLongitudeInicial(int longitudeInicial) {
		this.longitudeInicial = longitudeInicial;
	}

	public int getOpcaoDeMovimento() {
		return opcaoDeMovimento;
	}

	public void setOpcaoDeMovimento(int opcaoDeMovimento) {
		this.opcaoDeMovimento = opcaoDeMovimento;
	}

	public boolean isJogadaTabuleiroPossivel() {
		return jogadaTabuleiroPossivel;
	}

	public void setJogadaTabuleiroPossivel(boolean jogadaTabuleiroPossivel) {
		this.jogadaTabuleiroPossivel = jogadaTabuleiroPossivel;
	}

	public boolean isMoverExploradorPossivel() {
		return moverExploradorPossivel;
	}

	public void setMoverExploradorPossivel(boolean moverExploradorPossivel) {
		this.moverExploradorPossivel = moverExploradorPossivel;
	}

	public boolean isCasaLiberada() {
		return casaLiberada;
	}

	public void setCasaLiberada(boolean casaLiberada) {
		this.casaLiberada = casaLiberada;
	}
	
	public Jogador getJogadorDaVez() {
		return jogadorDaVez;
	}

	public void setJogadorDaVez(ArrayList<Jogador> jogadores,int numeroDoJogador) {
		this.jogadorDaVez = jogadores.get(numeroDoJogador);
	}

	public Jogador getJogadorAux() {
		return jogadorAux;
	}

	public void setJogadorAux(Jogador jogadorAux) {
		this.jogadorAux = jogadorAux;
	}

	public CartaDinamica getCartaEscolhida() {
		return cartaEscolhida;
	}

	public void setCartaEscolhida(CartaDinamica cartaEscolhida) {
		this.cartaEscolhida = cartaEscolhida;
	}

	public Tabuleiro getTabuleiroPoloSul() {
		return tabuleiroPoloSul;
	}

	public void setTabuleiroPoloSul(Tabuleiro tabuleiroPoloSul) {
		this.tabuleiroPoloSul = tabuleiroPoloSul;
	}

	public Tabuleiro getTabuleiroPoloNorte() {
		return tabuleiroPoloNorte;
	}

	public void setTabuleiroPoloNorte(Tabuleiro tabuleiroPoloNorte) {
		this.tabuleiroPoloNorte = tabuleiroPoloNorte;
	}

	public Tabuleiro getTabuleiroDaVez() {
		return tabuleiroDaVez;
	}

	public void setTabuleiroDaVez(Tabuleiro tabuleiroDaVez) {
		this.tabuleiroDaVez = tabuleiroDaVez;
	}

	public Tabuleiro getTabuleiroAux() {
		return tabuleiroAux;
	}

	public void setTabuleiroAux(Tabuleiro tabuleiroAux) {
		this.tabuleiroAux = tabuleiroAux;
	}

//	public ArrayList<CartaDinamica> getDeckCartaDinamica() {
//		return deckCartaDinamica;
//	}
//
//	public void setDeckCartaDinamica(ArrayList<CartaDinamica> deckCartaDinamica) {
//		this.deckCartaDinamica = deckCartaDinamica;
//	}

	public ArrayList<Jogador> getJogadoresOrdenados() {
		return jogadoresOrdenados;
	}

	public void setJogadoresOrdenados(ArrayList<Jogador> jogadoresOrdenados) {
		this.jogadoresOrdenados = jogadoresOrdenados;
	}

	public Dado getDado() {
		return dado;
	}
	
	public int getValorDadoLancado() {
		return dado.lanca();
	}
	
	public String getValorDadoColorido(int dado1, int dado2) {
		return dado.getDadoColorido(dado1, dado2);
	}

	public void setDado(Dado dado) {
		this.dado = dado;
	}

	public int getPOLO_INICIAL_SUL() {
		return POLO_INICIAL_SUL;
	}

	public int getPOLO_INICIAL_NORTE() {
		return POLO_INICIAL_NORTE;
	}
	
	public ArrayList<Integer> getValoresDados() {
		return valoresDados;
	}

	public void setValoresDados(ArrayList<Integer> valoresDados) {
		this.valoresDados = valoresDados;
	}

}
