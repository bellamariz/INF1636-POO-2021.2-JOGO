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
	private TabuleiroView tabuleiro = this;
	private Map<String, Image> imgPecas = new HashMap<String, Image>();
	private Map<Integer, Image> imgDados = new HashMap<Integer, Image>();
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

	private int gameMode = 0;
	
	public TabuleiroView() {
		try {
			imgTabuleiro = ImageIO.read(new File("1iteracao-workspace/1iteracao/src/view/assets/Latitude90-Tabuleiro2.jpg"));
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
				 Image img = ImageIO.read(new File("1iteracao-workspace/1iteracao/src/view/assets/jogador" + (i + 1) + ".png"));
				 imgPecas.put(CORES[i], img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void loadImagesDados() {
		for(int i = 0; i <= 5; i++) {
			try {
				 Image img = ImageIO.read(new File("1iteracao-workspace/1iteracao/src/view/assets/dado" + (i + 1) + ".png"));
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
				tabuleiro.repaint();
			}
			
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		tabuleiro.setLayout(null);
		Graphics2D g2d = (Graphics2D) g;
		
		//Botao de lancamento dos dados
        add(btLancaDado);
		
        //Imagem do tabuleiro
		g2d.drawImage(imgTabuleiro, 0, 0, this);
		
		//Imagens dos exploradores
		for (int i = 0; i < (2*gameMode); i ++)
			for (int j = 0; j < 6; j++) {
				if (i % 2 == 0)
					g2d.drawImage((imgPecas.get(CORES[i]).getScaledInstance(15, 25, Image.SCALE_SMOOTH)), 169 + 10*i, 310 + 7*j, 15, 30, this); //polo sul: x = 178, y = 330
				else
					g2d.drawImage((imgPecas.get(CORES[i]).getScaledInstance(15, 25, Image.SCALE_SMOOTH)), 477 + 10*(i-1), 310 + 7*j, 15, 30,this); //polo norte: x = 477, y = 330
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
		
		//Circulos das casas
		Ellipse2D circ = new Ellipse2D.Double(mouseX,mouseY,20,20);
		g2d.setPaint(Color.BLUE);
		g2d.draw(circ);
    }

	public void mousePressed(MouseEvent e) {
		mouseX=e.getX()-10;
		mouseY=e.getY()-10;
		tabuleiro.repaint();
		System.out.println("x:"+mouseX+",y:"+mouseY); //polo sul: x = 184, y = 340 e polo norte: x = 492, y = 340
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

}
