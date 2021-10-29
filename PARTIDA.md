**CONTEUDO**

- 1 tabuleiro, 3 dados, 24 exploradores, 12 fichas de meta, 18 cartas;
- Cada jogador só tem *6 exploradores*;
- Cores: Verde, Amarelo, Azul, Preto, Vermelho, Branco; --> cores dos exploradores e do dado colorido
- Embaralhar as 18 cartas;

**INICIO**
- Cada jogador lança um dado, o de maior numero começa o jogo;

**PONTUAÇÃO**
- Metas conquistadas + exploradores que antingiram o polo oposto;

**TABULEIRO**
- O jogador pode mover 1 ou 2 exploradores;
- Cada dado = 1 movimento;
- Conquistar polo: numero dos dados = numero exato de casas restantes para alcançá-lo;
- Se numero dos dados for SUPERIOR: explorador atravessa o polo (como uma casa comum);
- Quando um explorador conquista o polo --> sai do tabuleiro;
- Casa fechada = dois ou mais peões *de um mesmo jogador OU da mesma dupla*;
- Captura = um jogador entra na casa contendo *UM explorador do OPONENTE* (não vale se é dupla);
- Quando captura --> explorador do oponente retorna ao polo de origem;

**META E CARTA DINÂMICA**
- 1 meta = 1 ponto;
- Jogo não acaba se não conquistarem nenhuma meta;
PASSOS:
- Meta conquistada quando *dois exploradores do mesmo JOGADOR/COR* fecham a casa da meta;
- Ficha da meta retirada do tabuleiro e vai pro jogador;
- Jogador retira uma carta dinâmica e guarda;
- Jogador termina de andar (se DADOS NORMAIS) ou joga o dado colorido (se DADOS DUPLOS);
- Jogador realiza a ação da carta dinâmica;


**SIMULAÇÃO DE PARTIDA 1x1**

- Jogadores: 2;
- Cores: 2;
- Individual: Jogador 1 no PN, Jogador 2 no PS;
- Cada jogador pode capturar qualquer adversário;

    *CONDIÇÃO DE TÉRMINO*
    - Todos os exploradores de uma cor (UM JOGADOR) conquistarem o polo oposto.
    - Soma os pontos de cada JOGADOR, e a maior pontuação ganha.

    *DADOS DUPLOS/DADO COLORIDO*
    - Um jogador obtem dados duplos -> direito de jogar dado colorido.
    - Jogou o dado colorido: 
        1. Cor do Oponente - Escolhe um dos exploradores oponentes pra voltar pro polo de origem;
        2. Cor do próprio jogador - Escolhe um dos seus exploradores e conquista o polo oposto;
        3. Cor não se aplica - Ignora;


**SIMULAÇÃO DE PARTIDA 2x2**

- Jogadores: 4;
- Cores: 4;
- Duplas: Jogador 1 e 2 no PN, Jogador 3 e 4 no PS;

    *CONDIÇÃO DE TÉRMINO*
    - Todos os exploradores de uma cor (UM JOGADOR) conquistarem o polo oposto.
    - Soma os pontos de cada DUPLA, e a maior pontuação ganha.

    *DADOS DUPLOS/DADO COLORIDO*
    - Um jogador obtem dados duplos -> direito de jogar dado colorido.
    - Jogou o dado colorido: 
        1. Cor do Oponente - Escolhe um dos exploradores oponentes pra voltar pro polo de origem;
        2. Cor do próprio jogador - Escolhe um dos seus exploradores e conquista o polo oposto;
        3. Cor da dupla - Escolhe um dos exploradores da dupla para conquistar o polo oposto;
        4. Cor não se aplica - Ignora;
