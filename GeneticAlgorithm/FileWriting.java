import java.util.*;
import java.io.*;
/**
 * This class is simply used to store the solution distances
 * to a file in order to use another technology for plotting.
 */
public class FileWriting{
    public static void Write_To_File(ArrayList<ArrayList<Double>> population_distances_for_all_generations){
      try{
      BufferedWriter br = new BufferedWriter(new FileWriter("GA_plot_file.csv"));
      StringBuilder sb = new StringBuilder();
        for(ArrayList<Double> arrayOfDoubles : population_distances_for_all_generations){
          for(Double fitness : arrayOfDoubles){
            sb.append(fitness);
            sb.append(",");
          }
          sb.append("\n");
        }

        br.write(sb.toString());
        br.close();
        }
        catch(Exception e){
        System.out.println(e);
      }
    }
}
