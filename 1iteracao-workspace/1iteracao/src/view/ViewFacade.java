package view;

public class ViewFacade {
	private static ViewFacade view = null;
	private JanelaInicialView janelaInicialView;
	private TabuleiroView tabuleiroView;
	
	private ViewFacade() {}
	
	public static ViewFacade getInstance() {
		if(view == null) {
			view = new ViewFacade();
		}
		return view;
	}

	public void startView () {
		tabuleiroView = new TabuleiroView();
		janelaInicialView = new JanelaInicialView(tabuleiroView);
		janelaInicialView.setTitle("Latitude 90");
		janelaInicialView.setVisible(true);
	}
	
	public String[] getNomeJogadores() {
		return janelaInicialView.getNomesJogadores();
	}
	
	public int getGameMode() {
		return janelaInicialView.getGameMode();
	}
	
	public boolean getCanStartGame() {
		return janelaInicialView.getCanStartGame();
	}
}
