package controller;

import model.ModelFacade;
import view.ViewFacade;

public class ControllerFacade {

	private static ControllerFacade controller = null;
	
	public ControllerFacade() {
		ViewFacade.startView();
		
		if (ViewFacade.getCanStartGame())
			ModelFacade.startModel(ViewFacade.getGameMode(),ViewFacade.getNomeJogadores());
		else
			System.out.print("Não foi possível iniciar o jogo.\n");
	}
	
	public static ControllerFacade getController() {
		if(controller == null) {
			controller = new ControllerFacade();
		}
		return controller;
	} 
}
