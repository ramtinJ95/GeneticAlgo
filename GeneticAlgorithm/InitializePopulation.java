import java.util.*;

/**
 * This class is used to Initialize the population which
 * is required for the Genetic Algorithm to work in order
 * to make mutations, calculate the fitness score etc.
 */
public class InitializePopulation
{
    private static Random random = new Random();

    /**
     * Initializes the population.
     */
    public static ArrayList<Chromosome> Initialize_Population(ArrayList<Order> orders, ArrayList<Restaurant> restaurants)
    {
        ArrayList<Chromosome> population = new ArrayList<>();
        for(int a = 0; a < Constants.POPULATION_SIZE; a++)
        {
            Chromosome chromosome = Initialize_Population_Helper(orders, restaurants);
            population.add(chromosome);
        }
        return population;
    }
    
    /**
     * Initializes population based on the number Constants.NUMBER_OF_DRONES.
     * Several chromosomes are created and the collection of these chromosomes
     * is called a population. A chromosomes is a collection of all the drones
     * since all drones give rise to a single solution based on their routes.
     * 
     * NOTE: A drone is always filled with orders to a maximum capacity defined
     * by the number Constants.DRONE_CAPACITY before moving on to the next drone.
     * Once all drones have been assigned the maximum number of orders and their
     * corresponding restaurant there might still be additional orders left. In this
     * case, the drone_iterator is reset and we create new RestaurantOrders object
     * and keep giving them to the drones in a cyclical fashion until all orders
     * have been assigned to the drones.
     * 
     * NOTE: For each RestaurantOrders object the drones have to first travel
     * to the restaurant, pickup the orders and then deliver them BEFORE
     * processing the next RestaurantOrders object!
     * 
     */
    public static Chromosome Initialize_Population_Helper(ArrayList<Order> orders, ArrayList<Restaurant> restaurants)
    {
        ArrayList<Integer> indexes_of_orders = Randomize_Indexes(orders.size());
        Chromosome chromosome = new Chromosome(Constants.NUMBER_OF_DRONES);
        
        int order_iterator = 0;
        int drone_iterator = 0;
        while(order_iterator < orders.size())
        {
            RestaurantOrders restaurantOrders = Get_RestaurantOrders_For_Drone(order_iterator, orders, indexes_of_orders, restaurants);
            Drone drone = chromosome.Get_Drone(drone_iterator);
            
            drone.Add_RestaurantOrders(restaurantOrders);
            chromosome.Set_Drone(drone_iterator, drone);

            order_iterator += restaurantOrders.Get_Orders_Size();
            drone_iterator += 1;
            if(drone_iterator >= Constants.NUMBER_OF_DRONES) drone_iterator = 0;
        }
        chromosome = Assign_Drones_Initial_Positions(chromosome);
        return chromosome;
    }

    /**
     * Assigns each drone a starting position. The drones are assigned
     * their starting position equivalent to the restaurant position for
     * their first order(s). This way every drone will have a distance equal
     * to 0 for picking upp their "first" orders from the "first" restaurant.
     */
    public static Chromosome Assign_Drones_Initial_Positions(Chromosome chromosome)
    {
        for(Drone drone: chromosome.Get_Drones())
        {
            Restaurant restaurant = drone.Get_Restaurants_And_Orders().get(0).Get_Restaurant();
            drone.Set_X_Pos(restaurant.Get_X_Pos());
            drone.Set_Y_Pos(restaurant.Get_Y_Pos());
        }
        return chromosome;
    }

    /**
     * Returns a RestaurantOrders object with "random order(s)" and
     * the "corresponding random restaurant" assigned.
     */
    private static RestaurantOrders Get_RestaurantOrders_For_Drone(int order_iterator, ArrayList<Order> orders, ArrayList<Integer> indexes_of_orders, ArrayList<Restaurant> restaurants)
    {
        RestaurantOrders restaurantOrders = new RestaurantOrders();
        restaurantOrders = Get_Orders_For_Drone(order_iterator, orders, indexes_of_orders, restaurantOrders);
        restaurantOrders = Get_Restaurant_For_Drone(restaurants, restaurantOrders);
        return restaurantOrders;
    }

    /**
     * Returns a RestaurantOrders object with a "random order(s)"
     * assigned. The RestaurantOrders object is later assigned to a drone so
     * that the drone where it needs to deliver the order(s).
     * 
     * NOTE: A drone is always filled with orders to a maximum capacity defined
     * by the number Constants.DRONE_CAPACITY.
     */
    private static RestaurantOrders Get_Orders_For_Drone(int order_iterator, ArrayList<Order> orders, ArrayList<Integer> indexes_of_orders, RestaurantOrders restaurantOrders)
    {
        int drone_capacity_iterator = 0;
        for(int a = order_iterator; drone_capacity_iterator < Constants.DRONE_CAPACITY && a < Constants.NUMBER_OF_ORDERS; a++)
        {
            int order_index = indexes_of_orders.get(a);
            Order order = orders.get(order_index);
            restaurantOrders.Add_Order(order);
            drone_capacity_iterator++;
        }
        return restaurantOrders;
    }

    /**
     * Returns a RestaurantOrders object with a "random restaurant location"
     * assigned. The RestaurantOrders object is later assigned to a drone so
     * that the drone knows where to pickup the "corresponding" orders.
     */
    private static RestaurantOrders Get_Restaurant_For_Drone(ArrayList<Restaurant> restaurants, RestaurantOrders restaurantOrders)
    {
        int restaurant_index = random.nextInt(restaurants.size());
        Restaurant restaurant = restaurants.get(restaurant_index);
        restaurantOrders.Set_Restaurant(restaurant);
        return restaurantOrders;
    }

    /**
     * Fills an arraylist with indexes based on the parameter "size"
     * and then shuffles the arraylist. This method is used for randomizing
     * the indexes of orders.
     */
    private static ArrayList<Integer> Randomize_Indexes(int size)
    {
        ArrayList<Integer> randomized_indexes = new ArrayList<>();
        for(int a = 0; a < size; a++) randomized_indexes.add(a);
        Collections.shuffle(randomized_indexes);
        return randomized_indexes;
    }
}