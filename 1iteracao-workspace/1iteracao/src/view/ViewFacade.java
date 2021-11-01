package view;

public class ViewFacade {
	public static JanelaInicialView janelaInicialView;
	public static TabuleiroView tabuleiroView;

	public static void startView () {
		tabuleiroView = new TabuleiroView();
		janelaInicialView = new JanelaInicialView(tabuleiroView);
		janelaInicialView.setTitle("Latitude 90");
		janelaInicialView.setVisible(true);
	}
	
	public static String[] getNomeJogadores() {
		return janelaInicialView.getNomesJogadores();
	}
	
	public static int getGameMode() {
		return janelaInicialView.getGameMode();
	}
	
	public static boolean getCanStartGame() {
		return janelaInicialView.getCanStartGame();
	}
}
