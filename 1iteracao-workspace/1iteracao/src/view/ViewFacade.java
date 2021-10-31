package view;

import model.ModelFacade;

public class ViewFacade {

	public static JanelaInicial janelaInicial;
	public static Tabuleiro tabuleiro;
	public static int GAME_MODE;
	public static String[] jogadores;
	
	static public void startView() {
		tabuleiro = new Tabuleiro();
		janelaInicial = new JanelaInicial(tabuleiro);
		janelaInicial.setTitle("Latitude 90");
		janelaInicial.setVisible(true);
		GAME_MODE = janelaInicial.getGAME_MODE();
		jogadores = janelaInicial.getJogadores();
	}
	
	public void startController() {
		ModelFacade.modelStart(ViewFacade.GAME_MODE, ViewFacade.jogadores);
	}
}
