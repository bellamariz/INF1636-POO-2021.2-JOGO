package controller;

import model.ModelFacade;
import view.ViewFacade;

public class ControllerFacade {

	private static ControllerFacade controller = null;
	private boolean canStartGame = false;
	
	public ControllerFacade() {
		ViewFacade.startView();
		
		while (!canStartGame)
			if (ViewFacade.getCanStartGame())
				ModelFacade.startModel(ViewFacade.getGameMode(),ViewFacade.getNomeJogadores());
				canStartGame = true;
	}
	
	public static ControllerFacade getController() {
		if(controller == null) {
			controller = new ControllerFacade();
		}
		return controller;
	} 
}
