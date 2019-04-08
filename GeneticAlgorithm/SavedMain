import java.util.*;

public class Main
{
    /**
     * The main function. The entire program can be run by simply
     * typing "javac *.java" in the terminal and then simply
     * calling the main function by typing the command "java Main".
     */
    public static void main(String[] args)
    {
        if(Constants.Check_Constraints_On_Constants() == false) return;
        for(int i = 0; i < 25; i++){
          ArrayList<Order> orders_GA = GenerateData.Generate_Orders();
          ArrayList<Restaurant> restaurants_GA = GenerateData.Generate_Restaurants();

          GeneticAlgorithm.Run_Genetic_Algorithm(orders_GA, restaurants_GA);
     }
    }
}
