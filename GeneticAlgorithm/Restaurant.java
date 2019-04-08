import java.util.*;

/**
 * Data structure used for the restaurants where the
 * deliveries are picked up. A single Restaurant is
 * an allele, it contains a (x,y) position in the grid.
 */
public class Restaurant
{
    private int x_pos;
    private int y_pos;
    private int id;

    public Restaurant(int id, int x_pos, int y_pos)
    {
        this.id = id;
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }

    /**
     * Get-Functions, Set-Functions, Add-Functions and simply
     * Check-Functions. Hopefully these are rather self explanatory.
     */
    public int Get_ID() { return id; }
    public int Get_X_Pos() { return x_pos; }
    public int Get_Y_Pos() { return y_pos; }
}