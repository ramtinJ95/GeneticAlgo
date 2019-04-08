import java.util.*;

public class Mating
{
    /**
     * Simply invokes a call to a mating function.
     */
    public static ArrayList<Chromosome> Mate_And_Get_Next_Generation(ArrayList<Chromosome> population_mating_pool)
    {
        return Mate_Mid_Point_Crossover(population_mating_pool);
    }

    /**
     * Mates parent 1 with parent 2, parent 2 with parent 3 and so on.
     * The chromosome returned by mating is the child chromosome.
     * Lastly we also save the best chromosome without mating.
     */
    private static ArrayList<Chromosome> Mate_Mid_Point_Crossover(ArrayList<Chromosome> population_mating_pool)
    {
        int nr_of_child_chromosomes_needed = Constants.POPULATION_SIZE;
        ArrayList<Chromosome> population_next_generation = new ArrayList<>();

        //top half chromosomes
        for(int a = 0; a < Constants.POPULATION_SIZE / 2; a++)
        {
            Chromosome chromosome = population_mating_pool.get(a);
            population_next_generation.add(chromosome);
            nr_of_child_chromosomes_needed--;
        }

        for(int a = 0; a < nr_of_child_chromosomes_needed; a++)
        {
            Chromosome parent1 = population_mating_pool.get(a);
            Chromosome parent2 = population_mating_pool.get(a + 1);
            Chromosome child = Mate_Mid_Point_Crossover(parent1, parent2);
            population_next_generation.add(child);
        }

        return population_next_generation;
    }

    /**
     * Combines parent_1_chromosome with parent_2_chromosome. More specifically
     * the best genes in each parent chromosome are combined by dividing
     * each chromosome in half and then combining the better genes.
     */
    private static Chromosome Mate_Mid_Point_Crossover(Chromosome parent_1_chromosome, Chromosome parent_2_chromosome)
    {
        Polymerase child_polymerase = new Polymerase();

        int number_of_alleles_to_add_from_parent_1 = (Constants.NUMBER_OF_ORDERS / 2) + (Constants.NUMBER_OF_ORDERS % 2);
        child_polymerase.Set_Parent_Parameters(number_of_alleles_to_add_from_parent_1);
        child_polymerase = Merge_Alleles_To_Child_Without_Duplicates(parent_1_chromosome, child_polymerase);

        int number_of_alleles_to_add_from_parent_2 = Constants.NUMBER_OF_ORDERS / 2;
        child_polymerase.Set_Parent_Parameters(number_of_alleles_to_add_from_parent_2);
        child_polymerase = Merge_Alleles_To_Child_Without_Duplicates(parent_2_chromosome, child_polymerase);
        
        /**
         * The TWO rows below MUST be invoked after the second parent has added all of it's
         * alleles. This because, the very last genes from the second parent might not have
         * been added by the polymerase and to initialize the drone positions.
         * (See the Polymerase Class and the InitializePopulation Class for more information)
         */
        child_polymerase.Add_Remaining_Genes();
        child_polymerase.Set_Child_Chromosome(InitializePopulation.Assign_Drones_Initial_Positions(child_polymerase.Get_Child_Chromosome()));
        return child_polymerase.Get_Child_Chromosome();
    }

    /**
     * Simply iterators through all the parent alleles and send the best alleles
     * of the parent to the child_polymerase which is building the child_chromosome.
     * If all INTENDED alleles from a single parent have been added the loops break.
     * and the child polymerase is returned which the next parent MUST build opon.
     * More specifically the newly returned child_polymerase is sended into this
     * function again now with the second parent chromosomes instead of creating
     * a new child_polymerase which will completely break the running algorithm!
     * 
     */
    private static Polymerase Merge_Alleles_To_Child_Without_Duplicates(Chromosome parent_chromosome, Polymerase child_polymerase)
    {
        ArrayList<RestaurantOrders> parent_alleles_collection = Get_Collection_Of_Alleles(parent_chromosome);

        for(RestaurantOrders alleles : parent_alleles_collection)
        {
            Restaurant allele_restaurant = alleles.Get_Restaurant();
            for(Order allele_order : alleles.Get_Orders())
            {
                child_polymerase.Add_Allele(allele_restaurant);
                child_polymerase.Add_Allele(allele_order);
                if(child_polymerase.Parent_Alleles_Added()) break;
            }
            if(child_polymerase.Parent_Alleles_Added()) break;
        }
        return child_polymerase;
    }

    /**
     * Simply iterators through all alleles that the chromosome contains
     * and returns an ArrayList which is a "collection" of alleles.
     */
    private static ArrayList<RestaurantOrders> Get_Collection_Of_Alleles(Chromosome chromosome)
    {
        ArrayList<RestaurantOrders> collection_of_alleles = new ArrayList<>();
        for(Drone gene : chromosome.Get_Drones())
        {
            for(RestaurantOrders alleles : gene.Get_Restaurants_And_Orders())
            {
                collection_of_alleles.add(alleles);
            }
        }
        return collection_of_alleles;
    }
}