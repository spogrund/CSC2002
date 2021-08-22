import java.util.*;
import java.io.*;
public class seqFilter{

   public static void main(String[] args){
      String inFileName = args[0];
      int filterSize = Integer.parseInt(args[1]);
      String outFileName = args[2];
      double[] output = new double[100];
      double[] inArr = new double[100];
      int arrSize =0;
      try{
         File file = new File(inFileName);
         Scanner scf  = new Scanner(file);
         arrSize = Integer.parseInt(scf.nextLine());
         //filter = new Filter[arrSize];
         inArr = new double[arrSize];
         output = new double[arrSize];
         int i =0;
         while(scf.hasNextLine()){
            String[] input = scf.nextLine().split(" ");
            inArr[i] = Float.parseFloat(input[1]);
            i++;
         }
         
         //System.out.println(Arrays.toString(inArr));
         
      }
      catch(FileNotFoundException e){
         System.out.println("File not found");
      }
      int median = (filterSize-1)/2;
      long startTime = System.nanoTime();
      for (int i =0; i< arrSize; i++){
         if(i<median || i >= arrSize-median){
            output[i]= inArr[i];
         }         
         else{
            double[] subArray = Arrays.copyOfRange(inArr, i-median, i+median+1);
            Arrays.sort(subArray);
            double medianNum = subArray[(filterSize-1)/2];
            output[i] = medianNum;

         }
          
      }
      long endTime = System.nanoTime();
      System.out.println(Arrays.toString(output));
      System.out.println(String.valueOf(endTime-startTime));
      try{
         FileWriter fwriter = new FileWriter(outFileName);
         FileWriter fwriter2 = new FileWriter("Sequential " + Integer.toString(output.length)+" " +Integer.toString(filterSize) +" time.txt",true);
         for (int i=0; i< output.length; i++){
            fwriter.write(Double.toString(output[i])+"\n");
         }
         fwriter2.write(endTime-startTime + "\n");
         fwriter.close();
         fwriter2.close();
      }
      catch(IOException e){
         System.out.println("an error occured");
      }

   
   }

}