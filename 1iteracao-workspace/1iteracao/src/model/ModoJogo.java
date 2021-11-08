package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

class ModoJogo {
	private static ModoJogo modoJogoInstance;
	public static final String[] CORES = {"Preto", "Azul", "Verde", "Laranja", "Vermelho", "Branco"};
	public static int NUM_JOGADORES;
	private int gameMode = 0;
	private String nomes[] = new String[4];
	private ArrayList<CartaDinamica> deckCartaDinamica = new ArrayList<CartaDinamica>(18);
	private Jogador[] jogadores = null;
	private ArrayList<Jogador> jogadoresOrdenados = null;
	private ArrayList<Integer> dadosJogadores = null;

	public static synchronized ModoJogo getInstance(int mode, String[] names) {
		if (modoJogoInstance == null)
			modoJogoInstance = new ModoJogo(mode,names);
		return modoJogoInstance;
	}

	private ModoJogo(int mode, String names[]) {
		NUM_JOGADORES = 2*mode;
		this.gameMode = mode;
		this.nomes = names;
		this.jogadores = new Jogador[NUM_JOGADORES];
		this.jogadoresOrdenados = new ArrayList<Jogador>(NUM_JOGADORES);
		this.dadosJogadores = new ArrayList<Integer>(NUM_JOGADORES);
		initCards();
		initPlayers();	
		initOrdemDeJogada();
		exibeOrdemDeJogada();
	}

	//Getters e Setters
	public int getGameMode() {
		return this.gameMode;
	}

	public Jogador[] getJogadores() {
		return this.jogadores;
	}

	public ArrayList<Jogador> getJogadoresOrdenados(){
		return this.jogadoresOrdenados;
	}
	
	public ArrayList<Integer> getDadosJogadores(){
		return this.dadosJogadores;
	}
	
	public ArrayList<CartaDinamica> getDeckCartas(){
		return this.deckCartaDinamica;
	}
	
	//Inicializa o deck de cartas dinamicas
	private void initCards() {
		CartaDinamica carta;
		String cartaAcao = "Acao: ";
		String licaoAmbiental = "Licao Ambiental: ";

		for (int i = 0; i < 18; i++) {
			carta = new CartaDinamica(cartaAcao+i+1,licaoAmbiental+i+1);
			deckCartaDinamica.add(carta);
		}
		Collections.shuffle(deckCartaDinamica);
	}

	//Inicializa array de jogadores
	private void initPlayers() {
		for(int i=0; i<NUM_JOGADORES; i++) {
			jogadores[i]= new Jogador(nomes[i], ModoJogo.CORES[i],(i+1));
		}
	}

	//Inicializa lista de jogadores ordenados e dados de inicio
	private void initOrdemDeJogada() {
		Set<Integer> uniqueDados = new LinkedHashSet<Integer>(NUM_JOGADORES);
		Dado dado = new Dado();

		while (true) {
			int dadoint = dado.lanca();
			Integer numero = Integer.valueOf(dadoint);
			uniqueDados.add(numero);

			if(uniqueDados.size() == NUM_JOGADORES)
				break;
		}

		//Lista de dados jogados
		for (Integer num:uniqueDados) { dadosJogadores.add(num); }

		//Lista de jogadores ordenados por dado mais alto
		for (int j=0; j<NUM_JOGADORES; j++) {
			jogadores[j].setDado(dadosJogadores.get(j).intValue());
			jogadoresOrdenados.add(jogadores[j]);
		}
		Collections.sort(jogadoresOrdenados);
	}
	
	//Exibe lista de jogadores ordenados
	public void exibeOrdemDeJogada() {
		System.out.println("Ordem dos Jogadores:\n ");
		for (int k=0; k<jogadoresOrdenados.size();k++) {
			System.out.println("Dado Jogado: " + jogadoresOrdenados.get(k).getDado() + " - Vez " + k + ": Jogador " 
					+ jogadoresOrdenados.get(k).getNumeroDoJogador() + " - [ " + jogadoresOrdenados.get(k).getName() + ", Cor: " 
					+ jogadoresOrdenados.get(k).getCor() + "] \n");
		}
	}
	

}
