package controller;

import model.ModelFacade;
import view.ViewFacade;

public class ControllerFacade {
	private static ControllerFacade controller = null;
	private boolean canStartGame = false;
	private static ViewFacade view = null;
	private static ModelFacade model = null;
	
	private ControllerFacade() {
		view = ViewFacade.getInstance();
		model = ModelFacade.getInstance();
		
//		view.setListener(model);
		
		view.startView();
		while (!canStartGame) {
			if (view.getCanStartGame()) {
				model.startModel(view.getGameMode(),view.getNomeJogadores());
				canStartGame = true;
			}
		}
	}
	
	public static ControllerFacade getController() {
		if(controller == null) {
			controller = new ControllerFacade();
		}
		return controller;
	}
	
	public static ViewFacade getView() {
		return view;
	}
	
	public static ModelFacade getModel() {
		return model;
	}
}
