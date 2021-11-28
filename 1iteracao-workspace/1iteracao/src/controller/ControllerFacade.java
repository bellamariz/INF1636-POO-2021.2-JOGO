package controller;

import model.ModelFacade;
import util.Observavel;
import util.Observador;
import util.Operacoes;
import view.ViewFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;

public class ControllerFacade implements Observador, Observavel {
	private static ControllerFacade controller = null;
	private boolean canStartGame = false;
	private static ViewFacade viewFacade = null;
	private static ModelFacade modelFacade = null;
	private JFileChooser fileChooser = new JFileChooser();

	private ControllerFacade() {
		viewFacade = ViewFacade.getInstance();
		modelFacade = ModelFacade.getInstance();

		viewFacade.startView();
		viewFacade.addObserverToTabView(this);
		this.adicionarObservador(viewFacade);
		
		while (!canStartGame) {
			if (viewFacade.getCanStartGame()) {
				modelFacade.startModel(viewFacade.getGameMode(), viewFacade.getNomeJogadores());
				canStartGame = true;
			}
		}
		modelFacade.adicionarObservador(this);
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
	
//	public void salvaJogo() {
//        ModelFacade.codificaTabuleiro(codeTab);
//        String s = "";
//        s = s.concat(Integer.toString(rodada));
//        for(int i = 0; i < 8; i++) {
//            for(int j = 0;j < 8; j++) {
//                s = s.concat(" " + Integer.toString(codeTab[i][j]));
//            }
//        }
//        
//        JFileChooser chooser  = new JFileChooser();
//        int retval = chooser.showSaveDialog(null);
//        try{
//            FileWriter fw = new FileWriter(chooser.getSelectedFile()+".txt");
//            fw.write(s);
//            fw.flush();
//            fw.close();
//        } catch(Exception IOException) {} finally {}
//    }
//	
//	public void carregaJogo() {
//        
//        JFileChooser chooser  = new JFileChooser();
//        int retval = chooser.showOpenDialog(null);
//        String out="";
//                try{
//                    Scanner scan = new Scanner(chooser.getSelectedFile());
//                    
//                    while(scan.hasNextLine()) {
//                        out= out + scan.nextLine();
//                    }
//                } catch(FileNotFoundException e) {} finally {}
//        
//        String[] result = out.split(" "); 
//        
//        rodada = Integer.parseInt(result[0]) - 1;
//        
//        for(int i = 0; i < 8; i++) {
//            for(int j = 0;j < 8; j++) {
//            	int index = (i*8)+j + 1;
//            	
//            	codeTab[i][j] = Integer.parseInt(result[index]);
//            }
//        }
//        
//        ModelFacade.carregaTabuleiro(codeTab);
//        proxRodada();
//    }


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
