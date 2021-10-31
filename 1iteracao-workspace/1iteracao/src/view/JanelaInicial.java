package view;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.ModelFacade;

class JanelaInicial extends JFrame{
	
	private JPanel menuInicial;
	private JPanel menuJogadores;
	private JPanel tabuleiro;
    private JanelaInicial p = this;
	
	private int QTD_JOGADORES = 0;
	private int GAME_MODE = 1;
	private boolean GAME_MODE_STATUS = false;
	private boolean CAN_START = true;
	private static final int TWO_PLAYERS = 2;
	private static final int FOUR_PLAYERS = 4;
	public int DEFAULT_WIDTH = 1200;
    public int DEFAULT_HEIGHT = 700;
	public int BUTTON_WIDTH = 150;
    public int BUTTON_HEIGHT = 30;
    public int BUTTON_SPACE = 60;
    public int BUTTON_START_X = 525;
    public int BUTTON_START_Y = 280;
	public int TEXT_WIDTH = 80;
    public int TEXT_START_X = 520;
    public int TEXT_START_Y = 150;
    JPanel f1 = new JPanel();    
    JPanel f2 = new JPanel();
    JPanel f3 = new JPanel();
    String jogadores[] = null;

    public JanelaInicial(Tabuleiro tabuleiro) {

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize=tk.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setBounds(screenWidth/2 - DEFAULT_WIDTH/2, screenHeight/2 - DEFAULT_HEIGHT/2, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        f1.setSize(screenSize);
        novoOuCarregar();
    }    
      
    public boolean isCAN_START() {
		return CAN_START;
	}

	public void setCAN_START(boolean cAN_START) {
		CAN_START = cAN_START;
	}

	public int getGAME_MODE() {
		return GAME_MODE;
	}

	public void setGAME_MODE(int GAME_MODE) {
		this.GAME_MODE = GAME_MODE;
	}
	
	public String[] getJogadores() {
		return jogadores;
	}


	public void setJogadores(String[] jogadores) {
		this.jogadores = jogadores;
	}


	// aparecendo as opcoes de novo jogo ou carregar 
    public void novoOuCarregar () {
        JButton novoJogo = new JButton("Novo Jogo");
        JButton carregarJogo = new JButton("Carregar Jogo"); 
        
        //botao de novo jogo
        novoJogo.setBounds(BUTTON_START_X, BUTTON_START_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        f1.add(novoJogo);  
        
        //botao de carregar jogo
        carregarJogo.setBounds(BUTTON_START_X, BUTTON_START_Y + BUTTON_SPACE, BUTTON_WIDTH, BUTTON_HEIGHT);  
        f1.add(carregarJogo);

        menuInicial = f1;
        novoJogo.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){ 
        		System.out.println("novo jogo");
        		
				//repaint();
                int numJogadores = 0;

    			while ( numJogadores < 2 || numJogadores > 6 ) {
    				numJogadores = quantidadeJogadores();
    			}

                getContentPane().remove(f1);
                repaint();
                getContentPane().add(f2);
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
        f1.setLayout(null);
        f1.setVisible(true);
        add(f1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        menuInicial = f1;
        this.tabuleiro = tabuleiro;
    }
    
    // janela de perguntar a quantidade de jogadores
    public int quantidadeJogadores() {
		while (!GAME_MODE_STATUS) {			
			try {
				String msg = "Quantos jogadores? 2 ou 4";
				QTD_JOGADORES = Integer.parseInt( JOptionPane.showInputDialog(p, msg) );
			} 
			catch (NumberFormatException e) {
				QTD_JOGADORES = 0;
			}
			
			if ((QTD_JOGADORES == TWO_PLAYERS) || (QTD_JOGADORES == FOUR_PLAYERS)) {
				GAME_MODE_STATUS = true;
			}
			else {
    			JOptionPane.showMessageDialog(f1,"Número de jogadores inválido");
			}
		}

		return QTD_JOGADORES;
    }

    // janela de perguntar o nome dos jogadores
    public void registraJogadores() {
		jogadores = new String[QTD_JOGADORES];
		Point pos = new Point((TEXT_START_X + (2 - QTD_JOGADORES) * 60), TEXT_START_Y);
		
		JTextField[] texts = new JTextField[QTD_JOGADORES];
		
		for(int i = 0; i < QTD_JOGADORES; i++)
		{
			JLabel label = new JLabel("Jogador " + (i + 1));
			DefaultListModel<String> m = new DefaultListModel<String>();
			
			texts[i] = new JTextField("Jogador " + (i + 1), 16);
			
			label.setBounds(pos.x, pos.y, TEXT_WIDTH, TEXT_WIDTH/2);
			pos.translate(TEXT_WIDTH, 0);
			
			texts[i].setBounds(pos.x, pos.y, TEXT_WIDTH, TEXT_WIDTH/2);
			
			if (QTD_JOGADORES == TWO_PLAYERS)
				pos.translate(- TEXT_WIDTH, TEXT_WIDTH);
			
			else {
				if((i+1) % 2 == 0)
					pos.translate(- 4 * TEXT_WIDTH,  TEXT_WIDTH);
				else
					pos.translate(2 * TEXT_WIDTH, 0);
			}
			
			f2.add(label);
			f2.add(texts[i]);
		}
		

        JButton modo1x1 = new JButton(" Modo 1 VS 1");
        JButton modo2x2 = new JButton(" Modo 2 VS 2");
        JButton iniciarJogo = new JButton("Iniciar Jogo");
        
    	modo1x1.setBounds(TEXT_START_X + (2 - QTD_JOGADORES) * 60, pos.y, BUTTON_WIDTH, BUTTON_HEIGHT);
        modo2x2.setBounds(TEXT_START_X - (2 - QTD_JOGADORES) * 60, pos.y, BUTTON_WIDTH, BUTTON_HEIGHT);
        
        pos.translate(TEXT_WIDTH, TEXT_WIDTH);
        iniciarJogo.setBounds(BUTTON_START_X, pos.y , BUTTON_WIDTH, BUTTON_HEIGHT);
        
        if (QTD_JOGADORES == FOUR_PLAYERS) {
        	CAN_START = false;
            f2.add(modo1x1); 
            f2.add(modo2x2); 
            
            modo1x1.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){ 
                	GAME_MODE = 1;
    				System.out.printf("\nModo de Jogo Escolhido: 1 VS 1!\n");
    				CAN_START = true;
                }  
            }); 
            
            modo2x2.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){ 
                	GAME_MODE = 2;
    				System.out.printf("\nModo de Jogo Escolhido: 2 VS 2!\n");
    				CAN_START = true;
                }  
            }); 
        }


        f2.add(iniciarJogo);  
        iniciarJogo.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
            	if (QTD_JOGADORES == FOUR_PLAYERS && GAME_MODE == 1) {
            		for(int i = 0; i < (QTD_JOGADORES / 2); i++)
    					jogadores[i] = texts[i].getText() + " e " + texts[i + 2].getText();
            	}
            	else
	            	for(int i = 0; i < QTD_JOGADORES; i++)
						jogadores[i] = texts[i].getText();
				
				System.out.printf("\nJogo Iniciado\n");
				
				if (!CAN_START)
					JOptionPane.showMessageDialog(f1,"Escolha o modo de jogo antes");
				else {
					//TODO: mudar isso criando a ViewFacade
	                getContentPane().remove(f2);
					getContentPane().add(new Tabuleiro());
					setVisible(true);
					ModelFacade.modelStart(GAME_MODE, jogadores);
					

				}
            }  
        }); 
    } 
}