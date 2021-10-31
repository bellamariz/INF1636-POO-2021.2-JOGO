package view;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class JanelaInicial extends JFrame{
	
    private JanelaInicial janelaInicial = this;
	private static final int TWO_PLAYERS = 2;
	private static final int FOUR_PLAYERS = 4;
	private final int DEFAULT_WIDTH = 1200;
	private final int DEFAULT_HEIGHT = 700;
	private final int BUTTON_WIDTH = 150;
	private final int BUTTON_HEIGHT = 30;
	private final int BUTTON_SPACE = 60;
	private final int BUTTON_START_X = 525;
	private final int BUTTON_START_Y = 280;
	private final int TEXT_WIDTH = 80;
	private final int TEXT_START_X = 520;
	private final int TEXT_START_Y = 150;
	
	private int qtdeJogadores = 0;
	private int gameMode = 0;
	private String nomesJogadores[] = null;
	private static boolean GAME_MODE_STATUS = false;
	private boolean canStartGame = false;
	private boolean gameModeSelected = true;
	
    private JPanel menuInicial = new JPanel();    
    private JPanel menuJogadores = new JPanel();
    private JPanel tabuleiro = null;

    public JanelaInicial(Tabuleiro tabuleiro) {
    	this.tabuleiro=tabuleiro;
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize=tk.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setBounds(screenWidth/2 - DEFAULT_WIDTH/2, screenHeight/2 - DEFAULT_HEIGHT/2, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        menuInicial.setSize(screenSize);
        novoOuCarregar();
    }    
      
    public boolean getCanStartGame() {
		return canStartGame;
	}

	public int getGameMode() {
		return gameMode;
	}
	
	public String[] getNomesJogadores() {
		return nomesJogadores;
	}

	// aparecendo as opcoes de novo jogo ou carregar 
    private void novoOuCarregar () {
        JButton novoJogo = new JButton("Novo Jogo");
        JButton carregarJogo = new JButton("Carregar Jogo"); 
        
        //botao de novo jogo
        novoJogo.setBounds(BUTTON_START_X, BUTTON_START_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        menuInicial.add(novoJogo);  
        
        //botao de carregar jogo
        carregarJogo.setBounds(BUTTON_START_X, BUTTON_START_Y + BUTTON_SPACE, BUTTON_WIDTH, BUTTON_HEIGHT);  
        menuInicial.add(carregarJogo);

        //menuInicial = f1;
        novoJogo.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){ 
        		System.out.println("novo jogo");
        		
				//repaint();
                int numJogadores = 0;

    			while ( numJogadores < 2 || numJogadores > 6 ) {
    				numJogadores = quantidadeJogadores();
    			}

                getContentPane().remove(menuInicial);
                repaint();
                getContentPane().add(menuJogadores);
                setVisible(true);
    			registraJogadores();
            	
            }  
        }); 
        
        carregarJogo.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){ 

                //repaint();
        		//getContentPane().remove(f);
        		System.out.println("Carregar jogo");
            }  
        }); 
        menuInicial.setLayout(null);
        menuInicial.setVisible(true);
        add(menuInicial);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    // janela de perguntar a quantidade de jogadores
    private int quantidadeJogadores() {
		while (!GAME_MODE_STATUS) {			
			try {
				String msg = "Quantos jogadores? 2 ou 4";
				qtdeJogadores = Integer.parseInt( JOptionPane.showInputDialog(janelaInicial, msg) );
			} 
			catch (NumberFormatException e) {
				qtdeJogadores = 0;
			}
			
			if ((qtdeJogadores == TWO_PLAYERS) || (qtdeJogadores == FOUR_PLAYERS)) {
				GAME_MODE_STATUS = true;
			}
			else {
    			JOptionPane.showMessageDialog(menuInicial,"Número de jogadores inválido");
			}
		}

		return qtdeJogadores;
    }

    // janela de perguntar o nome dos jogadores
    private void registraJogadores() {
		nomesJogadores = new String[qtdeJogadores];
		Point pos = new Point((TEXT_START_X + (2 - qtdeJogadores) * 60), TEXT_START_Y);
		
		JTextField[] texts = new JTextField[qtdeJogadores];
		
		for(int i = 0; i < qtdeJogadores; i++)
		{
			JLabel label = new JLabel("Jogador " + (i + 1));
			
			texts[i] = new JTextField("Jogador " + (i + 1), 16);
			
			label.setBounds(pos.x, pos.y, TEXT_WIDTH, TEXT_WIDTH/2);
			pos.translate(TEXT_WIDTH, 0);
			
			texts[i].setBounds(pos.x, pos.y, TEXT_WIDTH, TEXT_WIDTH/2);
			
			if (qtdeJogadores == TWO_PLAYERS)
				pos.translate(- TEXT_WIDTH, TEXT_WIDTH);
			
			else {
				if((i+1) % 2 == 0)
					pos.translate(- 4 * TEXT_WIDTH,  TEXT_WIDTH);
				else
					pos.translate(2 * TEXT_WIDTH, 0);
			}
			
			menuJogadores.add(label);
			menuJogadores.add(texts[i]);
		}
		

        JButton modo1x1 = new JButton(" Modo 1 VS 1");
        JButton modo2x2 = new JButton(" Modo 2 VS 2");
        JButton iniciarJogo = new JButton("Iniciar Jogo");
        
    	modo1x1.setBounds(TEXT_START_X + (2 - qtdeJogadores) * 60, pos.y, BUTTON_WIDTH, BUTTON_HEIGHT);
        modo2x2.setBounds(TEXT_START_X - (2 - qtdeJogadores) * 60, pos.y, BUTTON_WIDTH, BUTTON_HEIGHT);
        
        pos.translate(TEXT_WIDTH, TEXT_WIDTH);
        iniciarJogo.setBounds(BUTTON_START_X, pos.y , BUTTON_WIDTH, BUTTON_HEIGHT);
        
        if (qtdeJogadores == FOUR_PLAYERS) {
        	gameModeSelected = false;
            menuJogadores.add(modo1x1); 
            menuJogadores.add(modo2x2); 
            
            modo1x1.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){ 
                	gameMode = 1;
    				System.out.printf("\nModo de Jogo Escolhido: 1 VS 1!\n");
    				gameModeSelected = true;
                }  
            }); 
            
            modo2x2.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){ 
                	gameMode = 2;
    				System.out.printf("\nModo de Jogo Escolhido: 2 VS 2!\n");
    				gameModeSelected = true;
                }  
            }); 
        }


        menuJogadores.add(iniciarJogo);  
        iniciarJogo.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
            	if (qtdeJogadores == FOUR_PLAYERS && gameMode == 1) {
            		for(int i = 0; i < (qtdeJogadores / 2); i++)
    					nomesJogadores[i] = texts[i].getText() + " e " + texts[i + 2].getText();
            	}
            	else
	            	for(int i = 0; i < qtdeJogadores; i++)
						nomesJogadores[i] = texts[i].getText();
				
				System.out.printf("\nJogo Iniciado\n");
				
				if (!gameModeSelected)
					JOptionPane.showMessageDialog(menuInicial,"Escolha o modo de jogo antes!");
				else {
	                getContentPane().remove(menuJogadores);
					getContentPane().add(new Tabuleiro());
					setVisible(true);
					canStartGame = true;
				}
            }  
        }); 
    } 
}