package view;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.ControllerFacade;
import util.Observavel;
import util.Operacoes;
import util.Observador;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.geom.*;
import java.util.List;


class TabuleiroView extends JPanel implements MouseListener, Observador, Observavel {
	private final int linhas = 6, colunas = 24;
	private String[][] coordenadasCasas = new String[linhas][colunas];
	private Image imgTabuleiro = null;
	private Image pecaSelecionada = null;
	private TabuleiroView tabuleiro = this;
	private Map<String, Image> imgPecas = new HashMap<String, Image>();
	private Map<Integer, Image> imgDados = new HashMap<Integer, Image>();
	private Map<Integer, Image> imgCartas = new HashMap<Integer, Image>();
	private Image imgSelecionado = null;
	private static final String[] CORES = {"Preto", "Azul", "Verde", "Laranja", "Vermelho", "Branco"};
	private static final int[] DADINHOS = {1, 2, 3, 4, 5, 6};
	private static final int[] CARTINHAS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};
	private int xOffSet = 677;
	private int yOffSet = 680;
	private int xIni = 0, yIni = 0, imgHeight = yOffSet, imgWidth = xOffSet;
	private final int BUTTON_WIDTH = 150;
	private final int BUTTON_HEIGHT = 30;
	private final int BUTTON_SPACE = 60;
	private final int BUTTON_START_X = 775;
	private final int BUTTON_START_Y = 330;
	private final int TEXT_WIDTH = 80;
	private JButton btLancaDado = new JButton("Lançar Dados");
	private JLabel dado1Manualmente = new JLabel("Dado 1");
	private JLabel dado2Manualmente = new JLabel("Dado 2");
	private JButton dado1valor1 = new JButton("1");
	private JButton dado1valor2 = new JButton("2");
	private JButton dado1valor3 = new JButton("3");
	private JButton dado1valor4 = new JButton("4");
	private JButton dado1valor5 = new JButton("5");
	private JButton dado1valor6 = new JButton("6");
	private JButton dado2valor1 = new JButton("1");
	private JButton dado2valor2 = new JButton("2");
	private JButton dado2valor3 = new JButton("3");
	private JButton dado2valor4 = new JButton("4");
	private JButton dado2valor5 = new JButton("5");
	private JButton dado2valor6 = new JButton("6");
	private JButton salvarJogo = new JButton("Salvar Jogo");
	private int dado1 = 0;
	private int dado2 = 0;
	private String dadoCol = null;
	private Color corDado = null;
	private int mouseX = 0;
	private int mouseY = 0;
	private int expCoordX = 0;
	private int expCoordY = 0;
	private int gameMode = 0;
	private boolean initBoard = true;
	private boolean canStart = false;
	private boolean exploradorSelected = false;
	private boolean exploradorProntoParaMover = false;
	private boolean inicio = true;
	private boolean podeLancarDado = true;
	private HashMap<String, ArrayList<String>> exploradoresPorCoordenada = new HashMap<String, ArrayList<String>>();
	private final static int LINHAS = 6, COLUNAS = 12;
	private String[][] matrizCasas = new String[LINHAS][COLUNAS];
	private final static int EXP_HEIGHT = 30;
	private final static int EXP_WIDTH = 15;
	private String newCoordenada = null;
	private int numExploradorSelecionado = 6;
	private ArrayList<String> coresOrdenadas = new ArrayList<String>(4);
	private int indiceCorDaVez = 0;
	private boolean posValida = false;
	private boolean canGetCarta = true;
	private boolean showCarta = false;
	private ArrayList<Integer> deckCartaDinamica = new ArrayList<Integer>(17);
	private int cartaDaVez;
	private JLabel cartaManualmente = new JLabel("Carta");
	private JButton carta1 = new JButton("1");
	private JButton carta3 = new JButton("3");
	protected List<Observador> observadores = new ArrayList<>();
	private boolean hasChanged;
	private int[] numeroJogadores = null;

	public TabuleiroView() {
		try {
			//imgTabuleiro = ImageIO.read(new File("D:\\Eclipse Workspaces\\INF1636_Jogo_Novo\\INF1636-POO-2021.2-JOGO\\assets\\Latitude90-Tabuleiro2.jpg")); //Bella
			imgTabuleiro = ImageIO.read(new File("C:\\Users\\User\\Documents\\2021.2\\poo\\INF1636-POO-2021.2-JOGO\\assets\\Latitude90-Tabuleiro2.jpg")); //Rachel
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			//pecaSelecionada = ImageIO.read(new File("D:\\Eclipse Workspaces\\INF1636_Jogo_Novo\\INF1636-POO-2021.2-JOGO\\assets\\selecionado.png")); //Bella
			pecaSelecionada = ImageIO.read(new File("C:\\Users\\User\\Documents\\2021.2\\poo\\INF1636-POO-2021.2-JOGO\\assets\\selecionado.png")); //Rachel
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		initTabView();
		setColors();
		loadImagesPecas();
		loadImagesDados();
		loadImagesCartas();
		lancaDados();
		initCards();
		montaMatrizCoordenadacasas();
		
		addMouseListener(this);
	}
	
	public void initTabView() {
		btLancaDado.setBounds(BUTTON_START_X, BUTTON_START_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		dado1Manualmente.setBounds(730, 370, TEXT_WIDTH, TEXT_WIDTH/2);
		dado2Manualmente.setBounds(730, 410, TEXT_WIDTH, TEXT_WIDTH/2);
		dado1valor1.setBounds(790, 375, 50, BUTTON_HEIGHT);
		dado1valor2.setBounds(845, 375, 50, BUTTON_HEIGHT);
		dado1valor3.setBounds(900, 375, 50, BUTTON_HEIGHT);
		dado1valor4.setBounds(955, 375, 50, BUTTON_HEIGHT);
		dado1valor5.setBounds(1010, 375, 50, BUTTON_HEIGHT);
		dado1valor6.setBounds(1065, 375, 50, BUTTON_HEIGHT);
		dado2valor1.setBounds(790, 415, 50, BUTTON_HEIGHT);
		dado2valor2.setBounds(845, 415, 50, BUTTON_HEIGHT);
		dado2valor3.setBounds(900, 415, 50, BUTTON_HEIGHT);
		dado2valor4.setBounds(955, 415, 50, BUTTON_HEIGHT);
		dado2valor5.setBounds(1010, 415, 50, BUTTON_HEIGHT);
		dado2valor6.setBounds(1065, 415, 50, BUTTON_HEIGHT);
		cartaManualmente.setBounds(730, 610, TEXT_WIDTH, TEXT_WIDTH/2);
		carta1.setBounds(BUTTON_START_X, 615, BUTTON_WIDTH/2 - 5, BUTTON_HEIGHT);
		carta3.setBounds(BUTTON_START_X + BUTTON_WIDTH/2 + 5, 615, BUTTON_WIDTH/2 - 5, BUTTON_HEIGHT);
		salvarJogo.setBounds(BUTTON_START_X, 655, BUTTON_WIDTH, BUTTON_HEIGHT);
	}
	
	public void setColors() {
		System.setProperty("Azul", "0x326cbe");
		System.setProperty("Verde", "0x72bc49");
		System.setProperty("Laranja", "0xdca639");
		System.setProperty("Preto", "0x000000");
		System.setProperty("Branco", "0xcfcfcf");
		System.setProperty("Vermelho", "0xcfcfcf");
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
		notificarObservadores(Operacoes.INICIALIZA_MODEL, JanelaInicialView.getGameMode(), JanelaInicialView.getNomesJogadores());
	}

	private void loadImagesPecas() {
		
		for(int i = 0; i < 4; i++) {
			try {
				//Image img = ImageIO.read(new File("D:\\Eclipse Workspaces\\INF1636_Jogo_Novo\\INF1636-POO-2021.2-JOGO\\assets\\jogador" + (i + 1) + ".png")); //Bella
				Image img = ImageIO.read(new File("C:\\Users\\User\\Documents\\2021.2\\poo\\INF1636-POO-2021.2-JOGO\\assets\\jogador" + (i + 1) + ".png")); //Rachel
				imgPecas.put(CORES[i], img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void loadImagesDados() {
		for(int i = 0; i < 6; i++) {
			try {
				//Image img = ImageIO.read(new File("D:\\Eclipse Workspaces\\INF1636_Jogo_Novo\\INF1636-POO-2021.2-JOGO\\assets\\dado" + (i + 1) + ".png")); //Bella
				Image img = ImageIO.read(new File("C:\\Users\\User\\Documents\\2021.2\\poo\\INF1636-POO-2021.2-JOGO\\assets\\dado" + (i + 1) + ".png")); //Rachel
				imgDados.put(Integer.valueOf(DADINHOS[i]), img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void loadImagesCartas() {
		for(int i = 0; i < 18; i++) {
			try {
				//Image img = ImageIO.read(new File("D:\\Eclipse Workspaces\\INF1636_Jogo_Novo\\INF1636-POO-2021.2-JOGO\\assets\\C" + (i + 1) + ".png")); //Bella
				Image img = ImageIO.read(new File("C:\\Users\\User\\Documents\\2021.2\\poo\\INF1636-POO-2021.2-JOGO\\assets\\C" + (i + 1) + ".png")); //Rachel
				imgCartas.put(Integer.valueOf(CARTINHAS[i]), img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Inicializa o deck de cartas dinamicas
	private void initCards() {
		for (int i = 0; i < 18; i++)
			deckCartaDinamica.add(i);

		Collections.shuffle(deckCartaDinamica);
	}
	
	private void montaMatrizCoordenadacasas() {
		coordenadasCasas[0][0] = "33,51,313,331"; 
		coordenadasCasas[0][1] = "67,85,233,251";
		coordenadasCasas[0][2] = "141,159,185,203";
		coordenadasCasas[0][3] = "232,250,185,203";
		coordenadasCasas[0][4] = "312,330,233,251";
		coordenadasCasas[0][5] = "344,362,313,331";
		coordenadasCasas[0][6] = "344,362,393,411";
		coordenadasCasas[0][7] = "312,330,487,505";
		coordenadasCasas[0][8] = "232,250,529,547";
		coordenadasCasas[0][9] = "141,159,529,547";
		coordenadasCasas[0][10] = "67,85,487,505";
		coordenadasCasas[0][11] = "33,51,393,411";
		
		coordenadasCasas[0][12] = "365,383,313,331";
		coordenadasCasas[0][13] = "399,417,233,251";
		coordenadasCasas[0][14] = "473,491,185,203";
		coordenadasCasas[0][15] = "564,582,185,203";
		coordenadasCasas[0][16] = "644,662,233,251";
		coordenadasCasas[0][17] = "676,694,313,331";
		coordenadasCasas[0][18] = "676,694,393,411";
		coordenadasCasas[0][19] = "644,662,487,505";
		coordenadasCasas[0][20] = "564,582,529,547";
		coordenadasCasas[0][21] = "473,491,529,547";
		coordenadasCasas[0][22] = "399,417,487,505";
		coordenadasCasas[0][23] = "365,383,393,411";
		
		coordenadasCasas[1][0] = "57,75,320,338";
		coordenadasCasas[1][1] = "84,102,245,263";
		coordenadasCasas[1][2] = "146,164,211,229";
		coordenadasCasas[1][3] = "227,245,211,229";
		coordenadasCasas[1][4] = "293,311,245,263";
		coordenadasCasas[1][5] = "321,339,320,338";
		coordenadasCasas[1][6] = "321,339,391,409";
		coordenadasCasas[1][7] = "293,311,469,487";
		coordenadasCasas[1][8] = "227,245,507,525";
		coordenadasCasas[1][9] = "146,164,507,525";
		coordenadasCasas[1][10] = "84,102,469,487";
		coordenadasCasas[1][11] = "57,75,391,409";
		
		coordenadasCasas[1][12] = "389,407,320,338";
		coordenadasCasas[1][13] = "416,434,245,263";
		coordenadasCasas[1][14] = "478,496,211,229";
		coordenadasCasas[1][15] = "559,577,211,229";
		coordenadasCasas[1][16] = "625,643,245,263";
		coordenadasCasas[1][17] = "653,671,320,338";
		coordenadasCasas[1][18] = "653,671,391,409";
		coordenadasCasas[1][19] = "625,643,469,487";
		coordenadasCasas[1][20] = "559,577,507,525";
		coordenadasCasas[1][21] = "478,496,507,525";
		coordenadasCasas[1][22] = "416,434,469,487";
		coordenadasCasas[1][23] = "389,407,391,409";
		
		coordenadasCasas[2][0] = "80,98,328,346";
		coordenadasCasas[2][1] = "101,119,266,284";
		coordenadasCasas[2][2] = "154,172,236,254";
		coordenadasCasas[2][3] = "223,241,236,254";
		coordenadasCasas[2][4] = "278,296,266,284";
		coordenadasCasas[2][5] = "299,317,328,346";
		coordenadasCasas[2][6] = "299,317,385,403";
		coordenadasCasas[2][7] = "278,296,449,467";
		coordenadasCasas[2][8] = "223,241,479,497";
		coordenadasCasas[2][9] = "154,172,479,497";
		coordenadasCasas[2][10] = "101,119,449,467";
		coordenadasCasas[2][11] = "80,98,385,403";
		
		coordenadasCasas[2][12] = "412,430,328,346";
		coordenadasCasas[2][13] = "433,451,266,284";
		coordenadasCasas[2][14] = "486,504,236,254";
		coordenadasCasas[2][15] = "555,573,236,254";
		coordenadasCasas[2][16] = "610,628,266,284";
		coordenadasCasas[2][17] = "631,649,328,346";
		coordenadasCasas[2][18] = "631,649,385,403";
		coordenadasCasas[2][19] = "610,628,449,467";
		coordenadasCasas[2][20] = "555,573,479,497";
		coordenadasCasas[2][21] = "486,504,479,497";
		coordenadasCasas[2][22] = "433,451,449,467";
		coordenadasCasas[2][23] = "412,430,385,403";
		
		coordenadasCasas[3][0] = "102,120,334,352";
		coordenadasCasas[3][1] = "120,138,284,302";
		coordenadasCasas[3][2] = "161,179,261,279";
		coordenadasCasas[3][3] = "216,234,261,279";
		coordenadasCasas[3][4] = "260,278,284,302";
		coordenadasCasas[3][5] = "277,295,334,352";
		coordenadasCasas[3][6] = "277,295,383,401";
		coordenadasCasas[3][7] = "260,278,431,449";
		coordenadasCasas[3][8] = "216,234,456,474";
		coordenadasCasas[3][9] = "161,179,456,474";
		coordenadasCasas[3][10] = "120,138,431,449";
		coordenadasCasas[3][11] = "102,120,383,401";
		
		coordenadasCasas[3][12] = "434,452,334,352";
		coordenadasCasas[3][13] = "452,470,284,302";
		coordenadasCasas[3][14] = "493,511,261,279";
		coordenadasCasas[3][15] = "548,566,261,279";
		coordenadasCasas[3][16] = "592,610,284,302";
		coordenadasCasas[3][17] = "609,627,334,352";
		coordenadasCasas[3][18] = "609,627,383,401";
		coordenadasCasas[3][19] = "592,610,431,449";
		coordenadasCasas[3][20] = "548,566,456,474";
		coordenadasCasas[3][21] = "493,511,456,474";
		coordenadasCasas[3][22] = "452,470,431,449";
		coordenadasCasas[3][23] = "434,452,383,401";
		
		coordenadasCasas[4][0] = "125,143,342,360";
		coordenadasCasas[4][1] = "137,155,303,321";
		coordenadasCasas[4][2] = "170,188,286,304";
		coordenadasCasas[4][3] = "208,226,286,304";
		coordenadasCasas[4][4] = "241,259,303,321";
		coordenadasCasas[4][5] = "254,272,342,360";
		coordenadasCasas[4][6] = "254,272,377,395";
		coordenadasCasas[4][7] = "241,259,412,430";
		coordenadasCasas[4][8] = "208,226,429,447";
		coordenadasCasas[4][9] = "170,188,429,447";
		coordenadasCasas[4][10] = "137,155,412,430";
		coordenadasCasas[4][11] = "125,143,377,395";
		
		coordenadasCasas[4][12] = "457,475,342,360";
		coordenadasCasas[4][13] = "469,487,303,321";
		coordenadasCasas[4][14] = "502,520,286,304";
		coordenadasCasas[4][15] = "540,558,286,304";
		coordenadasCasas[4][16] = "573,591,303,321";
		coordenadasCasas[4][17] = "586,604,342,360";
		coordenadasCasas[4][18] = "586,604,377,395";
		coordenadasCasas[4][19] = "573,591,412,430";
		coordenadasCasas[4][20] = "540,558,429,447";
		coordenadasCasas[4][21] = "502,520,429,447";
		coordenadasCasas[4][22] = "469,487,412,430";
		coordenadasCasas[4][23] = "457,475,377,395";
		
		coordenadasCasas[5][0] = "148,166,350,368";
		coordenadasCasas[5][1] = "155,173,323,341";
		coordenadasCasas[5][2] = "177,195,310,328";
		coordenadasCasas[5][3] = "199,217,310,328";
		coordenadasCasas[5][4] = "222,240,323,341";
		coordenadasCasas[5][5] = "231,249,350,368";
		coordenadasCasas[5][6] = "231,249,371,389";
		coordenadasCasas[5][7] = "222,240,392,410";
		coordenadasCasas[5][8] = "199,217,405,423";
		coordenadasCasas[5][9] = "177,195,405,423";
		coordenadasCasas[5][10] = "155,173,392,410";
		coordenadasCasas[5][11] = "148,166,371,389";
		
		coordenadasCasas[5][12] = "480,498,350,368";
		coordenadasCasas[5][13] = "487,505,323,341";
		coordenadasCasas[5][14] = "509,527,310,328";
		coordenadasCasas[5][15] = "531,549,310,328";
		coordenadasCasas[5][16] = "554,572,323,341";
		coordenadasCasas[5][17] = "563,581,350,368";
		coordenadasCasas[5][18] = "563,581,371,389";
		coordenadasCasas[5][19] = "554,572,392,410";
		coordenadasCasas[5][20] = "531,549,405,423";
		coordenadasCasas[5][21] = "509,527,405,423";
		coordenadasCasas[5][22] = "487,505,392,410";
		coordenadasCasas[5][23] = "480,498,371,389";
	}
	
	private void lancaDados() {
		btLancaDado.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if (podeLancarDado) {
					dado1 = ControllerFacade.getModelFacade().getValorDadoLancado(); //TODO: trocar pra observer
					dado2 = ControllerFacade.getModelFacade().getValorDadoLancado();
					dadoCol = ControllerFacade.getModelFacade().getValorDadoColorido(dado1, dado2);
					if (!inicio) {
						indiceCorDaVez += 1;
						if (indiceCorDaVez == (2*gameMode))
							indiceCorDaVez = 0;
					}
					else {
						notificarObservadores(1);
						inicio = false;
					}
					atualiza();
				}
				podeLancarDado = false;
			}
			
		});
		
		dado1valor1.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				dado1 = 1;
				dadoCol = ControllerFacade.getModelFacade().getValorDadoColorido(dado1, dado2);
				atualiza();
			}	
		});
		
		dado1valor2.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				dado1 = 2;
				dadoCol = ControllerFacade.getModelFacade().getValorDadoColorido(dado1, dado2);
				atualiza();
			}
		});
		
		dado1valor3.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				dado1 = 3;
				dadoCol = ControllerFacade.getModelFacade().getValorDadoColorido(dado1, dado2);
				atualiza();
			}	
		});
		
		dado1valor4.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				dado1 = 4;
				dadoCol = ControllerFacade.getModelFacade().getValorDadoColorido(dado1, dado2);
				atualiza();
			}
		});
		
		dado1valor5.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				dado1 = 5;
				dadoCol = ControllerFacade.getModelFacade().getValorDadoColorido(dado1, dado2);
				atualiza();
			}	
		});
		
		dado1valor6.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				dado1 = 6;
				dadoCol = ControllerFacade.getModelFacade().getValorDadoColorido(dado1, dado2);
				atualiza();
			}
		});
		
		dado2valor1.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				dado2 = 1;
				dadoCol = ControllerFacade.getModelFacade().getValorDadoColorido(dado1, dado2);
				atualiza();
			}	
		});
		
		dado2valor2.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				dado2 = 2;
				dadoCol = ControllerFacade.getModelFacade().getValorDadoColorido(dado1, dado2);
				atualiza();
			}
		});
		
		dado2valor3.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				dado2 = 3;
				dadoCol = ControllerFacade.getModelFacade().getValorDadoColorido(dado1, dado2);
				atualiza();
			}	
		});
		
		dado2valor4.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				dado2 = 4;
				dadoCol = ControllerFacade.getModelFacade().getValorDadoColorido(dado1, dado2);
				atualiza();
			}
		});
		
		dado2valor5.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				dado2 = 5;
				dadoCol = ControllerFacade.getModelFacade().getValorDadoColorido(dado1, dado2);
				atualiza();
			}	
		});
		
		dado2valor6.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				dado2 = 6;
				dadoCol = ControllerFacade.getModelFacade().getValorDadoColorido(dado1, dado2);
				atualiza();
			}
		});
		
		carta1.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				mostraCarta(0);
			}
		});
		
		carta3.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				mostraCarta(2);
			}
		});
		
		salvarJogo.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if (podeLancarDado) {
					notificarObservadores(Operacoes.SALVAR_JOGO);
				}
			}
			
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		tabuleiro.setLayout(null);
		Graphics2D g2d = (Graphics2D) g;
		Rectangle2D rt;

		
		//Botao de lancamento dos dados
        add(btLancaDado);
        add(dado1Manualmente);
        add(dado2Manualmente);
        add(dado1valor1);
        add(dado1valor2);
        add(dado1valor3);
        add(dado1valor4);
        add(dado1valor5);
        add(dado1valor6);
        add(dado2valor1);
        add(dado2valor2);
        add(dado2valor3);
        add(dado2valor4);
        add(dado2valor5);
        add(dado2valor6);
        add(cartaManualmente);
        add(carta1);
        add(carta3);
        add(salvarJogo);
		
        //Imagem do tabuleiro
		g2d.drawImage(imgTabuleiro, 0, 0, this);
		
		//Desenho dos exploradores
		if (initBoard) {
			for (int i = 0; i < (2*gameMode); i ++) {
				ArrayList<String> coordenadasIniciais = new ArrayList<String>();
				for (int j = 0; j < 6; j++) {
					if (i % 2 == 0) {
						int xPoloSul = 186 + 10*i;
						int yPoloSul = 347 + 7*j;
						g2d.drawImage((imgPecas.get(CORES[i]).getScaledInstance(15, 25, Image.SCALE_SMOOTH)), xPoloSul - EXP_WIDTH/2, yPoloSul - EXP_HEIGHT/2, EXP_WIDTH, EXP_HEIGHT, this); //polo sul: x = 189, y = 358
						coordenadasIniciais.add(String.valueOf(xPoloSul)+','+String.valueOf(yPoloSul));
					}
					else {
						int xPoloNorte = 519 + 10*(i-1);
						int yPoloNorte = 347 + 7*j;
						g2d.drawImage((imgPecas.get(CORES[i]).getScaledInstance(15, 25, Image.SCALE_SMOOTH)), xPoloNorte - EXP_WIDTH/2, yPoloNorte - EXP_HEIGHT/2, EXP_WIDTH, EXP_HEIGHT,this); //polo norte: x = 521, y = 358
						coordenadasIniciais.add(String.valueOf(xPoloNorte)+','+String.valueOf(yPoloNorte));
					}
				}
				exploradoresPorCoordenada.put(CORES[i],coordenadasIniciais);
			}
			initBoard=false;
		}
		
		else
			for (String str : exploradoresPorCoordenada.keySet()) {
				for (int i=0; i<6; i++) {
					if (!str.equals(CORES[numeroJogadores[indiceCorDaVez]-1]) || !(i == numExploradorSelecionado)) {
						int x = montaCoordenada(exploradoresPorCoordenada.get(str).get(i),"x");
						int y = montaCoordenada(exploradoresPorCoordenada.get(str).get(i),"y");
						g2d.drawImage((imgPecas.get(str).getScaledInstance(15, 25, Image.SCALE_SMOOTH)), x - EXP_WIDTH/2, y - EXP_HEIGHT/2, EXP_WIDTH, EXP_HEIGHT,this);
					}
				}
			}
		
		//Atualiza desenho dos exploradores
		if (exploradorProntoParaMover) {
			int x = montaCoordenada(exploradoresPorCoordenada.get(CORES[numeroJogadores[indiceCorDaVez]-1]).get(numExploradorSelecionado),"x");
			int y = montaCoordenada(exploradoresPorCoordenada.get(CORES[numeroJogadores[indiceCorDaVez]-1]).get(numExploradorSelecionado),"y");
			g2d.drawImage((imgPecas.get(CORES[numeroJogadores[indiceCorDaVez]-1]).getScaledInstance(15, 25, Image.SCALE_SMOOTH)), x-(EXP_WIDTH/2), y-(EXP_HEIGHT/2), EXP_WIDTH, EXP_HEIGHT,this);
			exploradorProntoParaMover = false;
			exploradorSelected = false;
			podeLancarDado = true;
			System.out.println("para colocar o explorador: mouseX:"+expCoordX+",MouseY:"+expCoordY);
			posValida = false;
			numExploradorSelecionado = 6;
		    
		}	
		
		else if (exploradorSelected) {
			g2d.drawImage((pecaSelecionada.getScaledInstance(15, 25, Image.SCALE_SMOOTH)), expCoordX - EXP_WIDTH/2, expCoordY - EXP_HEIGHT/2, EXP_WIDTH, EXP_HEIGHT,this);
			exploradorProntoParaMover = true;
		}	
		
		if (showCarta) {
			g2d.drawImage(imgCartas.get(cartaDaVez + 1),BUTTON_START_X, 455, BUTTON_WIDTH, BUTTON_WIDTH,this);
			showCarta = false;
		}
		
		//Cor do jogador da vez
		Rectangle2D corJogador = new Rectangle2D.Double(730, 180, 240, 140);
		g2d.setColor(Color.getColor(CORES[numeroJogadores[indiceCorDaVez]-1]));
		g2d.fill(corJogador);
		
		//Imagens dos dados
        g2d.drawImage(imgDados.get(Integer.valueOf(dado1)), 750, 200, 100, 100,this);
		g2d.drawImage(imgDados.get(Integer.valueOf(dado2)), 850, 200, 100, 100,this);
		
		//TODO: exibir mensagem pros casos do dado colorido
		if (dadoCol != null) {
			Rectangle2D dadoColorido = new Rectangle2D.Double(990, 200, 100, 100);
			g2d.setColor(Color.getColor(dadoCol));
			g2d.fill(dadoColorido);
		}		
		
    }
	
	private int montaCoordenada (String str, String tipo) {
		String[] coordenadas = str.split(",");
		if (tipo.equals("x")) {
			return (Integer.valueOf(coordenadas[0])).intValue();
		}
		else if (tipo.equals("y")) {
			return (Integer.valueOf(coordenadas[1])).intValue();
		}
		else
			return 0;
	}

	private int montaIJ (String str, String tipo) {
		String[] coordenadas = str.split(",");
		if (tipo.equals("xIni")) {
			return (Integer.valueOf(coordenadas[0])).intValue();
		}
		else if (tipo.equals("xFim")) {
			return (Integer.valueOf(coordenadas[1])).intValue();
		}
		else if (tipo.equals("yIni")) {
			return (Integer.valueOf(coordenadas[2])).intValue();
		}
		else if (tipo.equals("yFim")) {
			return (Integer.valueOf(coordenadas[3])).intValue();
		}
		else
			return 0;
	}
	
	private void mostraCarta(int carta) {
		if (carta == -1) {
	  		cartaDaVez = this.deckCartaDinamica.get(0);
	  		this.deckCartaDinamica.remove(0);
	  		this.deckCartaDinamica.add(cartaDaVez);
		}
		else
			cartaDaVez = carta;
  		System.out.println("carta da vez: " +  cartaDaVez);
  		if (cartaDaVez == 0)
  			System.out.println("carta 1");
  		else if (cartaDaVez == 2)
  			System.out.println("carta 3");
  		showCarta = true;
		atualiza();
	}
	
	private void atualiza() {
		tabuleiro.repaint();
	}

	public void mousePressed(MouseEvent e) {
		mouseX=e.getX();
		mouseY=e.getY();
		
		if (!podeLancarDado) {
			
			//Printa coordenadas dos exploradores
			if (!exploradorSelected && !exploradorProntoParaMover)
				for (String str : exploradoresPorCoordenada.keySet()) {
					if (str == CORES[numeroJogadores[indiceCorDaVez]-1])
						for (int i=0; i<6; i++) {
						      System.out.println("key: " + str + " value: " + exploradoresPorCoordenada.get(str).get(i));
						      expCoordX = montaCoordenada(exploradoresPorCoordenada.get(str).get(i),"x");
						      expCoordY = montaCoordenada(exploradoresPorCoordenada.get(str).get(i),"y");
		
					    	  System.out.println("ExpX:"+expCoordX+"ExpY:"+expCoordY+"\n");
						      if (((mouseX>=expCoordX-EXP_WIDTH) && (mouseX<=expCoordX+EXP_WIDTH)) && ((mouseY>=expCoordY-EXP_HEIGHT) && (mouseY<=expCoordY+EXP_HEIGHT))) {
						    	  exploradorSelected = true;
						    	  numExploradorSelecionado = i;
						    	  System.out.println("Explorador selecionado.\n");
						    	  break;
						      }
						}
				}
	
			if (exploradorProntoParaMover) {
			    for (int i = 0; i < linhas; i++) 
			    	for (int j = 0; j < colunas; j++) {
			    		int xIni = montaIJ(coordenadasCasas[i][j], "xIni");
			    		int xFim = montaIJ(coordenadasCasas[i][j], "xFim");
			    		int yIni = montaIJ(coordenadasCasas[i][j], "yIni");
			    		int yFim = montaIJ(coordenadasCasas[i][j], "yFim");
			    		if ((mouseX >= xIni && mouseX <= xFim) && (mouseY >= yIni && mouseY <= yFim)) {
			    			posValida = true;
			    			
			    			System.out.println("i:"+i+ ",j:"+j);
			    			break;
			    		}
			    	}
				newCoordenada=mouseX+","+mouseY;
				exploradoresPorCoordenada.get(CORES[numeroJogadores[indiceCorDaVez]-1]).set(numExploradorSelecionado, newCoordenada);
			}
			
			if (exploradorSelected || exploradorProntoParaMover) {
				if (exploradorProntoParaMover && !posValida) {
	    			System.out.println("posicao invalida");
				}
				else {
	
	    			System.out.println("exploradorProntoParaMover: " + exploradorProntoParaMover + "posValida: " + posValida);
					atualiza();
				}
			}
			
			if (canGetCarta)
				if ((mouseX >= 315 && mouseX <= 416) && (mouseY >= 597 && mouseY <= 684))
					mostraCarta(-1);
					
			
			System.out.println("mouseX:"+mouseX+",MouseY:"+mouseY); //polo sul: x = 189, y = 358 e polo norte: x = 521, y = 358
		}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}

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

	public static void s(Object o) {
		System.out.println(o);
	}

	@Override
	public void update(Observavel o, Object... args) {
		s("De " + o.getClass().getSimpleName() + " Para " + this.getClass().getSimpleName());

		if (args == null) {
			return;
		}

		final int operacao = (int) args[0];

		if (operacao == Operacoes.ORDENA_JOGADORES) {
			s("Operacao Ordena Jogadores. Msg: " + "vai comecar");
			numeroJogadores = new int[gameMode*2];

			try {
				System.arraycopy(args[1], 0, numeroJogadores, 0, gameMode*2);
			}
			catch (ClassCastException ignored) {}

			s("Operacao Ordena Jogadores. Msg: " + numeroJogadores[0]);
		}
	}
}
