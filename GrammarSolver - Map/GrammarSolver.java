/**
   CS 123 Grammar Solver Assignment 
   @author Danielle Idzi
   @version 1
   Completion date 2/18/24
*/

import java.util.*;

public class GrammarSolver{
   /**Map to store all BNF grammar rules */
   private Map <String, String[]> map;
   /**String to hold map BNF grammar rules after its been split between Terminal and non terminal*/
   public String[] list = null;
   /**Initializing radomizer for later use*/
   Random rand = new Random();

   /**Constructor to set up Tree map and strings
      @param All the grammar rules and words to form sentences
      @throws IllegalArugmentException if a word from the inputed list is already in map. 
   */
   public GrammarSolver(List<String> rules){
      //check to make sure list isn't empty or null
      if (rules == null || rules.isEmpty()) throw new IllegalArgumentException();
      
      //create a map could also change to hash map = non ordered tree map is ordered typically
      map = new TreeMap <>();
      //split up strings until the base case
      for (String line : rules){
         String [] parts = line.split ("::=");
         String [] rightRules = parts[1].split("\\|");
         
         //before putting in map must check to make sure it's not already in there
         if (map.containsKey(parts[0])) throw new IllegalArgumentException();
         map.put (parts[0], rightRules);
      }
   
   }
   
   /**Boolean Contains checks whether or not a symbol is in the map
      @param symbol desired to check
      @returns true if the inputed symbol is in the map, false if it is not in the map
      @throws IllegalArgumentException if the symbol is null or 0 characters long
    */
   public boolean contains (String symbol){
      //if non-terminal then return true else false
      // if ::= is infront than it is nonterminal 
      //all keys = non terminal 
      // termal = base case - "the" "a" "big" "fat"
      if (symbol == null || symbol.isEmpty()) throw new IllegalArgumentException();
      return map.containsKey(symbol);
   }
   
   /** getSymbols gets the key set of all main grammar rules
      @returns set of keys contained in the map
   */
   public Set<String> getSymbols(){
      return map.keySet();
   }
   
   /**generates a sentence based on the selected grammar rule
      @param takes the symbol that represents which grammar key to use
      @throws IllegalArgumentException if symbol inputed is null
      @returns string of the final sentence following the origninal grammar rule
   */
   public String generate(String symbol){
       String output = "";

       if (symbol == null || symbol.length() == 0) throw new IllegalArgumentException();
       if (!contains(symbol)) return symbol;
       String temp = "";
       String [] split = null;
       
       //gets all the values under the inputed key - turns them into an array
       list = map.get(symbol);
       //the array is already divided by the pips (|) so if there are multiple pipes choose 1 word
       //takes that array and randomly chooses 1 and save as a string
       // if there is only one value in the array it will just remain the same
       temp = list[rand.nextInt(list.length)];

       //split up the string based on spaces " " 
       split = temp.split(" "); 
       
       if (split != null){
         for (int i = 0; i < split.length; i++){ 
            //recurrsive part that keeps splitting the string until <...> (rule) turns into words
            if (this.contains(split[i])){
               output = output + generate(split[i]);
             }
            //base case
            else {
               output = output +" "+ split[i];
            }
          }
       }

      return output;
   }

}