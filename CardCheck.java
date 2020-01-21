package videopoker;

import java.util.Arrays;

/**
 * Classe que realiza os checks da mao, e retorna se houve algum premio
 * @author Vinicius Finke Raiter Jose 9791052 and Luca Machado Bottino 9760300
 */

public class CardCheck {    // classe que testa as combinacoes e retorna o premio do jogador, se houver

    /**
     * Metodo straight testa se a sequencia de cartas forma um straight
     * @param hand vetor da mao do jogador
     * @return 1 se ha um straight, 2 se é um royal straight (de 10 a AS) para depois checar se recebe o premio maximo
     * @return 0 se nao houve straight
     */

    private static int straight (int[] hand){  // checa para ver se formou um straight

        if ((hand[4] % 13) != 12){
            if ((hand[0]%13) + 1 == (hand[1]%13) && (hand[1]%13) + 1 == (hand[2]%13) && (hand[2]%13) + 1 == (hand[3]%13) && (hand[3]%13) + 1 == (hand[4]%13))
                return 1;   //straight comum
        }else{
            if((hand[0]%13) + 1 == (hand[1]%13) && (hand[1]%13) + 1 == (hand[2]%13) && (hand[2]%13) + 1 == (hand[3]%13) && (hand[3]%13) + 1 == (hand[4]%13))
                    return 2;   //royal straight
        }
        return 0;   // nao teve straight
    }

    /**
     * Metodo flush testa se ha um flush em cena
     * @param hand vetor com a mao do jogador
     * @return 1 se houve flush, 0 caso contrario
     */
    private static int flush(int[] hand){

        int cardsuit = hand[0]/13;  // busca o naipe da carta, 0 = diamond, 1 = spades, 2 = hearts, 3 = clubs

        for (int i = 1; i < 5; i++){
            if (hand[i]/13 != cardsuit) return 0;   // se alguma das cartas tiver um naipe diferente do checado, nao teve flush
        }

        return 1;   // todas as cartas sao do mesmo naipe, flush
    }


    /**
     * Metodo score é o núcleo da classe, alem de tambem checar pelos premios menores e mais faceis
     * por exemplo mas não limitado a: pares e trincas
     * @param hand vetor com a mao do jogador
     * @param aposta o valor int que ele apostou nessa rodada
     * @return
     */
    public static int score(int[] hand, int aposta) {

        int[] count = new int[13];

        for (int i = 0; i < 5; i++) {
            count[(hand[i] % 13)]++;
        }

        Arrays.sort(hand);

        if (straight(hand) + flush(hand) == 3){     // se for royal straight (retornado 2 pelo metodo) E flush, premio maximo

            System.out.println("ROYAL FLUSH!!!");
            return aposta*200;
        }
        if(straight(hand) + flush(hand) == 2){      // straight (retornando 1 do metodo) e flush

            System.out.println("STRAIGHT FLUSH!");
            return aposta*100;
        }
        if(flush(hand) == 1){

            System.out.println("FLUSH!");
            return aposta*10;
        }
        if(straight(hand) == 1 || straight(hand) == 2){     // se for straight, nao importa o royal se nao houver flush

            System.out.println("STRAIGHT!");
            return aposta*5;
        }

        Arrays.sort(count);

        if (count[12] == 4) {
            System.out.println("FOUR OF A KIND!");
            return aposta * 50; //quadra
        } else {
            if (count[12] == 3) {
                if (count[11] == 2) {
                    System.out.println("FULL HOUSE!");
                    return aposta * 20;
                } else {
                    System.out.println("THREE OF A KIND!");
                    return aposta * 2;
                }
            } else {
                if (count[12] == 2 && count[11] == 2) {
                    System.out.println("2 PAIRS");
                    return aposta;
                } else {
                    System.out.println("Sem sorte, tente outra vez");
                    return 0; //nada
                }
            }
        }
    }
}
