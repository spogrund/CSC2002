import java.util.*;
import java.io.*;
import java.util.concurrent.*;
public class pFilter extends RecursiveAction{
   
   double[] data;
   int lo;
   int hi;
   int filterSize;
   final static int SEQ_CUTOFF=500;
   double[] filtered;
   int median;
   
   public pFilter(double[] data, int filterSize){
      this.data = data;
      this.filterSize = filterSize;
      this.lo = 0;
      this.hi = data.length;
      this.filtered = new double[this.hi];
      this.median = (this.filterSize-1)/2;
      
   }
   public pFilter(double[] data, int filterSize, int lo, int hi, double[] filtered){
      this.data = data;
      this.filterSize = filterSize;
      this.lo = lo;
      this.hi = hi;
      this.filtered = filtered;
      this.median = (this.filterSize-1)/2;
   }
   protected void compute(){
      if ((hi-lo)< SEQ_CUTOFF){
         //System.out.println("here1");
         for(int i=lo; i<hi; i++){
            if(i<median || i >= data.length-median){
               filtered[i] = data[i];
            }
            else{
               double[] subArray = Arrays.copyOfRange(data, i-median, i+median+1);
               Arrays.sort(subArray);
               filtered[i] = subArray[median];
            }
            //System.out.println(Arrays.toString(filtered));
         }
      }
      else{
         pFilter pRight = new pFilter(data,filterSize,(hi+lo)/2,hi,filtered);
         pFilter pLeft = new pFilter(data,filterSize,lo,(hi+lo)/2, filtered);
         pLeft.fork();
         pRight.compute();
         pLeft.join();
         
      }
   }
   public double[] getRes(){
      return filtered;
   }
   
   
   public static void main(String[] args){
      String inFileName = args[0];
      int fSize = Integer.parseInt(args[1]);
      String outFileName = args[2];
      double[] inArr = new double[1];
      
      try{
         File f = new File(inFileName);
         Scanner scf = new Scanner(f);
         int arrSize = Integer.parseInt(scf.nextLine());
         int i = 0;
         inArr = new double[arrSize];
         while(scf.hasNextLine()){
            String[] input = scf.nextLine().split(" ");
            inArr[i] = Double.parseDouble(input[1]);
            i++ ;
         }
      }
      catch(FileNotFoundException e){
         System.out.println("file not found");
      }
      //System.out.println(Arrays.toString(inArr));
      long start = System.nanoTime();
      ForkJoinPool pool = new ForkJoinPool();
      pFilter pmf = new pFilter(inArr,fSize);
    // pmf.setResult();
      pool.invoke(pmf);
      double[] outArr = pmf.getRes();
      long end = System.nanoTime();
      System.out.println(Arrays.toString(outArr));
      System.out.println(end-start);
      
      try{
         FileWriter fwriter = new FileWriter(outFileName);
         FileWriter fwriter2 = new FileWriter("Parallel " + Integer.toString(outArr.length)+" " +Integer.toString(fSize) + " " +Integer.toString(SEQ_CUTOFF)+" time.txt",true);
         for (int i=0; i< outArr.length; i++){
            fwriter.write(Double.toString(outArr[i])+"\n");
         }
         fwriter2.write(end-start + "\n");
         fwriter.close();
         fwriter2.close();
      }
      catch(IOException e){
         System.out.println("an error occured");
      }
   }
  

}