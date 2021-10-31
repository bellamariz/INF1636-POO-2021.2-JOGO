package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

class MovePecas {
	private int exploradorParaMover, longitudeInicial, opcaoDeMovimento; //Variaveis de input
	private boolean fimDeJogo = false, jogadaTabuleiroPossivel = false, moverExploradorPossivel = false, casaLiberada = false, metaCumprida = false;
	private Jogador jogadorDaVez = null, jogadorAux = null;
	private Dado dado = new Dado();
	private CartaDinamica cartaEscolhida = null;
    private Tabuleiro tabuleiroPoloSul = null;
    private Tabuleiro tabuleiroPoloNorte = null;
    private Tabuleiro tabuleiroDaVez = null, tabuleiroAux = null; 
    private final int POLO_INICIAL_SUL = 1, POLO_INICIAL_NORTE = 2;
    private ArrayList<CartaDinamica> deckCartaDinamica = null;
    private ArrayList<Jogador> jogadoresOrdenados = null;
    private ArrayList<Integer> valoresDados = null;
    private static ModoJogo jogo = null;
    private Scanner s = new Scanner(System.in);
    
    public MovePecas(int mode, String[] nomes) {
    	jogo = ModoJogo.getInstance(mode, nomes);
    	initGame();
    }
    
    private void initGame() {
    	this.tabuleiroPoloSul = new Tabuleiro(ModoJogo.NUM_JOGADORES);
    	this.tabuleiroPoloNorte = new Tabuleiro(ModoJogo.NUM_JOGADORES);
    	this.deckCartaDinamica = jogo.getDeckCartas();
    	this.jogadoresOrdenados = jogo.getJogadoresOrdenados();
    	this.valoresDados = jogo.getDadosJogadores();
        inicializaTabuleiro(jogo.getJogadores());
    }
    
	//Carta escolhida retirada do topo do deck e colocada ao final
  	public CartaDinamica pegaCartaDoDeck() {
  		CartaDinamica carta = this.deckCartaDinamica.get(0);
  		this.deckCartaDinamica.remove(0);
  		this.deckCartaDinamica.add(carta);
  		return carta;
  	}
    
    //Inicializa os tabuleiros com os elementos
    public void inicializaTabuleiro(Jogador[] listaDeJogadores) {
        this.tabuleiroPoloSul.montaTabuleiro();
        this.tabuleiroPoloNorte.montaTabuleiro();
        
        for (int i=0; i<listaDeJogadores.length; i++) {
        	//Jogador impar inicia no polo sul, jogador par no polo norte
        	if (listaDeJogadores[i].getNumeroDoJogador()%2 != 0) {
        		listaDeJogadores[i].setPoloInicial(POLO_INICIAL_SUL);
        		tabuleiroPoloSul.montaPoloInicial(listaDeJogadores[i]);
        	}
        	else {
        		listaDeJogadores[i].setPoloInicial(POLO_INICIAL_NORTE);
        		tabuleiroPoloNorte.montaPoloInicial(listaDeJogadores[i]);
        	}
        	
        	tabuleiroPoloSul.setPodeMover(false);
        	tabuleiroPoloNorte.setPodeMover(false);
        }
        
    	if (jogadoresOrdenados != null) {
    		//jogo();
    	}
    	
    }
    
    //Seleciona qual vai ser o tabuleiro que vai ser utilizado na rodada atual
    private void selecionaTabuleiroDaVez(Explorador explorador) {
    	if (explorador.getNumeroDoJogador()%2 != 0 && !explorador.isInMatrizOposta())
			tabuleiroDaVez = tabuleiroPoloSul;
    	else if (explorador.getNumeroDoJogador()%2 != 0 && explorador.isInMatrizOposta())
			tabuleiroDaVez = tabuleiroPoloNorte;
    	else if (explorador.getNumeroDoJogador()%2 == 0 && !explorador.isInMatrizOposta())
			tabuleiroDaVez = tabuleiroPoloNorte;
    	else if (explorador.getNumeroDoJogador()%2 == 0 && explorador.isInMatrizOposta())
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
    private boolean verificaPossibilidadeInicial(Explorador explorador, int dado, int longitude, Jogador jogador) {
    	int[] coordenadasAntigas = {explorador.getiInicial(),explorador.getjInicial()};
    	
    	int coordenadaI = explorador.getiInicial() - dado;
    	int coordenadaJ = explorador.getjInicial() + longitude;
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
    		casaLiberada = tabuleiroDaVez.getMatrix()[coordenadaEsperada][longitude].casaLiberada(jogador, explorador.getNumero());
			
			//Casa esta fechada ou cheia
        	if (!casaLiberada) {
        		System.out.println("Casa não está liberada! Escolha outra.\n");
        		return false;
        	}
    	}
    	
    	//Casa esta liberada
    	
    	//Coloca null na posicao que o explorador ocupava no vetor polo inicial
    	tabuleiroDaVez.setPosicaoPoloInicialAsNull(explorador.getNumero(), explorador);
    	
    	//Define o i e j do explorador na matriz e coloca ele na matriz
		explorador.setIMatriz(coordenadaI);
		explorador.setJMatriz(coordenadaJ);
		movimentaTabuleiro(tabuleiroDaVez, explorador, coordenadasAntigas);
    	return true;
    }

    //Verifica se o explorador fazer o movimento (nao sendo a jogada inicial)
    private boolean verificaPossibilidade(Explorador explorador, int dado, int opcaoDeMovimento, Jogador jogador) {
    	int coordenadaInicialI, coordenadaInicialJ;
    	int coordenadaEsperada;
    	int start, end;
    	
    	//Nao pode mover o tabuleiro
    	tabuleiroDaVez.setPodeMover(tabuleiroDaVez.verificaPossibilidadeMovimento(explorador, dado, opcaoDeMovimento));
    	if (!tabuleiroDaVez.getPodeMover()) {
    		System.out.println("Nao foi possivel fazer essa jogada, tente novamente.\n");
    		return false;
    	}
    	
    	
    	//Verifica casa fechada/cheia
    	coordenadaInicialI = explorador.getIMatriz();
    	coordenadaInicialJ = explorador.getJMatriz();
    	tabuleiroAux = tabuleiroDaVez;
    	
    	//Opcao de movimento 1 --> latitude    	
    	if (opcaoDeMovimento == 1) {
    		
    		//Se o explorador vai mudar de matriz
    		if (explorador.isMudandoDeMatriz()) {    			
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
    			if (explorador.isInMatrizOposta()) {
    				
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
    			casaLiberada = tabuleiroAux.getMatrix()[percorre][coordenadaInicialJ].casaLiberada(jogador, explorador.getNumero());
    			
    			//Casa esta fechada ou cheia
	        	if (!casaLiberada) {
	        		System.out.println("Casa não está liberada! Escolha outra.\n");
	        		return false;
	        	}
	        }
    		
    		movimentaLatitude(explorador, dado);
    	}
    	
    	//Opcao de movimento 2 --> longitude
    	else if (opcaoDeMovimento == 2) {
    		coordenadaEsperada = coordenadaInicialJ + dado;
    		for (int percorre = coordenadaInicialJ; percorre < coordenadaEsperada; percorre++) {
    			casaLiberada = tabuleiroDaVez.getMatrix()[coordenadaInicialI][percorre].casaLiberada(jogador, explorador.getNumero());
    			
    			//Casa esta fechada ou cheia
	        	if (!casaLiberada) {
	        		System.out.println("Casa não está liberada! Escolha outra.\n");
	        		return false;
	        	}
        	}

    		movimentaLongitude(explorador, dado);    		
    	}
		
    	return true;
    }
    
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
    
    public void jogo() {
    	int contaVez = 0;
    	final int posicaoUltJogador = jogadoresOrdenados.size()-1;
    	int valorDadoDaVez = 0;
    	cartaEscolhida = null;
    	
    	while (!fimDeJogo) {
    		//Chegou no último jogador
    		if (contaVez == posicaoUltJogador) {
    			jogadorDaVez = jogadoresOrdenados.get(contaVez);
    			contaVez = -1;
    		}
    		
    		//Ainda nao chegou no ultimo jogador
    		else {
    			jogadorDaVez = jogadoresOrdenados.get(contaVez);
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
	    	
	    	contaVez+=1;
    	}
    	
    	declaraVencedor();
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
}