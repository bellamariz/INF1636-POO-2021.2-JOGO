package util;

public class Operacoes {
    public static final int OPERACAO_OLA = 1;
    public static final int OPERACAO_TCHAU = 2;
    public static final int ORDENA_JOGADORES = 3; //mostra o dado lancado para ordenar os jogadores
    public static final int DADO_LANCADO = 4; //apertou o botao lancar dado
    public static final int MOVE_EXPLORADOR = 5; //selecionou o explorador e a casa que quer que ele va
    public static final int CONQUISTOU_META = 6; //jogador conquistou uma meta
    public static final int TEM_BARREIRA = 7; //movimento do explorador nao pode ser feito pois tem barreira no caminho
    public static final int CAPTUROU_EXPLORADOR = 8; //o explorador capturou um explorador do jogador adversario
    public static final int DADO_COLORIDO = 9; //caso do dado colorido
    public static final int MOVIMENTO_CARTA = 10; //retirou a carta 1 ou 3 e vai fazer a movimentacao relacionada a elas
    public static final int CONTA_PONTOS = 11; //chamada no fim do jogo
}
