import java.util.*;

/**
 * This class is used to merge genes from two parent chromosomes
 * to create a child chromosome.
 * 
 * Terminology that might come in handy is presented below.
 * Please do not skip the explanations below if you actually
 * want to understand what's going on in the code.
 * 
 * ALLELE: Implicitly represented by an Order OR a Restaurant.
 * Explicitly represented by the (x,y) coordinates of the Orders
 * and the Restuarants, which represents the routes. NOTE: An allele
 * is simply a variant form of the same gene. The Orders and the restaurants
 * can therefore be seen as alleles since they fill the same functionality
 * but have variant forms (different [x,y] positions).
 * 
 * GENE: A collection of Alleles. Implicitly represend by a Drone
 * because it is a collection of Orders and Restaurants. Explicitly
 * represented by a collection of (x,y) coordinates for all the Orders
 * and Restaurants combined for a SINGLE Drone, which represents a sub-route.
 * 
 * CHROMOSOME: A collection of Genes. Implicitly represend by a "collection"
 * of drones. Explicitly represented by a collection of (x,y) coordinates
 * for all the Orders and Restaurants combined for a ALL Drones,
 * which represents the entire route for all the deliveries.
 * 
 * POPULATION: A population is a collection of chromosomes. The reason for having
 * several chromosomes is due to the fact that we are running a Genetic Algorithm
 * and unless we have several chromosomes we cannot really mate/breed two parents
 * in order to create a child chromosome.
 */
public class Polymerase
{
    private int allele_iterator;
    private int gene_iterator;
    private int gene_cap_iterator;
    private int number_of_alleles_to_add_from_parent;
    ArrayList<Integer> alleles_added_to_child = Get_Negative_One_ArrayList(Constants.NUMBER_OF_ORDERS);

    private boolean parent_alleles_added;
    private Chromosome child_chromosome;
    private RestaurantOrders restaurant_and_orders = new RestaurantOrders();

    /**
     * This constructor simply creates the polymerase object. The object
     * however cannot be used directly and probably will cause null pointer
     * exceptions or simply give solutions that doesn't make sense.
     * 
     * This is done deliberately in order to avoid missing important function
     * calls that must be done in order for the polymerase to work.
     * 
     * More specifically first the polymerase object is created by invoking
     * the Polymerase() constructor. Then the first parent parameters are set
     * by invoking the function Set_Parent_Parameters(). Once the polymerase
     * have added all the genes from the first parent the second parameters
     * need to be set by invoking the function Set_Parent_Parameters().
     * Lastly, once the polymerase have added all the genes from the second
     * parent the "second parent must" invoke the function Add_Remaining_Genes().
     */
    public Polymerase()
    {
        allele_iterator = -1;
        gene_iterator = 0;
        gene_cap_iterator = 0;
        number_of_alleles_to_add_from_parent = -1;
        parent_alleles_added = true;
        child_chromosome = new Chromosome(Constants.NUMBER_OF_DRONES);
    }

    /**
     * Sets the parent parameter, the number_of_alleles_to_add_from_parent so
     * that the polymerase knowns how many alleles to add from the parent.
     */
    public void Set_Parent_Parameters(int number_of_alleles_to_add_from_parent)
    {
        //Print_Alleles_Added_To_Child();
        parent_alleles_added = false;
        allele_iterator = 0;
        this.number_of_alleles_to_add_from_parent = number_of_alleles_to_add_from_parent;
    }

    /**
     * Adds an "allele" the child chromosome which the polymerase
     * is building.
     * 
     * To map this over to the drones and delivery problem. The
     * restaurants are the alleles, and each restaurant contains
     * an (x, y) coordinate. We do not have a duplicate check here as
     * we do for the "order_allele" because it is okay for a drone
     * to visit a restaurant several times and most likely will HAVE
     * to do it in order to make the deliveries it is suposed to make.
     */
    public void Add_Allele(Restaurant allele_restaurant)
    {
        if(parent_alleles_added == false)
        {
            restaurant_and_orders.Set_Restaurant(allele_restaurant);
        }
    }

    /**
     * Adds an "allele" to the child chromosome which the
     * polymerase is building. If a certain "allele" has already been
     * added for a certain functionality we don't want to add it
     * again (the if-statement).
     * 
     * To map this over to the drones and delivery problem. The orders
     * are the alleles, and each order contains an (x, y) coordinate.
     * If a certain order has already been delivered we do not want a
     * drone to unnecessarily fly over there only to relies that it has
     * already been delivered!
     */
    public void Add_Allele(Order allele_order)
    {
        if(alleles_added_to_child.get(allele_order.Get_ID()) == -1)
        {
            alleles_added_to_child.set(allele_order.Get_ID(), allele_order.Get_ID());
            //System.err.println("allele_iterator: " + Integer.toString(allele_iterator));
            //System.err.println("order.Get_ID(): " + Integer.toString(allele_order.Get_ID()));
            restaurant_and_orders.Add_Order(allele_order);
            allele_iterator++;
            gene_cap_iterator++;
            Recalculate_Child_Polymerase();
        }
    }

    /**
     * If-Statement-1: If all alleles from a single parent have been
     * added set the parent_alleles_added boolean to true which
     * prevents the Mating-Function() in the Mating class to stop
     * sending. The same is true for the second parent because we only
     * want to add X = Constants.NUMBER_OF_ORDERS  amount of genes.
     * NOTE: an allele here is an "order".
     * 
     * If-Statement-2: Each drone can only delivery X = Constants.DRONE_CAPACITY
     * amount of orders before the have to return to a restaurant and pick up
     * new orders. Therefore, once a drone has filled been filled with maximum
     * capacity we set the (drone_capacity_iterator to 0) and increase the
     * (drone_iterator by 1). This is our assigning scheme. we fill each drone
     * to maximum capacity and then move on to the next drone and fill it with maximum
     * capacity etc; this is done in a cyclical fashion. (See If-Statement_3)
     * NOTE: gene_cap_iterator == drone_capacity_iterator AND drone_iterator == gene_iterator.
     * 
     * If-Statement-3: We only have (X = Constants.NUMBER_OF_DRONES) drones. Once
     * all drones have been filled up to a maximum capacity we reset the
     * (drone_iterator to 0). If there are still orders left we keep filling
     * up the drones to maximum capacity in a cyclical fashion. Remember,
     * every new "RestaurantOrders" object that is added to a single drone it
     * has to visit the restaurant first deliver the orders and the process
     * the next "RestaurantOrders" object that was might have been assigned to it.
     */
    public void Recalculate_Child_Polymerase()
    {
        if(allele_iterator >= number_of_alleles_to_add_from_parent)
        {
            //Print_Alleles_Added_To_Child();
            parent_alleles_added = true;
        }
        if(gene_cap_iterator >= Constants.DRONE_CAPACITY)
        {
            gene_cap_iterator = 0;
            //System.err.println(restaurant_and_orders.Get_Restaurant());
            child_chromosome.Get_Drone(gene_iterator).Add_RestaurantOrders(restaurant_and_orders);
            if(parent_alleles_added == false); restaurant_and_orders = new RestaurantOrders();
            gene_iterator++;
        }
        if(gene_iterator >= Constants.NUMBER_OF_DRONES)
        {
            gene_iterator = 0;
        }
    }

    /**
     * Once all parent genes have been added from "both" parents
     * the object RestaurantOrders which contains the alleles
     * from the parents might not have been added in the very last case.
     * Therefore we need to add the remaining genes, if there are any, after
     * the polymerase is completely done. This is an edge case and was rather
     * hard to avoid.
     */
    public void Add_Remaining_Genes()
    {
        if(allele_iterator >= number_of_alleles_to_add_from_parent)
        {
            if(restaurant_and_orders.Get_Orders().size() > 0)
            {
                //System.err.println(restaurant_and_orders.Get_Restaurant());
                child_chromosome.Get_Drone(gene_iterator).Add_RestaurantOrders(restaurant_and_orders);
                parent_alleles_added = true;
                //Print_Alleles_Added_To_Child();
            }
        }
    }

    /**
     * Fills an Arraylist of with (-1) and the size depends on the
     * parameter "size";
     */
    private static ArrayList<Integer> Get_Negative_One_ArrayList(int size)
    {
        ArrayList<Integer> arr = new ArrayList<>();
        for(int a = 0; a < size; a++) arr.add(-1);
        return arr;
    }

    /**
     * This method was only used for debugging purposes
     * in order to verify our solutions.
     */
    private void Print_Alleles_Added_To_Child()
    {
        System.err.println();
        for(Integer integer :  alleles_added_to_child)
        {
            System.err.print(integer);
            System.err.print(" ");
        }
        System.err.println();
    }

    /**
     * Get-Functions, Set-Functions, Add-Functions and simply
     * Check-Functions. Hopefully these are rather self explanatory.
     */
    public int Get_Allele_Iterator() { return allele_iterator; }
    public int Get_Gene_Iterator() { return gene_iterator; }
    public int Get_Gene_Cap_Iterator() { return gene_cap_iterator; }
    public int Get_Number_Of_Alleles_To_Add_From_Parent() { return number_of_alleles_to_add_from_parent; }

    public void Set_Allele_Iterator(int allele_iterator) { this.allele_iterator = allele_iterator; }
    public void Set_Gene_Iterator(int gene_iterator) { this.gene_iterator = gene_iterator; }
    public void Set_Gene_Cap_Iterator(int gene_cap_iterator) { this.gene_cap_iterator = gene_cap_iterator; }
    public void Set_Number_Of_Alleles_To_Add_From_Parent(int number_of_alleles_to_add_from_parent) { this.number_of_alleles_to_add_from_parent = number_of_alleles_to_add_from_parent; }

    public boolean Parent_Alleles_Added() { return parent_alleles_added; }
    
    public Chromosome Get_Child_Chromosome() { return child_chromosome; }
    public void Set_Child_Chromosome(Chromosome child_chromosome) { this.child_chromosome = child_chromosome; }
}