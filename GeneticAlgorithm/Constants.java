import java.util.*;

/**
 * Several Constraints are presents due to the fact that multiple instances
 * are needed during selection, mating and mutations for the genetic algorithm.
 *
 * NOTE: The simulated annealing algorithm should be run with the same parameters
 * as the genetic. The parameters are defined below. This because it wouldn't
 * make sense to compare the distances if we don't have the same parameters!
 */
public class Constants
{
    public static final int DRONE_CAPACITY = 4;
    public static final int GRID_SIZE = 2000;
    public static final int NUMBER_OF_DRONES = 5;
    public static final int NUMBER_OF_ORDERS = 600;
    public static final int NUMBER_OF_RESTAURANTS = 20;
    public static final int POPULATION_SIZE = 18;
    public static final int NUMBER_OF_RUNS = 200000; 

    /**
     * This functions makes sure that the constraints are verified.
     * These constraints might not impose any restriction on the
     * Simulated Annealing Algorithm. However they are necessary for
     * the implementation of the Genetic Algorithm.
     */
    public static boolean Check_Constraints_On_Constants()
    {
        boolean constraints_passed = true;
        if(POPULATION_SIZE <= 1)
        {
            constraints_passed = false;
            System.err.println();
            System.err.println("Population Size <= 1");
            System.err.println("Constrant: Population Size > 1");
            System.err.println("Otherwise the Genetic Algorithm cannot be solved!");
            System.err.println();
        }
        if(DRONE_CAPACITY * NUMBER_OF_DRONES - DRONE_CAPACITY + 1 > NUMBER_OF_ORDERS)
        {
            constraints_passed = false;
            System.err.println();
            System.err.println("(DRONE_CAPACITY * NUMBER_OF_DRONES - DRONE_CAPACITY + 1) > NUMBER_OF_ORDERS");
            System.err.println("Constrant: (DRONE_CAPACITY * NUMBER_OF_DRONES - DRONE_CAPACITY + 1) <= NUMBER_OF_ORDERS");
            System.err.println("Otherwise the Genetic Algorithm cannot be solved!");
            System.err.println();
        }
        return constraints_passed;
    }
}
