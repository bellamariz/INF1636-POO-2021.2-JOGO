package model;

class CartaDinamica {
	private final String cartaAcao;
	private final String cartaLicaoAmbiental;
	
	public CartaDinamica(String acao, String licaoAmbiental) {
		this.cartaAcao=acao;
		this.cartaLicaoAmbiental=licaoAmbiental;
	}

	public String getCartaAcao() {
		return cartaAcao;
	}

	public String getCartaLicaoAmbiental() {
		return cartaLicaoAmbiental;
	}
	
	public void exibeCartaEscolhida() {
		System.out.println("A Carta Dinâmica escolhida é:\n" + this.getCartaAcao() + " - " + this.getCartaLicaoAmbiental() +"\n");
	}

}
