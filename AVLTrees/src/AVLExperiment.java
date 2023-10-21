import java.io.*;
import java.util.*;
/**Class that reads from vaccinations.csv, randomizes the data to a user specified degree and 
   stores it in an ArrayList, and inserts each element into an AVL Tree before searching for
   each element in this AVLTree. In each case of inserting and searching, the respective 
   comparison operations are counted to find the min, max and average comparisons for
   these respective operations.
   15/03/2022
   @author Taahir Suleman
*/
public class AVLExperiment{
   AVLTree<Vaccine> avl = new AVLTree<Vaccine>();
   ArrayList<Vaccine> vArr = new ArrayList<Vaccine>();
   int count = 0;
   int iMin = 10000000;
   int iMax = 0;
   int fMin = 10000000;
   int fMax = 0;
   int sumI = 0;
   int sumF = 0;   
   
   /**Method used to extract the data from vaccinations.csv and populate an ArrayList of
      Vaccine objects with this data (vArr).
   */
   public void read(){
      try{
         Scanner sc = new Scanner(new FileReader("data/vaccinations.csv"));
         while(sc.hasNext()){
            String line = sc.nextLine();
            vArr.add(new Vaccine(line));
            count++;
         }
         
      }
      catch(FileNotFoundException e){
         System.out.println("Error - file was not found\n" + e);
      }
   }
   
   /**Creates and returns an ArrayList of Integers containing a random sequence of numbers from
      0 to 9918, to be used in the randomization of the vArr ArrayList. This is done by first 
      populating an ArrayList of Integers with the ordered sequence of numbers, and then 
      using the shuffle() method from the Collections package to randomize this sequence.
      @return ArrayList of Integers containing a random sequence of numbers from 0 to 9918.
   */
   public ArrayList<Integer> randList(){
      Random rand = new Random();
      ArrayList<Integer> rnd = new ArrayList<Integer>();
      for(int i = 0; i < 9919; i++){
         rnd.add(i);
      }
      Collections.shuffle(rnd);
      return rnd;
   }
   
   /**Used to create a randomized version of the vArr ArrayList (to a user-specified degree of
      randomness) before assigning vArr to this version. This is achieved by firstusing the 
      randList() method specified above to create a random set of indices to the size of the 
      vArr ArrayList, and then add the elements at the first X (user-specified) indices from 
      the random list, removing each of these elements from the original vArr ArrayList. Lastly,
      the rest of the new ArrayList version is populated with the remaining elements in the 
      original vArr. At the end, the vArr variable is assigned to point to the new 
      randomized version.
      @param X number of elements from the randomized list of indices to be added to the start
            of the randomized version of vArr.
   */
   public void randomize(int X){
      ArrayList<Integer> rand = randList();
      ArrayList<Vaccine> temp = new ArrayList<Vaccine>();
      for(int i = 0; i < X; i++){
         temp.add(vArr.get(rand.get(i)));
         vArr.remove(rand.get(i));
         count--;
      } 
      for(int i = 0; i < count; i++){
         temp.add(vArr.get(i));
      }
      vArr = temp;
   }
   
   /**Compares the insert comparison operations integer parameter to the current min and max 
      insert comparison operations.
      @param op individual insert's operation counter to be compared against the current min
             and max.
   */
   public void checkIMinMax(int op){
      if(op < iMin)
         iMin = op;
      if (op > iMax)
         iMax = op;
   }
   
   /**Compares the find comparison operations integer parameter to the current min and max find 
      comparison operations.
      @param op individual find's operation counter to be compared against the current min
             and max.
   */
   public void checkFMinMax(int op){
      if(op < fMin)
         fMin = op;
      if (op > fMax)
         fMax = op;
   }
   
   /**Rests the insert and find operation counters back to 0 after its been added to the current
      respective cumulative operation counters and compared against their current respective
      min and max.
   */
   public void reset(){
      avl.iOpCount = 0;
      avl.fOpCount = 0;
   }
   
   /**inserts each Vaccine object from the randomized vArr ArrayList into an AVL tree of Vaccine
      objects. First, the read() method is used to extract the data from vaccines.csv and
      populate an ArrayList of Vaccine objects with it (vArr). Next, the randomize() method is
      called to randomize vArr to a specified extent. Last, each element in the randomized vArr
      is inserted into the AVL tree, with the comparisons being added to the sum of all insert 
      comparisons and then compared to the current min and max, before being reset to 0 at the
      end of each iteration.
      @param X number of elements from the randomized list of indices to be added to the start
             of the randomized version of vArr.
   */
   public void populate(int X){
      read();
      randomize(X);
      for(int i = 0; i < vArr.size(); i++){
         avl.insert(vArr.get(i));
         sumI += avl.iOpCount;
         checkIMinMax(avl.iOpCount);
         reset();
      }
   }
   
   /**Searches for each data item from the randomized vArr array in the AVLTree using its find
      methods. After each find, the respective comparison operation value is added to the sum of
      all the comparisons from each find operation, and compared to the current min and max
      before being reset to 0 at the end of each iteration.
   */
   public void find(){
      for(int i = 0; i < vArr.size(); i++){
         avl.find(vArr.get(i));
         sumF += avl.fOpCount;
         checkFMinMax(avl.fOpCount);
         reset();
      }      
   }
   /** Main method that is invoked when AVLExperiment is run to use the methods of this class to
       perform the experiment accordingly for a certain level of randomization X specified by
       args[0].
       @param args string array containing the supplied command-line parameters.
   */
   public static void main(String[] args){
      AVLExperiment av = new AVLExperiment();
      av.populate(Integer.parseInt(args[0]));
      av.find();
      int iAve = av.sumI/9919;
      int fAve = av.sumF/9919;
      System.out.println("(" + args[0] + "," + av.iMin + "," + av.iMax + "," + iAve + ")");
      System.out.println("(" + args[0] + "," +  av.fMin + "," + av.fMax + "," + fAve + ")");

      
   }    

}