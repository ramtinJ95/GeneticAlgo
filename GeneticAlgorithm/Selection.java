import java.util.*;

public class Selection
{
    /**
     * Picks the best drones based on their fitness score and returns
     * a sub-list of the original drone list with the best drones.
     */
    public static ArrayList<Chromosome> Select_Mating_Pool(ArrayList<Chromosome> population)
    {
        for(Chromosome chromosome : population)
        {
            chromosome = Sort_Genes_Descending_Order(chromosome);
            chromosome = Roulette_Wheel_Selection_Genes(chromosome);
        }
        population = Sort_Chromosomes_Descending_Order(population);
        population = Roulette_Wheel_Selection_Chromosomes(population);

        ArrayList<Chromosome> population_mating_pool = population;
        return population_mating_pool;
    }

    /**
     * This function recalculates the score of the chromosomes so that the
     * SCORES OF THE CHROMOSOMES are on a relative scale rather than an absolute
     * scale. Furthermore, the scores are multiplied with 100 to make the scale
     * interval (0-100) instead of (0-1) in order to avoid underflow problems.
     */
    private static ArrayList<Chromosome> Roulette_Wheel_Selection_Chromosomes(ArrayList<Chromosome> population)
    {
        double sum_of_scores = 0.0;
        for(Chromosome chromosome : population) sum_of_scores += chromosome.Get_Fitness();
        for(Chromosome chromosome : population) chromosome.Set_Fitness(100 * (chromosome.Get_Fitness() / sum_of_scores));
        return population;
    }

    /**
     * This function recalculates the score of the drones/genes so that the
     * SCORES OF THE GENES are on a relative scale rather than an absolute scale.
     * Furthermore, the scores are multiplied with 100 to make the scale
     * interval (0-100) instead of (0-1) in order to avoid underflow problems.
     */
    private static Chromosome Roulette_Wheel_Selection_Genes(Chromosome chromosome)
    {
        for(Drone drone : chromosome.Get_Drones())
        {
            drone.Set_Fitness(100 * (drone.Get_Fitness() / chromosome.Get_Fitness()));
        }
        return chromosome;
    }

    /**
     * Sorts the chromosomes based on their fitness score in a ascending order.
     * Uses the Collections.sort() method which relies on the compareTo()
     * function of the Chromosome class.
     */
    private static ArrayList<Chromosome> Sort_Chromosomes_Ascending_Order(ArrayList<Chromosome> population)
    {
        Collections.sort(population);
        return population;
    }

    /**
     * Sorts the drones/genes based on their fitness score in a ascending order.
     * Uses the Collections.sort() method which relies on the compareTo()
     * function of the Drone class.
     */
    private static Chromosome Sort_Genes_Ascending_Order(Chromosome chromosome)
    {
        Collections.sort(chromosome.Get_Drones());
        return chromosome;
    }

    /**
     * Sorts the chromosomes based on their fitness score in a descending order.
     * Uses the Collections.sort() method which relies on the compareTo()
     * function of the Chromosome class.
     */
    private static ArrayList<Chromosome> Sort_Chromosomes_Descending_Order(ArrayList<Chromosome> population)
    {
        Collections.sort(population, Collections.reverseOrder());
        return population;
    }

    /**
     * Sorts the drones/genes based on their fitness score in a descending order.
     * Uses the Collections.sort() method which relies on the compareTo()
     * function of the Drone class.
     */
    private static Chromosome Sort_Genes_Descending_Order(Chromosome chromosome)
    {
        Collections.sort(chromosome.Get_Drones(), Collections.reverseOrder());
        return chromosome;
    }
}