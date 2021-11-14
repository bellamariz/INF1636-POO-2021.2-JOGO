package model;

import java.util.ArrayList;

public class ModelFacade {
	private static ModelFacade model = null;
	private MovePecas movePecas = null;
	private Dado dado = new Dado();
	
	private ModelFacade() {}
	
	public static ModelFacade getInstance() {
		if(model == null) {
			model = new ModelFacade();
		}
		return model;
	}

	public void startModel (int gameMode, String[] nomesJogadores) {
		movePecas = new MovePecas(gameMode,nomesJogadores,dado);
	}
	
	public int getValorDado() {
		return dado.lanca();
	}
	
	public String getDadoColorido(int dado1, int dado2) {
		return dado.getDadoColorido(dado1, dado2);
	}
	
	public ArrayList<String> getCoresOrdenados(){
		ArrayList<String> coresOrdenadas = new ArrayList<String>(4);
		ArrayList<Jogador> jogadores = movePecas.getJogadoresOrdenados();
		for (Jogador j: jogadores) 
			coresOrdenadas.add(j.getCor());
		
		return coresOrdenadas;
	}

}
