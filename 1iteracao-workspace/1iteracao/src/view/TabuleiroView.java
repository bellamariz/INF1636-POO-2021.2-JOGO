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
	private Image imgTabuleiro = null;
	private Image pecaSelecionada = null;
	private TabuleiroView tabuleiro = this;
	private Map<String, Image> imgPecas = new HashMap<String, Image>();
	private Map<Integer, Image> imgDados = new HashMap<Integer, Image>();
	private Image imgSelecionado = null;
	private static final String[] CORES = {"Preto", "Azul", "Verde", "Laranja", "Vermelho", "Branco"};
	private static final int[] DADINHOS = {1, 2, 3, 4, 5, 6};
	private int xOffSet = 677;
	private int yOffSet = 680;
	private int xIni = 0, yIni = 0, imgHeight = yOffSet, imgWidth = xOffSet;
	private final int BUTTON_WIDTH = 150;
	private final int BUTTON_HEIGHT = 30;
	private final int BUTTON_SPACE = 60;
	private final int BUTTON_START_X = 755;
	private final int BUTTON_START_Y = 310;
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
	private boolean exploradorSelected = false;
	private boolean exploradorProntoParaMover = false;
	private HashMap<String, ArrayList<String>> exploradoresPorCoordenada = new HashMap<String, ArrayList<String>>();
	private final static int LINHAS = 6, COLUNAS = 12;
	private String[][] matrizCasas = new String[LINHAS][COLUNAS];
	private final static int EXP_HEIGHT = 30;
	private final static int EXP_WIDTH = 15;
	private String newCoordenada = null;
	private int numExploradorSelecionado;
	private String corDaVez;
	private ArrayList<String> CoresOrdenadas = new ArrayList<String>(4);
	
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
		lancaDados();
		
		
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
		
		for(int i = 0; i <= 3; i++) {
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
		for(int i = 0; i <= 5; i++) {
			try {
				//Image img = ImageIO.read(new File("D:\\Eclipse Workspaces\\INF1636_Jogo_Novo\\INF1636-POO-2021.2-JOGO\\assets\\dado" + (i + 1) + ".png")); //Bella
				Image img = ImageIO.read(new File("C:\\Users\\User\\Documents\\2021.2\\poo\\INF1636-POO-2021.2-JOGO\\assets\\dado" + (i + 1) + ".png")); //Rachel
				imgDados.put(Integer.valueOf(DADINHOS[i]), img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
	
	private void ordenaCores(){
		
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
						int xPoloSul = 179 + 10*i;
						int yPoloSul = 332 + 7*j;
						g2d.drawImage((imgPecas.get(CORES[i]).getScaledInstance(15, 25, Image.SCALE_SMOOTH)), xPoloSul, yPoloSul, EXP_WIDTH, EXP_HEIGHT, this); //polo sul: x = 189, y = 358
						coordenadasIniciais.add(String.valueOf(xPoloSul)+','+String.valueOf(yPoloSul));
					}
					else {
						int xPoloNorte = 511 + 10*(i-1);
						int yPoloNorte = 332 + 7*j;
						g2d.drawImage((imgPecas.get(CORES[i]).getScaledInstance(15, 25, Image.SCALE_SMOOTH)), xPoloNorte, yPoloNorte, EXP_WIDTH, EXP_HEIGHT,this); //polo norte: x = 521, y = 358
						coordenadasIniciais.add(String.valueOf(xPoloNorte)+','+String.valueOf(yPoloNorte));
					}
				}
				exploradoresPorCoordenada.put(CORES[i],coordenadasIniciais);
			}
			initBoard=false;
		}
		
		if (!initBoard)
			for (String str : exploradoresPorCoordenada.keySet()) {
				for (int i=0; i<6; i++) {
					if (!str.equals(CORES[0]) || !(i == numExploradorSelecionado)) {
						int x = montaCoordenada(exploradoresPorCoordenada.get(str).get(i),"x");
						int y = montaCoordenada(exploradoresPorCoordenada.get(str).get(i),"y");
						g2d.drawImage((imgPecas.get(str).getScaledInstance(15, 25, Image.SCALE_SMOOTH)), x, y, EXP_WIDTH, EXP_HEIGHT,this);
					}
				}
			}
		
		if (exploradorSelected) {
			g2d.drawImage((pecaSelecionada.getScaledInstance(15, 25, Image.SCALE_SMOOTH)), expCoordX, expCoordY, EXP_WIDTH, EXP_HEIGHT,this);
			exploradorProntoParaMover = true;
			exploradorSelected = false;
		}
		
		else if (exploradorProntoParaMover) {
			int expX = montaCoordenada(exploradoresPorCoordenada.get(CORES[0]).get(numExploradorSelecionado),"x");
		    int expY = montaCoordenada(exploradoresPorCoordenada.get(CORES[0]).get(numExploradorSelecionado),"y");
			g2d.drawImage((imgPecas.get(CORES[0]).getScaledInstance(15, 25, Image.SCALE_SMOOTH)), expX, expY, EXP_WIDTH, EXP_HEIGHT,this);
			exploradorProntoParaMover = false;
			System.out.println("para colocar o explorador: mouseX:"+expCoordX+",MouseY:"+expCoordY);
		}		
	
		//Printa coordenadas dos exploradores
		for (String str : exploradoresPorCoordenada.keySet()) {
			for (int i=0; i<6; i++) {
			      System.out.println("key: " + str + " value: " + exploradoresPorCoordenada.get(str).get(i));
			}
		}
		
		//Imagens dos dados
        g2d.drawImage(imgDados.get(Integer.valueOf(dado1)), 730, 200, 100, 100,this);
		g2d.drawImage(imgDados.get(Integer.valueOf(dado2)), 830, 200, 100, 100,this);
		
		//TODO: exibir mensagem pros casos do dado colorido
		if (dadoCol != null) {
			Rectangle2D dadoColorido = new Rectangle2D.Double(940, 200, 100, 100);
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
	
	private void atualiza() {
		tabuleiro.repaint();
	}

	public void mousePressed(MouseEvent e) {
		mouseX=e.getX();
		mouseY=e.getY();
		
		//Printa coordenadas dos exploradores
		if (!exploradorSelected && !exploradorProntoParaMover)
			for (String str : exploradoresPorCoordenada.keySet()) {
				if (str == CORES[0])
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
			newCoordenada=mouseX+","+mouseY;
			exploradoresPorCoordenada.get(CORES[0]).set(numExploradorSelecionado, newCoordenada);
		}
		
		if (exploradorSelected || exploradorProntoParaMover)
			atualiza();
		
		System.out.println("mouseX:"+mouseX+",MouseY:"+mouseY); //polo sul: x = 189, y = 358 e polo norte: x = 521, y = 358
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}

}
