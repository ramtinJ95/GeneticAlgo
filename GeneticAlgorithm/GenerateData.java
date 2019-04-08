import java.util.*;


/**
 * The class generates random orders and random restaurant positions.
 */
public class GenerateData
{
    private static Random random = new Random();

    /**
     * Genereates random (x,y) coordinates for the orders.
     * The ids are always assigned as indexes.
     */
    public static ArrayList<Order> Generate_Orders()
    {
        ArrayList<Order> orders = new ArrayList<>();
        for(int a = 0; a < Constants.NUMBER_OF_ORDERS; a++)
        {
            int x_pos = random.nextInt(Constants.GRID_SIZE);
            int y_pos = random.nextInt(Constants.GRID_SIZE);
            Order order = new Order(a, x_pos, y_pos);
            orders.add(order);
        }
        return orders;
    }

    /**
     * Genereates random (x,y) coordinates for the restuarant.
     */
    public static ArrayList<Restaurant> Generate_Restaurants()
    {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for(int a = 0; a < Constants.NUMBER_OF_RESTAURANTS; a++)
        {
            int x_pos = random.nextInt(Constants.GRID_SIZE);
            int y_pos = random.nextInt(Constants.GRID_SIZE);
            Restaurant restaurant = new Restaurant(a, x_pos, y_pos);
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    /**
     * This method was only used for debugging purposes in order
     * to veryfy our solution.
     */
    public static ArrayList<Order> Generate_Debug_Orders()
    {
        ArrayList<Order> orders = new ArrayList<>();
        for(int a = 0; a < Constants.NUMBER_OF_ORDERS; a++)
        {
        	Order order = new Order(a, a, a);
        	orders.add(order);
        }
        return orders;
    }

    /**
     * This method was only used for debugging purposes in order
     * to veryfy our solution.
     */
    public static ArrayList<Restaurant> Generate_Debug_Restaurants()
    {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for(int a = 0; a < Constants.NUMBER_OF_RESTAURANTS; a++)
        {
        	Restaurant restaurant = new Restaurant(a, a, a);
        	restaurants.add(restaurant);
        }
        return restaurants;
    }
}