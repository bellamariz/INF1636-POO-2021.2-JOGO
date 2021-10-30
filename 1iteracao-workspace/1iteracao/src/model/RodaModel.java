package model;

public class RodaModel {
	private ModoJogo modo = null;
	//TODO estruturar a classe RodaModel como Facade

	public RodaModel(int mode, String names[]) {
		modo = new ModoJogo(mode, names);
	}
}
