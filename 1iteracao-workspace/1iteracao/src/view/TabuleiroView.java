package view;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.ControllerFacade;
import model.ModelFacade;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.nio.*;
import java.awt.geom.*;
import java.lang.Math.*;


class TabuleiroView extends JPanel implements MouseListener{
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
	private JButton btLancaDado = new JButton("Lançar Dados");
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
	private HashMap<String, ArrayList<String>> exploradoresPorCoordenada = new HashMap<String, ArrayList<String>>();
	private final static int LINHAS = 6, COLUNAS = 12;
	private String[][] matrizCasas = new String[LINHAS][COLUNAS];
	private final static int EXP_HEIGHT = 30;
	private final static int EXP_WIDTH = 15;
	private String newCoordenada = null;
	private int numExploradorSelecionado = 6;
	private String corDaVez;
	private ArrayList<String> coresOrdenadas = new ArrayList<String>(4);
	private int indiceCorDaVez = 0;
	private boolean posValida = false;
	private int expX;
	private int expY;	
	
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
		
		btLancaDado.setBounds(BUTTON_START_X, BUTTON_START_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		setColors();
		loadImagesPecas();
		loadImagesDados();
		loadImagesCartas();
		lancaDados();
		montaMatrizCoordenadacasas();
		
		
		addMouseListener(this);
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
				//TODO: implementar observable para pegar os objetos do Model
				dado1 = ControllerFacade.getModel().getValorDado();
				dado2 = ControllerFacade.getModel().getValorDado();
				dadoCol = ControllerFacade.getModel().getDadoColorido(dado1, dado2);
				atualiza();
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
		
        //Imagem do tabuleiro
		g2d.drawImage(imgTabuleiro, 0, 0, this);
		
		
		//primeiro circulo do polo sul(mais interno)
		g2d.setColor(Color.pink);
        rt=new Rectangle2D.Double(148, 350, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(155, 323, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(177, 310, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(199, 310, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(222, 323, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(231, 350, 18, 18);
		g2d.fill(rt);
		rt=new Rectangle2D.Double(231, 371, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(222, 392, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(199, 405, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(177, 405, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(155, 392, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(148, 371, 18, 18);
		g2d.fill(rt);
		
		//segundo circulo do polo sul
		g2d.setColor(Color.pink);
        rt=new Rectangle2D.Double(125, 342, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(137, 303, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(170, 286, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(208, 286, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(241, 303, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(254, 342, 18, 18);
		g2d.fill(rt);
		rt=new Rectangle2D.Double(254, 377, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(241, 412, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(208, 429, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(170, 429, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(138, 412, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(125, 377, 18, 18);
		g2d.fill(rt);
		
		//terceiro circulo do polo sul
		g2d.setColor(Color.pink);
        rt=new Rectangle2D.Double(102, 334, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(120, 284, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(161, 261, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(216, 261, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(260, 284, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(277, 334, 18, 18);
		g2d.fill(rt);
		rt=new Rectangle2D.Double(277, 383, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(260, 431, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(216, 456, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(161, 456, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(120, 431, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(102, 383, 18, 18);
		g2d.fill(rt);
		
		//quarto circulo do polo sul
		g2d.setColor(Color.pink);
        rt=new Rectangle2D.Double(80, 328, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(101, 266, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(154, 236, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(223, 236, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(278, 266, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(299, 328, 18, 18);
		g2d.fill(rt);
		rt=new Rectangle2D.Double(299, 385, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(278, 449, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(223, 479, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(154, 479, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(101, 449, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(80, 385, 18, 18);
		g2d.fill(rt);
		
		//quinto circulo do polo sul
		g2d.setColor(Color.pink);
        rt=new Rectangle2D.Double(57, 320, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(84, 245, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(146, 211, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(227, 211, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(293, 245, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(321, 320, 18, 18);
		g2d.fill(rt);
		rt=new Rectangle2D.Double(321, 391, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(293, 469, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(227, 507, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(146, 507, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(84, 469, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(57, 391, 18, 18);
		g2d.fill(rt);
		
		//sexto circulo do polo sul
		g2d.setColor(Color.pink);
        rt=new Rectangle2D.Double(33, 313, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(67, 226, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(141, 185, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(232, 185, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(312, 226, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(344, 313, 18, 18);
		g2d.fill(rt);
		rt=new Rectangle2D.Double(344, 393, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(312, 487, 18, 18); 
		g2d.fill(rt);
        rt=new Rectangle2D.Double(232, 529, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(141, 529, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(67, 487, 18, 18); 
		g2d.fill(rt);
        rt=new Rectangle2D.Double(33, 393, 18, 18);
		g2d.fill(rt);
		
		//primeiro circulo do polo norte(mais interno)
		g2d.setColor(Color.pink);
        rt=new Rectangle2D.Double(148 + 332, 350, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(155 + 332, 323, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(177 + 332, 310, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(199 + 332, 310, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(222 + 332, 323, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(231 + 332, 350, 18, 18);
		g2d.fill(rt);
		rt=new Rectangle2D.Double(231 + 332, 371, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(222 + 332, 392, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(199 + 332, 405, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(177 + 332, 405, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(155 + 332, 392, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(148 + 332, 371, 18, 18);
		g2d.fill(rt);
		
		//segundo circulo do polo norte
		g2d.setColor(Color.pink);
        rt=new Rectangle2D.Double(125 + 332, 342, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(137 + 332, 303, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(170 + 332, 286, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(208 + 332, 286, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(241 + 332, 303, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(254 + 332, 342, 18, 18);
		g2d.fill(rt);
		rt=new Rectangle2D.Double(254 + 332, 377, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(241 + 332, 412, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(208 + 332, 429, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(170 + 332, 429, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(138 + 332, 412, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(125 + 332, 377, 18, 18);
		g2d.fill(rt);
		
		//terceiro circulo do polo norte
		g2d.setColor(Color.pink);
        rt=new Rectangle2D.Double(102 + 332, 334, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(120 + 332, 284, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(161 + 332, 261, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(216 + 332, 261, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(260 + 332, 284, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(277 + 332, 334, 18, 18);
		g2d.fill(rt);
		rt=new Rectangle2D.Double(277 + 332, 383, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(260 + 332, 431, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(216 + 332, 456, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(161 + 332, 456, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(120 + 332, 431, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(102 + 332, 383, 18, 18);
		g2d.fill(rt);
		
		//quarto circulo do polo norte
		g2d.setColor(Color.pink);
        rt=new Rectangle2D.Double(80 + 332, 328, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(101 + 332, 266, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(154 + 332, 236, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(223 + 332, 236, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(278 + 332, 266, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(299 + 332, 328, 18, 18);
		g2d.fill(rt);
		rt=new Rectangle2D.Double(299 + 332, 385, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(278 + 332, 449, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(223 + 332, 479, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(154 + 332, 479, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(101 + 332, 449, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(80 + 332, 385, 18, 18);
		g2d.fill(rt);
		
		//quinto circulo do polo norte
		g2d.setColor(Color.pink);
        rt=new Rectangle2D.Double(57 + 332, 320, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(84 + 332, 245, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(146 + 332, 211, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(227 + 332, 211, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(293 + 332, 245, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(321 + 332, 320, 18, 18);
		g2d.fill(rt);
		rt=new Rectangle2D.Double(321 + 332, 391, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(293 + 332, 469, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(227 + 332, 507, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(146 + 332, 507, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(84 + 332, 469, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(57 + 332, 391, 18, 18);
		g2d.fill(rt);
		
		//sexto circulo do polo norte
		g2d.setColor(Color.pink);
        rt=new Rectangle2D.Double(33 + 332, 313, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(67 + 332, 226, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(141 + 332, 185, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(232 + 332, 185, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(312 + 332, 226, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(344 + 332, 313, 18, 18);
		g2d.fill(rt);
		rt=new Rectangle2D.Double(344 + 332, 393, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(312 + 332, 487, 18, 18); 
		g2d.fill(rt);
        rt=new Rectangle2D.Double(232 + 332, 529, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(141 + 332, 529, 18, 18);
		g2d.fill(rt);
        rt=new Rectangle2D.Double(67 + 332, 487, 18, 18); 
		g2d.fill(rt);
        rt=new Rectangle2D.Double(33 + 332, 393, 18, 18);
		g2d.fill(rt);

		
		//Inicializa imagens dos exploradores
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
					if (!str.equals(CORES[indiceCorDaVez]) || !(i == numExploradorSelecionado)) {
						int x = montaCoordenada(exploradoresPorCoordenada.get(str).get(i),"x");
						int y = montaCoordenada(exploradoresPorCoordenada.get(str).get(i),"y");
						g2d.drawImage((imgPecas.get(str).getScaledInstance(15, 25, Image.SCALE_SMOOTH)), x - EXP_WIDTH/2, y - EXP_HEIGHT/2, EXP_WIDTH, EXP_HEIGHT,this);
					}
				}
			}
		
		if (exploradorProntoParaMover) {
			int x = montaCoordenada(exploradoresPorCoordenada.get(CORES[indiceCorDaVez]).get(numExploradorSelecionado),"x");
			int y = montaCoordenada(exploradoresPorCoordenada.get(CORES[indiceCorDaVez]).get(numExploradorSelecionado),"y");
			g2d.drawImage((imgPecas.get(CORES[indiceCorDaVez]).getScaledInstance(15, 25, Image.SCALE_SMOOTH)), x-(EXP_WIDTH/2), y-(EXP_HEIGHT/2), EXP_WIDTH, EXP_HEIGHT,this);
			exploradorProntoParaMover = false;
			exploradorSelected = false;
			indiceCorDaVez += 1;
			if (indiceCorDaVez == (2*gameMode))
				indiceCorDaVez = 0;
			System.out.println("para colocar o explorador: mouseX:"+expCoordX+",MouseY:"+expCoordY);
			posValida = false;
			numExploradorSelecionado = 6;
		    
		}	
		
		else if (exploradorSelected) {
			g2d.drawImage((pecaSelecionada.getScaledInstance(15, 25, Image.SCALE_SMOOTH)), expCoordX - EXP_WIDTH/2, expCoordY - EXP_HEIGHT/2, EXP_WIDTH, EXP_HEIGHT,this);
			exploradorProntoParaMover = true;
		}	
	
//		//Printa coordenadas dos exploradores
//		for (String str : exploradoresPorCoordenada.keySet()) {
//			for (int i=0; i<6; i++) {
//			      System.out.println("key: " + str + " value: " + exploradoresPorCoordenada.get(str).get(i));
//			}
//		}
		
		Rectangle2D corJogador = new Rectangle2D.Double(730, 180, 240, 140);
		g2d.setColor(Color.getColor(CORES[indiceCorDaVez]));
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
		
		
//		//Elipse teste mouse
//		Ellipse2D circ = new Ellipse2D.Double(mouseX - 10, mouseY - 10,20,20);
//		g2d.setPaint(Color.BLUE);
//		g2d.draw(circ);
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
	
	private void atualiza() {
		tabuleiro.repaint();
	}

	public void mousePressed(MouseEvent e) {
		mouseX=e.getX();
		mouseY=e.getY();
		
		//Printa coordenadas dos exploradores
		if (!exploradorSelected && !exploradorProntoParaMover)
			for (String str : exploradoresPorCoordenada.keySet()) {
				if (str == CORES[indiceCorDaVez])
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
			exploradoresPorCoordenada.get(CORES[indiceCorDaVez]).set(numExploradorSelecionado, newCoordenada);
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
		
		System.out.println("mouseX:"+mouseX+",MouseY:"+mouseY); //polo sul: x = 189, y = 358 e polo norte: x = 521, y = 358
		
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}

}
