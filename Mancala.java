//Name: Mathias Brook
import java.util.*;

public class Mancala{
  
  int[] board = new int[14];
 
  public Mancala(){
     
  }
  
  //Converts player friendly indexes to raw array indexes
  private int convert(boolean side, int index) {
    int convert = 0;
    if (side == false) convert = 7;
    convert += index - 1;
    return convert;
  }
  
  //Takes all the rocks from the hole opposite of the empty hole and put them in the side with the previously empty hole's mancala
  private int steal(int index) {
    int take = 0;
    int opposite = 12 - index; //holes across from each other always add up to 12
    take = board[opposite];
    board[opposite] = 0;
    return take;
  }
  
  //initializes the board with the correct values
  private void initialize(){ //distributes 4 rocks to all holes except mancalas
    for (int i = 0; i < board.length; i++) {
      if (i != 6 && i != 13) board[i] = 4;
    }
    board[6] = 0;
    board[13] = 0;
  }
  
  //sets the values on the board 
  //true indicates player side
  //false indicates computer side
  //may assume correct input since it is a private method
  private void set(boolean playerComputer,int index,int num){
   int i = convert(playerComputer,index); //converts index to raw array index
   board[i] = num;
  }
  
  //picks up stones from the indicated index and distributes them 
  //returns same boolean if the play lands in the players own mancala
  //or the hole is empty
  //true indicates player side
  //false indicates computer side
  //may assume valid input since it is a private method
  private boolean pick(boolean playerComputer,int index){
   int i = convert(playerComputer, index); //converts index to raw array index
   int count = board[i];
   board[i] = 0;
   i++;
   while (count > 0) { //makes sure all rocks have been distributed
      if (i > 13) i = 0;
      if (i == 13 && playerComputer == false && count >= 1) { //detects if computer's stone has landed in its mancala, add one point to the mancala
         board[i]++;
         if (count == 1) { //if that stone was the last stone, set next turn to be computer again
            count--;
            return false;
         }
         count--;
      } else if (i == 6 && playerComputer == true && count >= 1) { //if computer's stone has landed in its mancala, add one point to the mancala
         board[i]++;
         if (count == 1) { //if that stone was the last stone, set next turn to be player again
            count--;
            return true;
         }
         count--;
      } else if ((i != 6 && i != 13) && count == 1) { //if someones's last stone lands in neither mancala, set the next turn to the other player
         if (playerComputer == true) { //if last stone was the player and lands in an empty hole, steal all the rocks from the hole opposite from it and add it to the mancala
            if (board[i] == 0 && i <= 5 && i >= 0) {
               board[6] += steal(i);
               board[i]++;
               count--;
               return false;
            }
            board[i]++;
            count--;
         } else if (playerComputer == false) { //if last stone was the computer and lands in an empty hole, steal all the rocks from the hole opposite from it and add it to the mancala
            if (board[i] == 0 && i <= 12 && i >= 7) {
               board[13] += steal(i);
               board[i]++;
               count--;
               return true;
            }
            board[i]++;
            count--;
         }
      } else if ((i != 6 && i != 13) && count > 1) { //distributes rocks normally
         board[i]++;
         count--;
      }
      i++;
   }
   return (playerComputer == false) ? true : false; //sets the next turn based on the current player
  }
  
  //Prints out the board. Prints the computer board on top and the player board on the bottom with the stores
  // on each end.
  public String toString(){
     return "  " + board[12] + " " + board[11] + " " + board[10] + " " + board[9] + " " + board[8] + " " + board[7] + "\n" + board[13] + "             " + board[6] + "\n" + "  " + board[0] + " " + board[1] + " " + board[2] + " " + board[3] + " " + board[4] + " " + board[5];
  }
  
  //Determines if the game is over
  //If the game is over, moves the stones from the loser's side to the
  //winner's mancala
  private boolean gameOver(){
    int sum;
    if (board[0] == 0 && board[1] == 0 && board[2] == 0 && board[3] == 0 && board[4] == 0 && board[5] == 0) { //detects if players's side is empty
      sum = board[7] + board[8] + board[9] + board[10] + board[11] + board[12]; //takes the sum of all rocks in computer's area
      board[6] += sum; //adds the sum of rocks to player's mancala
      board[7] = 0; //empties all computer holes
      board[8] = 0;
      board[9] = 0;
      board[10] = 0;
      board[11] = 0;
      board[12] = 0;
      return true;  
    } else if (board[7] == 0 && board[8] == 0 && board[9] == 0 && board[10] == 0 && board[11] == 0 && board[12] == 0) { //detects if computer's side is empty
      sum = board[0] + board[1] + board[2] + board[3] + board[4] + board[5]; //takes the sum of all rocks in player's area
      board[13] += sum; //adds the sum of rocks to computer's mancala
      board[0] = 0; //empties all player holes
      board[1] = 0;
      board[2] = 0;
      board[3] = 0;
      board[4] = 0;
      board[5] = 0;
      return true;
    }
    return false;
  }
  
  
  
//Plays the game. Be sure to use the value returned by pick to determine whose turn it is.
//Once the game is over, announce the winner
  public void play(){ //start a game with player starting first
    initialize(); //sets up the board
    Scanner scan = new Scanner(System.in);
    System.out.println(this + "\nYour turn. Enter a number between 1-6");
    boolean nextPlayerTurn = true;
    boolean over = false;
    int input = scan.nextInt(); //scans for player number input
    while (true) {      
      if (input >= 1 && input <= 6 && nextPlayerTurn == true && board[input-1] != 0) { //if player's input is between 1 - 6 and that certain hole is not empty, distribute the rocks in that hole
         nextPlayerTurn = pick(true,input);
         System.out.println("Player " + input + "\n" + this);
         over = gameOver(); //tests for game over
      } else if (input >= 1 && input <= 6 && nextPlayerTurn == true && board[input-1] == 0) { //if player's selected hole has no rocks, print out an error message
         System.out.println("Your current area has no rocks. Please select another area");
      } else { //if player's input is not between 1 - 6
         System.out.println("Your input is invalid. Please type a number from 1 - 6");
      }
      while (nextPlayerTurn == false && !(board[7] == 0 && board[8] == 0 && board[9] == 0 && board[10] == 0 && board[11] == 0 && board[12] == 0)) { //makes a computer play loop to continue playing until the next turn is player or there are no rocks in any computer holes
         int random = (int)((Math.random()*6)+1); //generates a random number between 1 - 6
         while (board[convert(false,random)] == 0) { //if the generated number hole has no rocks, regenerate a new number
            random = (int)((Math.random()*6)+1);
         }
         nextPlayerTurn = pick(false,random); //distributes the rocks in the random hole
         System.out.println("Computer " + random + "\n" + this);
         over = gameOver(); //tests for game over
      }
      if (over == true) { //if game is over, display winner and break the while input loop
         if (board[6] > board[13]) System.out.println("The Player has won!" + "\n" + this); //decides the winner based on who has the most rocks in mancala
         else if (board[6] == board[13]) System.out.println("Tie!" + "\n" + this);
         else System.out.println("The Computer has won!" + "\n" + this);
         break;
      }
      if (nextPlayerTurn == true) System.out.println("Your turn. Enter a number between 1-6");
      input = scan.nextInt();
    }
  }   
                                
  public static void main(String[] args){
    
    Mancala mygame = new Mancala();
    mygame.initialize();
    System.out.println(mygame);
    System.out.println("Player 3");
    System.out.println(mygame.pick(true,3));
    System.out.println(mygame);
    System.out.println("Player 6");
    System.out.println(mygame.pick(true,6));
    System.out.println(mygame);
    System.out.println("Computer 6");
    mygame.pick(false,6);
    System.out.println(mygame);
    System.out.println("Player 1");
    mygame.pick(true,1);
    System.out.println(mygame);
    System.out.println("Computer 2");
    System.out.println(mygame.pick(false,2));
    System.out.println(mygame);
    System.out.println("Set Player 3 to 0");
    mygame.set(true,3,0);
    System.out.println(mygame);
    System.out.println("Computer 5");
    mygame.pick(false,5);
    System.out.println(mygame);
    System.out.println("Player 6");
    System.out.println(mygame.pick(true,6));
    System.out.println(mygame);
    System.out.println("Computer 6 and being set to 8");
    mygame.set(false,6,8);
    System.out.println(mygame+"\n");
    mygame.pick(false,6);
    System.out.println(mygame);
    System.out.println("Computer 1");
    mygame.pick(false,1);
    System.out.println(mygame);
    System.out.println("Computer 2");
    mygame.pick(false,2);
    System.out.println(mygame);
    System.out.println("Computer 3");
    mygame.pick(false,3);
    System.out.println(mygame);
    System.out.println("Computer 4");
    mygame.pick(false,4);
    System.out.println("Game Over expected false "+mygame.gameOver());
    System.out.println(mygame);
    System.out.println("Computer 5");
    mygame.pick(false,5);
    System.out.println(mygame);
    System.out.println("Computer 6");
    mygame.pick(false,6);
    System.out.println(mygame);
    System.out.println("Game Over expected true "+mygame.gameOver()); 
    System.out.println(mygame);
    System.out.println("==================");
    Mancala game = new Mancala();
    game.play();
  }
}