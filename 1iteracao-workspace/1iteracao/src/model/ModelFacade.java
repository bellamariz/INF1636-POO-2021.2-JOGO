package model;

import util.Observador;
import util.Observavel;
import util.Operacoes;

import java.util.ArrayList;
import java.util.List;

public class ModelFacade implements Observavel {
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
		final int valorDado = dado.lanca();
		notificarObservadores(Operacoes.DADO_LANCADO, valorDado);
		return valorDado;
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

	protected List<Observador> observadores = new ArrayList<>();

	public void adicionarObservador(Observador o) {
		if (!observadores.contains(o)) {
			this.observadores.add(o);
		}
	}

	public int contarObservadores() {
		return this.observadores.size();
	}

	public void removerObservador(Observador o) {
		if (this.observadores.contains(o)) {
			this.observadores.remove(o);
		}
	}

	public void notificarObservadores(Object ...args) {
		for (Observador o : this.observadores) {
			o.update(this, args);
		}
	}
}
