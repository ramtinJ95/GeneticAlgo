import java.util.*;

public class Fitness
{
    /**
     * This function assigns "all drone" in "every chromosome" a
     * fitness score. See the function below for details on how
     * "each drone" is assigned a score.
     */
    public static ArrayList<Chromosome> Fitness_Function_Population(ArrayList<Chromosome> population)
    {
        for(Chromosome chromosome : population)
        {
            chromosome = Fitness_Function_Chromosome(chromosome);            
        }
        return population;
    }

    /**
     * This function assigns "all drones" / "all genes" a fitness score.
     * Furthermore, the chromosome is also assigned a fitness score which
     * represents the score for a single solution. The chromosome score
     * is simply calculated by taking the summing over the score of the genes.
     * See the function below for details on how "each drone" is assigned a score.
     */
    private static Chromosome Fitness_Function_Chromosome(Chromosome chromosome)
    {
        double fitness_chromosome = 0.0;
        for(Drone drone : chromosome.Get_Drones())
        {
            drone = Fitness_Function_Gene(drone);
            fitness_chromosome += drone.Get_Fitness();
        }
        chromosome.Set_Fitness(fitness_chromosome);
        return chromosome;
    }

    /**
     * This function assigns "the given drone" a fitness score according
     * to its travelling route.
     * 
     * The fitness score is calculated as the inverse of the average
     * distance. The choice of average distance is crucial. Otherwise,
     * drones that make more deliveries would get a worse score even if
     * they are much more efficient with their travelling routes. The
     * average distance is calculated by simply dividing by the number of
     * "visited places": (restaurant)-->(order pos1)-->(...)-->(order posX).
     */
    private static Drone Fitness_Function_Gene(Drone drone)
    {
        ArrayList<RestaurantOrders> restaurants_and_orders = drone.Get_Restaurants_And_Orders();

        double total_distance = 0.0;
        int visited_places = 0;
        for(RestaurantOrders restaurantOrders : restaurants_and_orders)
        {
            ArrayList<Order> orders = restaurantOrders.Get_Orders();
            Restaurant restaurant = restaurantOrders.Get_Restaurant();
            double distance = Euclid_Distance(drone, restaurant);
            drone = Update_Drone_Position(drone, restaurant);
            for(Order order : orders)
            {
                distance += Euclid_Distance(drone, order);
                drone = Update_Drone_Position(drone, order);
            }
            total_distance += distance;
            visited_places += 1 + orders.size(); //(+1) for the restaurant.
        }
        double double_visited_places = visited_places;
        double average_distance = total_distance / double_visited_places;
        double fitness_gene = 1 / average_distance;
        drone.Set_Fitness(fitness_gene);
        return drone;
    }

    /**
     * Calculates the distance between the "drone" and the location of
     * the "order". Furthermore, once the distance have been calculated
     * the drone's position is updated to the location of the "order".
     */
    public static double Euclid_Distance(Drone drone, Order order)
    {
        double euclidean_distance = Euclid_Distance(drone.Get_X_Pos(), drone.Get_Y_Pos(), order.Get_X_Pos(), order.Get_Y_Pos());
        return euclidean_distance;
    }

    /**
     * Calculates the distance between the "drone" and the location of
     * the "restaurant". Furthermore, once the distance have been calculated
     * the drone's position is updated to the location of the "restaurant".
     */
    public static double Euclid_Distance(Drone drone, Restaurant restaurant)
    {
        double euclidean_distance = Euclid_Distance(drone.Get_X_Pos(), drone.Get_Y_Pos(), restaurant.Get_X_Pos(), restaurant.Get_Y_Pos());
        return euclidean_distance;
    }

    /**
     * Calculates the Euclidean Distance.
     */
    public static double Euclid_Distance(int x1, int y1, int x2, int y2)
    {
        double x_diff_squared = Math.pow(x1 - x2, 2);
        double y_diff_squared = Math.pow(y1 - y2, 2);
        double euclidean_distance = Math.sqrt(x_diff_squared + y_diff_squared);
        return euclidean_distance;
    }

    /**
     * Updates the drone's position to the location of the "restaurant"
     * where the orders were picked up
     */
    public static Drone Update_Drone_Position(Drone drone, Restaurant restaurant)
    {
        drone.Set_X_Pos(restaurant.Get_X_Pos());
        drone.Set_Y_Pos(restaurant.Get_Y_Pos());
        return drone;
    }

    /**
     * Updates the drone's position to the location of the "restaurant"
     * where the orders were picked up
     */
    public static Drone Update_Drone_Position(Drone drone, Order order)
    {
        drone.Set_X_Pos(order.Get_X_Pos());
        drone.Set_Y_Pos(order.Get_Y_Pos());
        return drone;
    }

    /**
     * This function simulates a run of the given solution by
     * calculating the accumulated euclidean distances from the drones
     * and then returns the value.
     */
    public static double Get_Solution_Distance(Chromosome solution)
    {
        double total_distance = 0.0;
        for(Drone drone : solution.Get_Drones())
        {
            for(RestaurantOrders restaurantOrders : drone.Get_Restaurants_And_Orders())
            {
                Restaurant restaurant = restaurantOrders.Get_Restaurant();
                total_distance += Euclid_Distance(drone, restaurant);
                drone = Update_Drone_Position(drone, restaurant);
                for(Order order : restaurantOrders.Get_Orders())
                {
                    total_distance += Euclid_Distance(drone, order);
                    drone = Update_Drone_Position(drone, order);
                }
            }
        }
        solution = InitializePopulation.Assign_Drones_Initial_Positions(solution);
        return total_distance;
    }

    /**
     * This function simulates a run of the given solution by
     * calculating the accumulated euclidean distances from the drones
     * and simply prints them.
     */
    public static void Print_Solution_Distance(Chromosome solution)
    {
        double total_distance = 0.0;
        for(Drone drone : solution.Get_Drones())
        {
            for(RestaurantOrders restaurantOrders : drone.Get_Restaurants_And_Orders())
            {
                Restaurant restaurant = restaurantOrders.Get_Restaurant();
                total_distance += Euclid_Distance(drone, restaurant);
                drone = Update_Drone_Position(drone, restaurant);
                for(Order order : restaurantOrders.Get_Orders())
                {
                    total_distance += Euclid_Distance(drone, order);
                    drone = Update_Drone_Position(drone, order);
                }
            }
        }
        solution = InitializePopulation.Assign_Drones_Initial_Positions(solution);
        System.err.println(total_distance);
    }
}