import java.util.*;

/**
 * The chromosome class simply has a collection of drones.
 * The class does not contain much logic, it simply just contains
 * a collection of drones and has a fitness score associated with
 * it in order to evaluate the solution. This class could have been
 * avoided complete but was made for convenience purposes.
 */
public class Chromosome implements Comparable<Chromosome>
{
    private ArrayList<Drone> drones;
    private double fitness;

    public Chromosome()
    {
        fitness = 0.0;
        drones = new ArrayList<>();
    }

    public Chromosome(int number_of_drones)
    {
        fitness = 0.0;
        drones = new ArrayList<>();
        for(int a = 0; a < number_of_drones; a++) drones.add(new Drone());
    }

    public Chromosome(ArrayList<Drone> drones)
    {
        fitness = 0.0;
        this.drones = drones;
    }

    /**
     * Get-Functions, Set-Functions, Add-Functions and simply
     * Check-Functions. Hopefully these are rather self explanatory.
     */
    public void Add_Drone(Drone drone) { drones.add(drone); }

    public Drone Get_Drone(int index) { return drones.get(index); }
    public void Set_Drone(int index, Drone drone) { drones.set(index, drone); }

    public double Get_Fitness() { return fitness; }
    public void Set_Fitness(double fitness) { this.fitness = fitness; }

    public ArrayList<Drone> Get_Drones() { return drones; }
    public void Set_Drones(ArrayList<Drone> drones) { this.drones = drones; }

    public int compareTo(Chromosome chromosome)
    {
        return Double.compare(fitness, chromosome.Get_Fitness());
    }


    /**
     * Everything below is only used for printing stuff.
     */
    public static void Solution_Verification_Chromosome(Chromosome chromosome, int generation)
    {
        Print_New_Line(5);
        Print_Labels_And_Ids(0, "Generation", generation);
        Print_Labels_And_Ids(1, "Chromosome", -1);
        Print_Chromosome_Distance(1, chromosome);
        for(int b = 0; b < chromosome.Get_Drones().size(); b++)
        {
            Drone drone = chromosome.Get_Drone(b);
            Print_Labels_And_Ids(2, "Drone", -1);
            for(int c = 0; c < drone.Get_Restaurants_And_Orders().size(); c++)
            {
                RestaurantOrders restaurant_and_order = drone.Get_Restaurants_And_Orders().get(c);
                Restaurant restaurant = restaurant_and_order.Get_Restaurant();
                ArrayList<Order> orders = restaurant_and_order.Get_Orders();
                Print_Labels_And_Ids(3, "restaurant", restaurant.Get_ID());
                Print_Orders(3, orders);
            }
        }
        Print_New_Line(5);
    }

    public static void Solution_Verification_Population(ArrayList<Chromosome> population, int generation)
    {
        Print_New_Line(5);
        Print_Labels_And_Ids(0, "Generation", generation);
        for(int a = 0; a < population.size(); a++)
        {
            Chromosome chromosome = population.get(a);
            Print_Labels_And_Ids(1, "Chromosome", -1);
            Print_Chromosome_Distance(1, chromosome);
            for(int b = 0; b < chromosome.Get_Drones().size(); b++)
            {
                Drone drone = chromosome.Get_Drone(b);
                Print_Labels_And_Ids(2, "Drone", -1);
                for(int c = 0; c < drone.Get_Restaurants_And_Orders().size(); c++)
                {
                    RestaurantOrders restaurant_and_order = drone.Get_Restaurants_And_Orders().get(c);
                    Restaurant restaurant = restaurant_and_order.Get_Restaurant();
                    ArrayList<Order> orders = restaurant_and_order.Get_Orders();
                    Print_Labels_And_Ids(3, "restaurant", restaurant.Get_ID());
                    Print_Orders(3, orders);
                }
            }
        }
        Print_New_Line(5);
    }

    private static void Print_New_Line(int nr)
    {
        for(int a = 0; a < nr; a++) System.err.println();
    }

    private static void Print_Chromosome_Distance(int nr_3_spaces, Chromosome chromosome)
    {
        String output = "";
        for(int a = 0; a < nr_3_spaces; a++) output += "   ";
        double score  = Fitness.Get_Solution_Distance(chromosome);
        output += "Chromosome Distance: " + Double.toString(score);
        System.err.println(output);
    }

    private static void Print_Labels_And_Ids(int nr_3_spaces, String name, int name_id)
    {
        String output = "";
        for(int a = 0; a < nr_3_spaces; a++) output += "   ";
        if(name_id >= 0) output += name + "ID: " + Integer.toString(name_id);
        else output += name + ":";
        System.err.println(output);
    }

    private static void Print_Orders(int nr_3_spaces, ArrayList<Order> orders)
    {
        String output = "";
        for(int a = 0; a < nr_3_spaces; a++) output += "   ";
        output += "Orders: [ ";
        for(Order order : orders) output += Integer.toString(order.Get_ID()) + " ";
        output += "]";
        System.err.println(output);
    }

    public void Print_Info()
    {
        for(Drone drone: drones)
        {
            System.err.println();
            String s = "Drone: x_pos = " + Integer.toString(drone.Get_X_Pos()) + " y_pos = " + Integer.toString(drone.Get_Y_Pos());
            System.err.println(s);
            System.err.println("Drone fitness: " + Double.toString(drone.Get_Fitness()));
            
            for(int a = 0; a < drone.Get_Restaurants_And_Orders().size(); a++)
            {
                RestaurantOrders restaurantOrders = drone.Get_Restaurants_And_Orders().get(a);
                ArrayList<Order> orders = restaurantOrders.Get_Orders();
                Restaurant restaurant = restaurantOrders.Get_Restaurant();
                s = "Restaurant" + Integer.toString(a) + ": x_pos = " + Integer.toString(restaurant.Get_X_Pos()) + " y_pos = " + Integer.toString(restaurant.Get_Y_Pos());
                System.err.println(s);
                s = "Orders:";
                for(int b = 0; b < orders.size(); b++)
                {
                    Order order  = orders.get(b);
                    s += " (" + Integer.toString(order.Get_X_Pos()) + ", " + Integer.toString(order.Get_Y_Pos()) + ")";
                }
                System.err.println(s);
            }
            System.err.println();
        }
        System.err.println("Chromosome fitness: " + Double.toString(fitness));
    }
}