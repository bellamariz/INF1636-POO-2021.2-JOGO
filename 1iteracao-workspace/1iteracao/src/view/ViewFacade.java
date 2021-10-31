package view;

public class ViewFacade {
	public static JanelaInicial janelaInicial;
	public static Tabuleiro tabuleiro;

	public static void startView () {
		tabuleiro = new Tabuleiro();
		janelaInicial = new JanelaInicial(tabuleiro);
		janelaInicial.setTitle("Latitude 90");
		janelaInicial.setVisible(true);
	}
	
	public static String[] getNomeJogadores() {
		return janelaInicial.getNomesJogadores();
	}
	
	public static int getGameMode() {
		return janelaInicial.getGameMode();
	}
	
	public static boolean getCanStartGame() {
		return janelaInicial.getCanStartGame();
	}
}
