import java.util.*;

/**
 * A data structure used for representing the drones. While
 * a chromosome, a collection of drones, is an entire solution
 * each individual drone can be considered a sub solution to the
 * chromosome solution. Why is this? Because each individual drone
 * represents a sub route in the entire collection of routes with the
 * chromosome contains.
 * 
 * 
 * NOTE: Once a drone has picked up one or several orders from
 * a restaurant it has to deliver ALL orders before picking up new orders.
 */
public class Drone implements Comparable<Drone>
{
    private int x_pos;
    private int y_pos;
    private double fitness;

    private ArrayList<RestaurantOrders> restaurants_and_orders;

    /**
     * Just like the polymerase function the positions are initialized
     * with junk values. During debugging if we ever saw (-1) in the positions
     * we would know that we "forgot" to assign initial positions to the drones.
     */
    public Drone()
    {
        fitness = 0.0;
        x_pos = -1;
        y_pos = -1;
        restaurants_and_orders = new ArrayList<>();
    }

    /**
     * The more safer constructor.
     */
    public Drone(int x_pos, int y_pos)
    {
        fitness = 0.0;
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }

    /**
     * Get-Functions, Set-Functions, Add-Functions and simply
     * Check-Functions. Hopefully these are rather self explanatory.
     */
    public void Add_RestaurantOrders(RestaurantOrders restaurantOrders) { restaurants_and_orders.add(restaurantOrders); }

    public int Get_X_Pos() { return x_pos; }
    public void Set_X_Pos(int x_pos) { this.x_pos = x_pos; }

    public int Get_Y_Pos() { return y_pos; }
    public void Set_Y_Pos(int y_pos) { this.y_pos = y_pos; }

    public double Get_Fitness() { return fitness; }
    public void Set_Fitness(double fitness) { this.fitness = fitness; }

    public ArrayList<RestaurantOrders> Get_Restaurants_And_Orders() { return restaurants_and_orders; }
    public void Set_Restaurants_And_Orders(ArrayList<RestaurantOrders> restaurants_and_orders) { this.restaurants_and_orders = restaurants_and_orders; }

    public int compareTo(Drone drone)
    {
        return Double.compare(fitness, drone.Get_Fitness());
    }
}