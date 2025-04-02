/**
   CS 123 Question Tree/20 Questions
   @author Danielle Idzi
   @version 2
   Completion date 3/3/24
*/

import java.util.*;
import java.io.*;

public class QuestionTree
{
  private UserInterface my;
  private QuestionNode overallRoot;
  private int gamewon;
  private int totalgames;

  /**Constructor that initalizes the visual interface, and variables within program
   @param UserInterface to recieve and put information on pop up game window.*/
 
  public QuestionTree (UserInterface ui)
  {
    my = ui;
    overallRoot = new QuestionNode ("Jedi");
    gamewon = 0;
    totalgames = 0;
  }
  
  /**Play's the game by displaying questions, or if there are no more questions on that branch than it asks if the 
      object left is correct. If the object is correct it will add +1 to games won, otherwise it will ask the user to 
      add the new object and a question to differenciate it from the previously last object.*/

  public void play()
  {
   overallRoot = play (overallRoot);   
  }  
  private QuestionNode play(QuestionNode root){
   
   //if its not a leaf node, if it is a question
   if(!isLeaf(root)){
      my.print(root.data);
      
      if (my.nextBoolean()) root.left = play(root.left);
      else root.right = play (root.right);
   
   }
   //it is a leaf node
   else {
      my.print("Would your object happen to be " + root.data + "?");
      //if computer wins
      if (my.nextBoolean()) {
         //todo: I win isn't showing up in main consol
         my.print("I win!");
         my.println("");
         gamewon++;
      }
      else {
         my.println("I lose. What is your object?");
         //grab user answer and create a node
         String tempObject = my.nextLine();
       //  QuestionNode tempoary = new QuestionNode (my.nextLine());
         my.println("Type a yes/no question to distinguish your item from " + root.data + ": " );
         QuestionNode tempoary = new QuestionNode(my.nextLine());
         
         my.println("And what is the answer for your object?");
         boolean questionDecider = my.nextBoolean();
         
         //adding new node to tree
         if (questionDecider){
            tempoary.left = new QuestionNode(tempObject);
            tempoary.right = root;
         }
         else {
            tempoary.right = new QuestionNode(tempObject);
            tempoary.left = root;
         }
         
         root = tempoary;
      }
      totalgames++;
   }
   
   return root;
  }
  
  private boolean isLeaf (QuestionNode root){
   return (root.left == null) && (root.right == null);
  }

  /**Saves the current tree in a .txt file with "Q:" before any question and "A:" before any of the answers
   @param output location and type*/
  public void save(PrintStream output)
  {
      this.save(overallRoot, output);    
  }
  private void save (QuestionNode root, PrintStream output){
        
    if (root == null) return;
    //if its a leaf then it must be an answer, and add "Q: " 
    if (this.isLeaf(root)){
      output.println("A:" + root.data);
    }
    //its an question 
    else {
       output.println("Q:" + root.data);
    }
   
   save(root.left, output);
   save(root.right, output);
  
  return;
  }
  
  /**
  Loads old games and converts text files back into a tree database.
  @param current docuemnt that contains the old tree information
  @throw NullPointerException if there is no text in file
  */
  public void load(Scanner input)
  {
      overallRoot = null;
      if (input.hasNext()){
         overallRoot = loadTree(input);       
      }
      else throw new NullPointerException();
  }
  

  private QuestionNode loadTree(Scanner input){
      String line = input.nextLine();
      
      //split the sentence to seperate the "Q:" or "A:"
      String[] lineSplit = line.split(":");
         
      QuestionNode current = new QuestionNode(lineSplit[1]);
         
        /* 
         //case 1: it is the first value from file
         if (overallRoot == null){
            current = new QuestionNode(lineSplit[1]);
         }
         */
         //case 2: there is already a start and it is a question:  
       if (lineSplit[0].equals("Q")){
         current.left = this.loadTree(input);
         current.right = this.loadTree(input);

        }
      return current;
      
   
  }  
  /**Returns total amount of games played*/
  public int totalGames()
  {
    return totalgames;
  }
  /**Returns total amount of games won*/
  public int gamesWon()
  {
    return gamewon;
  }
  
}