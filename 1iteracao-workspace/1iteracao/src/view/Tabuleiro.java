package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.stream.Collectors;


public class Tabuleiro extends JPanel{
	private Image imgTabuleiro = null;
	private Tabuleiro tabuleiro = this;
	private Map<String, Image> imgPecas = new HashMap<String, Image>();
	private Map<Integer, Image> imgDados = new HashMap<Integer, Image>();
	private static final String[] CORES = {"Verde", "Laranja", "Azul", "Preto", "Vermelho", "Branco"};
	private static final int[] DADINHOS = {1, 2, 3, 4, 5, 6};
	private int xOffSet = 647;
	private int yOffSet = 650;
	private int xIni = 0, yIni = 0, imgHeight = yOffSet, imgWidth = xOffSet;
	
	public Tabuleiro() {
		try {
			imgTabuleiro = ImageIO.read(new File("1iteracao-workspace/1iteracao/src/view/assets/Latitude90-Tabuleiro.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadImagesPecas();
		loadImagesDados();
	}
	
	private void loadImagesPecas() {
		
		for(int i = 0; i <= 3; i++) {
			try {
				 Image img = ImageIO.read(new File("1iteracao-workspace/1iteracao/src/view/assets/jogador" + (i + 1) + ".png"));
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
				 Image img = ImageIO.read(new File("1iteracao-workspace/1iteracao/src/view/assets/dado" + (i + 1) + ".png"));
				 img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
				 imgDados.put(Integer.valueOf(DADINHOS[i]), img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		tabuleiro.setLayout(null);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(imgTabuleiro, 0, 0, imgWidth, imgHeight,this);
		
		for (int i = 0; i < 4; i ++)
			for (int j = 0; j < 6; j++)
				g2d.drawImage(imgPecas.get(CORES[i]), 700 + 30*j, 50 + 30*i, 30, 30,this);
		
		for (int j = 0; j < 2; j++)
			g2d.drawImage(imgDados.get(DADINHOS[j]), 700 + j * 100, 180, 100, 100,this);
	}
}
