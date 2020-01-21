package videopoker;

import entradateclado.EntradaTeclado;
import java.util.*;

/**
 * programa de poker de 5 cartas
 * @author Luca Machado Bottino 9760300 e Vinicius Finke Raiter Jose 9791052
 *
 */


public class VideoPoker{
	
	/**
	 *"     " 
	 *" _0_ "
	 *"| A |"
	 *"| E |"
	 *"'---'"
	 *pixel art das cartas
	 * ps naipe
	 * 	O = ouros
	 * 	E = espadas
	 * 	C = copas
	 * 	P = paus
	* */
	
	private static void printHand(int [] hand){
		/**
		 * printHand imprime a mao do jogador
		 * utilizamos uma notacao interna em que
		 * ordem de numero 2, 3, 4 .. j, q, k, A
		 * ordem de naipe ouro < espadas < copas < paus
		 * ex. 0 => 2 de ouros, 13 => 2 de espadas..
		 * @param vetor de inteiros com a mao do jogador
		 * funcao void (sem retorno)
		 */
		char numero = '0';
		char naipe = 'R';
		int naipeInt, numeroInt, n, i;


		for(i=0;i<5; i++){

			n = hand[i];
			numeroInt = (int) n % 13;
			naipeInt = (int) n/13;

			switch (numeroInt){
				case 0:
					numero = '2';
					break;
				case 1:
					numero = '3';
					break;
				case 2:
					numero = '4';
					break;
				case 3:
					numero = '5';
					break;
				case 4:
					numero = '6';
					break;
				case 5:
					numero = '7';
					break;
				case 6:
					numero = '8';
					break;
				case 7:
					numero = '9';
					break;
				case 8:
					numero = '0';
					break;
				case 9:
					numero = 'J';
					break;
				case 10:
					numero = 'Q';
					break;
				case 11:
					numero = 'K';
					break;
				case 12:
					numero = 'A';
					break;
			}

			switch (naipeInt){
				case 0:
					naipe = 'O';
					break;
				case 1:
					naipe = 'E';
					break;
				case 2:
					naipe = 'C';
					break;
				case 3:
					naipe = 'P';
					break;
			}

			System.out.println(" _"+ i +"_ ");
			if(numero != '0'){
				System.out.println("| "+ numero +" |");
			}else{
				System.out.println("|10 |"); //numero 10 exige representacao diferente
			}
			System.out.println("| "+ naipe +" |");
			System.out.println("'---'\n");
		}  
		return;
		
	}
	
	
	private static void changeCards(String answer, int[] hand, Stack deck){
			/**changeCards pega como argumento as cartas da mao, e uma string
			 * reposta que diz quais cartas devem ser trocadas
			 * coloca essas cartas de volta no baralho, embaralha e
			 * puxa as cartas de novo
			 * @param String com a resposta de quais cartas trocar, vetor de inteiros da mao, e pilha deck de cartas
			 *
			 * */

			int i;
			String splited[];
			splited = answer.split(" ");
			int size = splited.length;
			int[] auxInt = new int [size];
			
			for(i=0;i<size;i++){//separa a mensagem digitada em um vetor de inteiros
				auxInt[i] = Integer.parseInt(splited[i]);
			}
			
			for(i=0;i<size;i++){//coloca cartas de volta no baralho
				deck.push(hand[auxInt[i]]);
			}
			
			Collections.shuffle(deck);//embaralha o deck
			
			for(i=0;i<size;i++){//puxa novas cartas
				hand[auxInt[i]] = (int) deck.pop();
			}	
			
			return;
	}
	
	public static void main (String args[]) {

		
		String answer = "0 1";
		int[] hand = new int [5];//inicia a mao
		int i; 
		Stack deck = new Stack();
		String exit = "a";
		int premio=0;
		

		for(i=0; i<52; i++) {
			deck.push(i);//inicia o baralho em notacao interna
			/*0 .. 12 ouros
			13 .. 25 espadas
			26 .. 38 copas
			39 .. 51 paus
			 */
		}
		
		int creditos = 200;
		int aposta = 0;


		do{
			do {

				System.out.println("Digite o Valor da sua aposta");
				try{
					aposta = EntradaTeclado.leInt();
				}
				catch(Exception e){
					System.out.println("Input Error");
				}
			}while(aposta<0 || aposta > creditos);
			 
			creditos = creditos - aposta; //retira o dinheiro da aposta
			
			Collections.shuffle(deck); //embaralha o deck
			
			
			for(i=0;i<5;i++){//tira as 5 cartas do deck
				hand[i] = (int) deck.pop();
			}
			
			printHand(hand);
			
			/*Realiza ate 2 trocas de 0 a 5 cartas*/
			System.out.println("Digite as cartas que voce quer trocar ou -1 para continuar");


			try{
				answer = EntradaTeclado.leString();
			}
			catch(Exception e){
				System.out.println("Input Error");
			}


			if(!answer.equals("-1")) {

				changeCards(answer, hand, deck);//troca as cartas
				printHand(hand);//imprime a mao

				System.out.println("Digite as cartas que voce quer trocar ou digite -1 para continuar");

				try {
					answer = EntradaTeclado.leString();
				} catch (Exception e) {
					System.out.println("Input Error");
				}

				if (!answer.equals("-1")) {
					changeCards(answer, hand, deck);
				}
				printHand(hand);
			}

			premio = CardCheck.score (hand, aposta); //score retorna a quantidade de fichas ganhas

			creditos = creditos + premio; //coloca o premio no numero de creditos

			System.out.println("Voce tem: " + creditos + " creditos.");

			System.out.println("Para continuar a jogar digite qualquer tecla\n Para sair digite exit");

			try{
				exit = EntradaTeclado.leString();
			}
			catch(Exception e){
				System.out.println("Input Error");
			}

		}while(creditos > 0 && !exit.equals("exit"));
		
		
		System.out.println("Fim do Jogo!\n Sua Pontuacao Final foi de: " + creditos + " pontos!");//placar final
		
		return;
	}

}
