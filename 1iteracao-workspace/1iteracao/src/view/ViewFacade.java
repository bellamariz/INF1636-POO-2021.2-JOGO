package view;

import util.Observador;
import util.Observavel;

public class ViewFacade implements Observavel, Observador {
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

	public TabuleiroView getTabuleiroView() {
		return tabuleiroView;
	}

	public void addObserverToTabView(Observador o) {
		this.tabuleiroView.adicionarObservador(o);
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

	@Override
	public void adicionarObservador(Observador o) {
		this.tabuleiroView.adicionarObservador(o);
	}

	@Override
	public int contarObservadores() {
		return this.tabuleiroView.contarObservadores();
	}

	@Override
	public void removerObservador(Observador o) {
		this.tabuleiroView.removerObservador(o);
	}

	@Override
	public void notificarObservadores(Object... args) {
		this.tabuleiroView.notificarObservadores(args);
	}

	// Linka ViewFacade -> Tabuleiro
	@Override
	public void update(Observavel o, Object... args) {
		this.tabuleiroView.update(o, args);
	}
}
