package controller;

import model.ModelFacade;
import view.ViewFacade;

public class ControllerFacade {

	private static ControllerFacade c = null;
	
	
	public ControllerFacade() {
		//ModelFacade.startModel();
		ViewFacade.startView();
	}
	
	public static ControllerFacade getController() {
		if(c == null) {
			c = new ControllerFacade();
		}
		return c;
	} 
}
