import java.util.*;

/**
 * This class simply invokes other functions from other classes.
 * There is not much logic here and the names of the functions
 * and the variables makes the entire process rather self-explanatory.
 */
public class GeneticAlgorithm
{
    private static Random random = new Random();

    public static void Run_Genetic_Algorithm(ArrayList<Order> orders, ArrayList<Restaurant> restaurants)
    {
        ArrayList<ArrayList<Double>> population_distances_for_all_generations = new ArrayList<>();
        ArrayList<Chromosome> population = InitializePopulation.Initialize_Population(orders, restaurants);
        //Print_User_Info();

        /**
         * Run "one" of the "2" statements below, otherwise one will get lost.
         * Make sure that the same one is being run after the for-loop!
         */
        //Print_Generation_Population_Data(population, 1);
        //Print_Best_Chromosomes_Data_In_Generation_Population(population, 1);

        for(int a = 0; a < Constants.NUMBER_OF_RUNS; a++)
        {
            //Storing the solution distances
            ArrayList<Double> population_distances = Store_Solution_Distances(population);
            population_distances_for_all_generations.add(population_distances);

            //Fitness assigned population
            population = Fitness.Fitness_Function_Population(population);

            //The mating pool
            population = Selection.Select_Mating_Pool(population);

            //The next generation population
            population = Mating.Mate_And_Get_Next_Generation(population);

            //The next generation population with mutations imposed.
            population = Mutation.Swap_2_Alleles_Randomly_From_Single_Chromosome_In_Population_X_Times(a, population, orders, restaurants);

        }
        /**
         * Run "one" of the "three" statements below, otherwise one will get lost.
         * Make sure that the same one is being before the for-loop unless you pick
         * the Print_Solution_Distances() function, in which case none of the others
         * should run!
         */
        //Print_Solution_Distances(population_distances_for_all_generations);
        //Print_Generation_Population_Data(population, Constants.NUMBER_OF_RUNS);
        Double minSol = Double.MAX_VALUE;
        ArrayList<Double> ansMat =population_distances_for_all_generations.get(population_distances_for_all_generations.size()-1);
        for(Double sol: ansMat){
          if(sol < minSol){
            minSol = sol;
          }
        }
        System.out.print(minSol);
        System.out.print(", ");
        //Print_Best_Chromosomes_Data_In_Generation_Population(population, Constants.NUMBER_OF_RUNS);
    }

    /**
     * Prints order and restaurant data for given generation
     * population list. Furthermore each chromosome distance
     * is also printed.
     */
    private static void Print_Generation_Population_Data(ArrayList<Chromosome> population, int generation)
    {
        Chromosome.Solution_Verification_Population(population, generation);
    }

    /**
     * Prints order and restaurant data for the best chromosome
     * for the given generation population. Furthermore the best chromosome
     * distance is also printed.
     */
    private static void Print_Best_Chromosomes_Data_In_Generation_Population(ArrayList<Chromosome> population, int generation)
    {
        Chromosome.Solution_Verification_Chromosome(population.get(0), generation);
    }

    /**
     * Prints information to the user as a guidance.
     */
    private static void Print_User_Info()
    {
        System.err.println();
        System.err.println("The genetic algorithm is running, please wait!");
        System.err.println("Please see the ReadMe.txt file for additional information.");
        System.err.println();
    }

    /**
     * Simply stores the chromosome/solution distances for every
     * chromosome in the population. This is done for every generation
     * since the method is invoked from the Run_Genetic_Algorithm()
     * function.
     */
    private static ArrayList<Double> Store_Solution_Distances(ArrayList<Chromosome> population)
    {
        ArrayList<Double> population_distances = new ArrayList<>();
        for(Chromosome chromosome : population)
        {
            double solution_distance = Fitness.Get_Solution_Distance(chromosome);
            population_distances.add(solution_distance);
        }
        Collections.sort(population_distances);
        return population_distances;
    }

    /**
     * Prints the solutions distances for (every solution / chromosome) in a
     * population and this is done for every single generation! This method
     * can be used for a simple print of the solution distances to the terminal.
     */
    public static void Print_Solution_Distances(ArrayList<ArrayList<Double>> population_distances_for_all_generations)
    {
        for(ArrayList<Double> population_distances : population_distances_for_all_generations)
        {
            for(Double chromosome_solution_distance : population_distances)
            {
                System.err.println(chromosome_solution_distance);
            }
        }
    }
}
