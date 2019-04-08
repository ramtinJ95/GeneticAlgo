import java.util.*;

/**
 * Simple data structure for storing orders according
 * to the restaurant they were picked up from. 
 */
public class RestaurantOrders
{
    private Restaurant restaurant;
    private ArrayList<Order> orders;

    public RestaurantOrders()
    {
        restaurant = null;
        orders = new ArrayList<>();
    }

    public RestaurantOrders(Restaurant restaurant, ArrayList<Order> orders)
    {
        this.restaurant = restaurant;
        this.orders = orders;
    }

    /**
     * Get-Functions, Set-Functions, Add-Functions and simply
     * Check-Functions. Hopefully these are rather self explanatory.
     */
    public int Get_Orders_Size() { return orders.size(); }

    public void Add_Order(Order order) { orders.add(order); }

    public void Set_Restaurant(Restaurant restaurant) { this.restaurant = restaurant; }

    public ArrayList<Order> Get_Orders() { return orders; }
    public Restaurant Get_Restaurant() { return restaurant; }
}