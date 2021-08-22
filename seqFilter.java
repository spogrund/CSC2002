import java.util.*;
import java.io.*;
public class seqFilter{

   public static void main(String[] args){
      String inFileName = args[0];
      int filterSize = Integer.parseInt(args[1]);
      String outFileName = args[2];
      //Filter[] filter = new Filter[5];
      float[] incFilter = new float[filterSize];
      float[] output = new float[100];
      float[] inArr = new float[100];
      int arrSize =0;
      try{
         File file = new File(inFileName);
         Scanner scf  = new Scanner(file);
         arrSize = Integer.parseInt(scf.nextLine());
         //filter = new Filter[arrSize];
         inArr = new float[arrSize+2];
         output = new float[arrSize];
         int i =2;
         String[] input = scf.nextLine().split(" ");
         inArr[0] = Float.parseFloat(input[1]);
         inArr[1] = Float.parseFloat(input[1]);
         while(scf.hasNextLine()){
            input = scf.nextLine().split(" ");
            inArr[i] = Float.parseFloat(input[1]);
            i++;
         }
         inArr[i] = Float.parseFloat(input[1]);
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
            float[] subArray = Arrays.copyOfRange(inArr, i-median, i+median+1);
            Arrays.sort(subArray);
            float medianNum = subArray[(filterSize-1)/2];
            output[i] = medianNum;

         }
          
      }
      long endTime = System.nanoTime();
      System.out.println(Arrays.toString(output));
      System.out.println(String.valueOf(endTime-startTime));
   
   }

}