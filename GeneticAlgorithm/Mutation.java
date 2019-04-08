import java.util.*;

/**
 * This class is used to mutate the genes of the chromosomes.
 * This is done to avoid local convergence and to follow the
 * structure of a Genetic Algorithm.
 */
public class Mutation
{
    private static Random random = new Random();

    /**
     * Invokes the Mutate_Chromosome_Single_Alleles_Collection() function for
     * a single chromosome in the population.
     */
    public static ArrayList<Chromosome> Mutate_Random_Chromosome_In_Population(ArrayList<Chromosome> population, ArrayList<Order> orders, ArrayList<Restaurant> restaurants)
    {
        int random_chromosome_to_mutate = random.nextInt(Constants.POPULATION_SIZE);
        
        Chromosome chromosome = population.get(random_chromosome_to_mutate);
        Chromosome mutated_chromosome = Mutate_Chromosome_Single_Alleles_Collection(chromosome, orders, restaurants);

        population.set(random_chromosome_to_mutate, mutated_chromosome);
        return population;
    }

    /**
     * Simply invokes the Mutate_Chromosome_Single_Alleles_Collection() function for
     * every chromosome in the population.
     */
    public static ArrayList<Chromosome> Mutate_Population_Single_Alleles_Collection(ArrayList<Chromosome> population, ArrayList<Order> orders, ArrayList<Restaurant> restaurants)
    {
        for(Chromosome chromosome : population)
        {
            chromosome = Mutate_Chromosome_Single_Alleles_Collection(chromosome, orders, restaurants);
        }
        return population;
    }

    /**
     * Swaps two alleles for a single chromosome in the population randomly!
     */
    public static ArrayList<Chromosome> Swap_2_Alleles_Randomly_From_Single_Chromosome_In_Population_X_Times(int generation, ArrayList<Chromosome> population, ArrayList<Order> orders, ArrayList<Restaurant> restaurants)
    {
        int X = Get_Number_Of_Mutations(generation);
        for(int a = 0; a < X; a++)
        {
            int random_chromosome_index = random.nextInt(Constants.POPULATION_SIZE);
            Chromosome random_chromosome = population.get(random_chromosome_index);
    
            int random_gene_1_index = random.nextInt(random_chromosome.Get_Drones().size());
            Drone random_gene_1 = random_chromosome.Get_Drone(random_gene_1_index);
            int random_alleles_collection_1_index = random.nextInt(random_gene_1.Get_Restaurants_And_Orders().size());
            RestaurantOrders random_alleles_collection_1 = random_gene_1.Get_Restaurants_And_Orders().get(random_alleles_collection_1_index);
            int random_allele_order_1_index = random.nextInt(random_alleles_collection_1.Get_Orders_Size());
            int random_allele_order_1_ID = random_alleles_collection_1.Get_Orders().get(random_allele_order_1_index).Get_ID();
            int random_allele_restaurant_1_ID = random_alleles_collection_1.Get_Restaurant().Get_ID();
    
            int random_gene_2_index = random.nextInt(random_chromosome.Get_Drones().size());
            Drone random_gene_2 = random_chromosome.Get_Drone(random_gene_2_index);
            int random_alleles_collection_2_index = random.nextInt(random_gene_2.Get_Restaurants_And_Orders().size());
            RestaurantOrders random_alleles_collection_2 = random_gene_2.Get_Restaurants_And_Orders().get(random_alleles_collection_2_index);
            int random_allele_order_2_index = random.nextInt(random_alleles_collection_2.Get_Orders_Size());
            int random_allele_order_2_ID = random_alleles_collection_2.Get_Orders().get(random_allele_order_2_index).Get_ID();
            int random_allele_restaurant_2_ID = random_alleles_collection_2.Get_Restaurant().Get_ID();
    
            int total_number_of_alleles = Constants.NUMBER_OF_ORDERS + Constants.NUMBER_OF_RESTAURANTS;
            if(random.nextInt(total_number_of_alleles) < Constants.NUMBER_OF_RESTAURANTS)
            {
                random_alleles_collection_1.Set_Restaurant(restaurants.get(random_allele_restaurant_2_ID));
                random_alleles_collection_2.Set_Restaurant(restaurants.get(random_allele_restaurant_1_ID));
            }
            else
            {
                random_alleles_collection_1.Get_Orders().set(random_allele_order_1_index, orders.get(random_allele_order_2_ID));
                random_alleles_collection_2.Get_Orders().set(random_allele_order_2_index, orders.get(random_allele_order_1_ID));
            }
        }
        return population;
    }

    /**
     * Exponentional decay function for calculating number
     * of mutations to make for every generation.
     */
    private static int Get_Number_Of_Mutations(int generation)
    {
        double number_of_orders = Constants.NUMBER_OF_ORDERS;
        double number_of_restaurants = Constants.NUMBER_OF_RESTAURANTS;
        double grid_size = Constants.GRID_SIZE;
        double number_of_runs = Constants.NUMBER_OF_RUNS;

        double multiplier = (number_of_orders + number_of_restaurants);
        double decay_ratio = (1 / multiplier);

        double double_nr_mutations = multiplier * Math.pow(1.0 - decay_ratio, generation);
        int int_nr_mutations = (int)double_nr_mutations;
        return int_nr_mutations + 1;
    }

    /**
     * Completely mutates the worse half of the chromosomes in the population list.
     */
    public static ArrayList<Chromosome> Mutate_Worst_Parent_Chromosome_Completely(ArrayList<Chromosome> population, ArrayList<Order> orders, ArrayList<Restaurant> restaurants)
    {
        int worse_parent_chromosome_index = (Constants.POPULATION_SIZE / 2) - 1;
        Chromosome mutated_chromosome = InitializePopulation.Initialize_Population_Helper(orders, restaurants);
        population.set(worse_parent_chromosome_index, mutated_chromosome);
        return population;
    }

    /**
     * Completely mutates the worse half of the chromosomes in the population list.
     */
    public static ArrayList<Chromosome> Mutate_Worse_Half_In_Population_Completely(ArrayList<Chromosome> population, ArrayList<Order> orders, ArrayList<Restaurant> restaurants)
    {
        for(int a = Constants.POPULATION_SIZE / 2; a < Constants.POPULATION_SIZE; a++)
        {
            Chromosome mutated_chromosome = InitializePopulation.Initialize_Population_Helper(orders, restaurants);
            population.set(a, mutated_chromosome);
        }
        return population;
    }

    /**
     * Simply invokes the Mutate_Chromosome_All_Alleles_Collection() function for
     * every chromosome in the population.
     */
    public static ArrayList<Chromosome> Mutate_Population_All_Alleles_Collection(ArrayList<Chromosome> population, ArrayList<Order> orders, ArrayList<Restaurant> restaurants)
    {
        for(Chromosome chromosome : population)
        {
            chromosome = Mutate_Chromosome_All_Alleles_Collection(chromosome, orders, restaurants);
        }
        return population;
    }

    /**
     * The functions starts with picking a random gene to mutate in a single chromosome.
     * Then a "single collection of alleles" is chosen randomly from the gene and is then
     * mutated. The restaurant allele mutation simply occurs by reassigning the restaurant
     * where the orders are picked up randomly. The "single collection" of order alleles
     * are mutated by simply shuffling the list. This changes the pathway of the deliveries
     * and is done to avoid local convergence.
     */
    private static Chromosome Mutate_Chromosome_Single_Alleles_Collection(Chromosome chromosome, ArrayList<Order> orders, ArrayList<Restaurant> restaurants)
    {
        int random_gene_mutation_index = random.nextInt(Constants.NUMBER_OF_DRONES);
        Drone random_gene_to_mutate = chromosome.Get_Drone(random_gene_mutation_index);

        int alleles_collection_size = random_gene_to_mutate.Get_Restaurants_And_Orders().size();
        int random_alleles_collection_index = random.nextInt(alleles_collection_size);
        RestaurantOrders random_alleles_collection_to_mutate = random_gene_to_mutate.Get_Restaurants_And_Orders().get(random_alleles_collection_index);

        //Mutating single restaurant allele
        int random_allele_restaurant_mutation_index = random.nextInt(Constants.NUMBER_OF_RESTAURANTS);
        Restaurant random_allele_restaurant_mutated = restaurants.get(random_allele_restaurant_mutation_index);
        random_alleles_collection_to_mutate.Set_Restaurant(random_allele_restaurant_mutated);

        //Mutating single collection of order for the corresponding restaurant.
        Collections.shuffle(random_alleles_collection_to_mutate.Get_Orders());

        return chromosome;
    }

    /**
     * The functions starts with picking a random gene to mutate in a single chromosome.
     * Then, "All collection of alleles" of randomly selected the gene is then mutated.
     * The restaurant alleles mutation simply occurs by reassigning the restaurant where
     * the orders are picked up randomly. The order alleles are mutated by simply shuffling
     * "every orders list" for the gene. This changes the pathway of the deliveries and is done to avoid local convergence.
     */
    private static Chromosome Mutate_Chromosome_All_Alleles_Collection(Chromosome chromosome, ArrayList<Order> orders, ArrayList<Restaurant> restaurants)
    {
        int random_gene_mutation_index = random.nextInt(Constants.NUMBER_OF_DRONES);
        Drone random_gene_to_mutate = chromosome.Get_Drone(random_gene_mutation_index);

        for(RestaurantOrders alleles_collection: random_gene_to_mutate.Get_Restaurants_And_Orders())
        {
            //Mutating the restaurant alleles
            int random_allele_restaurant_mutation_index = random.nextInt(Constants.NUMBER_OF_RESTAURANTS);
            Restaurant random_allele_restaurant_mutated = restaurants.get(random_allele_restaurant_mutation_index);
            alleles_collection.Set_Restaurant(random_allele_restaurant_mutated);

            //Mutating all the orders corresponding to the restaurant.
            Collections.shuffle(alleles_collection.Get_Orders());
        }
        return chromosome;
    }
}