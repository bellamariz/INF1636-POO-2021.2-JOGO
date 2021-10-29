package model;

class Meta {
	private final int QTDE_FICHAS = 12;
	private final int PONTO_META = 1;
	private int metasRestantes = QTDE_FICHAS;
	private boolean metaNoTabuleiro = false;
	private boolean capturavel = false;

	public Meta() {}

	public int getMetasRestantes() {
		return metasRestantes;
	}
	
	public boolean isMetaNoTabuleiro() {
		return metaNoTabuleiro;
	}
	
	public int getPontoMeta() {
		return PONTO_META;
	} 

	public void setMetaNoTabuleiro(boolean metaNoTabuleiro) {
		this.metaNoTabuleiro = metaNoTabuleiro;
	}

	public boolean isCapturavel() {
		return capturavel;
	}

	public void setCapturavel(boolean capturavel) {
		this.capturavel = capturavel;
	}

	//Captura a meta
	public void capturaMeta() {
		if (this.metasRestantes > 0) {
			this.metasRestantes-=1;
		}
	}
	
	//Confere se pela menos uma meta foi capturada durante o jogo
	public boolean confereObjetivoMeta() {
		return (metasRestantes<QTDE_FICHAS) && (metasRestantes>=0);
	}
	
	//Confere se as metas esgotaram
	public final boolean metasEsgotadas() {
		boolean esgotou = (this.metasRestantes <= 0);
		if (esgotou) {
			System.out.println("\nNão há metas restantes no tabuleiro.\n");
			return true;
		}
		else
			return false;
	}
}
