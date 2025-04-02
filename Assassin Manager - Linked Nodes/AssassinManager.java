/**
   This program manages a game of Assassin. All players are given a target and the last player surviving wins. 
   See AssassinNode and AssassinMain
   @author Danielle Idzi CS123
   @version v1
   1/22/2024

*/

import java.util.*;

public class AssassinManager {
   
    /**Node of all players and targets*/
    private AssassinNode killring;
    /**Node of players who died and who killed them*/
    private AssassinNode graveyard;
    
    
    /**Main constructor that sets up killring with all the imported names
    @param List of names for players
    @throws IllegalArgumentException if there are no names on list 
    */  
    public AssassinManager (List<String> names) {
      //add names to kill ring
      if (names.isEmpty()) throw new IllegalArgumentException();
      if (killring == null) killring = new AssassinNode (names.get(0));

      for (int i = 1; i< names.size(); i++){
        AssassinNode newNode = new AssassinNode(names.get(i));
        AssassinNode current = killring;
        while (current.next != null){
          current = current.next;
        }
       current.next = newNode;
      }
      //is done automatically, i've added it as a reminder and to help me visualize
      graveyard = null;
    
    }

    /**Void that prints out the Killring, and who is stalking who*/
    public void printKillRing() {
      AssassinNode current = killring;
      while (current.next != null){
          System.out.println("    "+ current.name + " is stalking " + current.next.name);
          current = current.next;
        }
       //will exit while loop once current.next = null, then need to still print that last pair
      System.out.println("    " + current.name + " is stalking " + killring.name);         
    }
 
    /**Void that prints out the graveyard, containing the victim and killer*/   
    public void printGraveyard() {
      AssassinNode current = graveyard;
      
      while(current != null){
         System.out.println("    "+ current.name + " was killed by " + current.killer);
         current = current.next; 
      }      
    }
 
    /**Check the kill ring for a specific player
      @param name of player who you want to check on
      @return true if player is still alive and in the game, false otherwise
    */   
    public boolean killRingContains (String name) {
        AssassinNode current = killring;
        while(current != null){
         if (current.name.equalsIgnoreCase(name)) return true;         
         current = current.next;
      }
      return false;
    }
 
    /**Check the graveyard for a specific player
      @param name of player who you want to check on
      @return true if player is dead, false if they are still in the game
    */   
    public boolean graveyardContains (String name) {
      AssassinNode current = graveyard;
      while(current != null){
         if (current.name.equalsIgnoreCase(name)) return true;         
         current = current.next;
      }
      return false;
    }
 
    /**Checks if there is one player left alive
    @return true if there is one player alive left, otherwise false
    */  
    public boolean gameOver() {
      if (killring.next == null) return true;
      else return false;
    }

    /**Returns the name of the winner
      @return name (string) of player who survived
      @throws IllegalArgumentException if the game isn't over yet
    */   
    public String winner () {
      if (gameOver()){
         return killring.name;
      
      }
      //if the game isn't over
      else throw new IllegalArgumentException();
      
    }

    /**Kill takes a player name and removes them from the killring, and adds them to the graveyard. 
      Once someone dies all nodes are moved so that everyone has a victim still.  
      @param name of player who has been killed
      @throws IllegalArgumentException if player named isn't alive or in the kill ring
      
    */    
    public void kill (String name) { 
      AssassinNode current = killring;
      AssassinNode temp;
      //check to make sure name is inside the killring
      if (!killRingContains(name)) throw new IllegalArgumentException();
      
      //if the first node was killed:
      if (current.name.equalsIgnoreCase(name)){
         //if the first node is killed than the last node was the killer
         while (current.next != null){
            current = current.next;
         }
      }
      else{
         //if the killer was in the middle
         while (!current.next.name.equalsIgnoreCase(name)){
            current = current.next;
         }
      } 
      temp = current;
      
      
      //if the victim was the first node, and killer was last node
      if (current.next == null){
         killring = killring.next;   
      }
      //if the victim is the last node then just errase it
      else if (current.next.next == null) {
         current.next = null;
      } 
      //if there are more people after the victim then redirect kill ring node
      else {
         current.next = current.next.next;
      }
      
      if (graveyard == null){
         graveyard = new AssassinNode(name);
         graveyard.killer = temp.name;
      }
      
      else {
         AssassinNode tempgraveyard = graveyard;
         tempgraveyard = new AssassinNode(name);
         tempgraveyard.killer = temp.name; 
         
         tempgraveyard.next = graveyard;
         graveyard = tempgraveyard;  
         
         
      }
      
    }
}
