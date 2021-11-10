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
	private int xOffSet = 647;
	private int yOffSet = 650;
	private int xIni = 0, yIni = 0, imgHeight = yOffSet, imgWidth = xOffSet;
	private final int BUTTON_WIDTH = 150;
	private final int BUTTON_HEIGHT = 30;
	private final int BUTTON_SPACE = 60;
	private final int BUTTON_START_X = 725;
	private final int BUTTON_START_Y = 300;
	private JButton btLancaDado = new JButton("Lançar Dados");
	private int dado1 = 0;
	private int dado2 = 0;
	private int mouse_x = 0;
	private int mouse_y = 0;

	private int gameMode = 0;
	
	public TabuleiroView() {
		try {
			imgTabuleiro = ImageIO.read(new File("src/view/assets/Latitude90-Tabuleiro.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		btLancaDado.setBounds(BUTTON_START_X, BUTTON_START_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		loadImagesPecas();
		loadImagesDados();
		lancaDados();
		
		
		addMouseListener(this);
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

	private void loadImagesPecas() {
		
		for(int i = 0; i <= 3; i++) {
			try {
				 Image img = ImageIO.read(new File("src/view/assets/jogador" + (i + 1) + ".png"));
				 img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
				 imgPecas.put(CORES[i], img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void loadImagesDados() {
		for(int i = 0; i <= 5; i++) {
			try {
				 Image img = ImageIO.read(new File("src/view/assets/dado" + (i + 1) + ".png"));
				 img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
				 imgDados.put(Integer.valueOf(DADINHOS[i]), img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void lancaDados() {
		btLancaDado.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				dado1 = ControllerFacade.getModel().getValorDado();
				dado2 = ControllerFacade.getModel().getValorDado();
				tabuleiro.repaint();
			}
			
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		tabuleiro.setLayout(null);
		Graphics2D g2d = (Graphics2D) g;	
		Rectangle2D rt;
		
        add(btLancaDado);
		
		g2d.drawImage(imgTabuleiro, 0, 0, imgWidth, imgHeight,this);
		
		for (int i = 0; i < (2*gameMode); i ++)
			for (int j = 0; j < 6; j++) {
				if (i % 2 == 0)
					g2d.drawImage(imgPecas.get(CORES[i]), 153 + 10*i, 295 + 7*j, 30, 30,this); //polo sul: x = 178, y = 330
				else
					g2d.drawImage(imgPecas.get(CORES[i]), 452 + 10*(i-1), 295 + 7*j, 30, 30,this); //polo norte: x = 477, y = 330
			}
        
        g2d.drawImage(imgDados.get(Integer.valueOf(dado1)), 700, 200, 100, 100,this);
		g2d.drawImage(imgDados.get(Integer.valueOf(dado2)), 800, 200, 100, 100,this);
        
    }

	public void mousePressed(MouseEvent e) {
		int x=e.getX(),y=e.getY();
		System.out.println("x:"+x+",y:"+y); //polo sul: x = 178, y = 330 e polo norte: x = 477, y = 330
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

}
