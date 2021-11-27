package controller;

import model.ModelFacade;
import util.Observavel;
import util.Observador;
import util.Operacoes;
import view.ViewFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControllerFacade implements Observador, Observavel {
	private static ControllerFacade controller = null;
	private boolean canStartGame = false;
	private static ViewFacade viewFacade = null;
	private static ModelFacade modelFacade = null;

	private ControllerFacade() {
		viewFacade = ViewFacade.getInstance();
		modelFacade = ModelFacade.getInstance();
		
		viewFacade.startView();

		viewFacade.addObserverToTabView(this);

		this.adicionarObservador(viewFacade);

		modelFacade.adicionarObservador(this);

		while (!canStartGame)
			if (viewFacade.getCanStartGame())
				modelFacade.startModel(viewFacade.getGameMode(), viewFacade.getNomeJogadores());
				canStartGame = true;
	}
	
	public static ControllerFacade getController() {
		if(controller == null) {
			controller = new ControllerFacade();
		}
		return controller;
	}
	
	public static ViewFacade getViewFacade() {
		return viewFacade;
	}
	
	public static ModelFacade getModelFacade() {
		return modelFacade;
	}

	public static void s(Object o) {
		System.out.println(o);
	}

	@Override
	public void update(Observavel o, Object... args) {
		s("De " + o.getClass().getSimpleName() + " Para " + this.getClass().getSimpleName());

		if (args == null) {
			return;
		}

		final int operacao = (int) args[0];
		String mensagem = null;

		try {
			mensagem = (String) args[1];
		}
		catch (ClassCastException ignored) {}

		if (operacao == Operacoes.OPERACAO_OLA) {
			s("Operacao 1. Msg: " + mensagem);
			notificarObservadores(1, "Tchau");
		}
		else if (operacao == Operacoes.DADO_LANCADO) {
			final int dado = (int) args[1];
//			final double valor2 = args[2];
//			...n
			s("Recebemos via mensagem o dado " + dado);
		}
		else {
			s(o + " " + Arrays.asList(args).toString());
		}
	}

	protected List<Observador> observadores = new ArrayList<>();
	private boolean hasChanged;

	public void adicionarObservador(Observador o) {
		if (!observadores.contains(o)) {
			this.observadores.add(o);
		}
	}

	public void clearChanged() {}

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
