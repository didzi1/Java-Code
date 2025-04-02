public class QuestionNode {
   public String data; 
   public QuestionNode left;
   public QuestionNode right;
   
   // construct a leaf node with given data
   public QuestionNode (String data) {
      this (data, null, null);
   }
   
   // construct a branch node with given data. left tree and right tree
   public QuestionNode (String data, QuestionNode left, QuestionNode right) {
      this.data = data;
      this.left = left;
      this.right = right;
   }
   
   
   
   
   
   
   
   
   
   
}