package noapplet.example;

public class Place {
    /** 0-based column index of this place. */
    public final int x;

    /** 0-based row index of this place. */
    public final int y;

    /** Create a new place of the given indices.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public Place(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**Returns a boolean indicating whether two Place objects are equal
     *
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if(getClass() != o.getClass()) return false;
        return x == ((Place)o).x && y == ((Place)o).y;
    }

    /**Returns int of a hashcode
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return (x * 31) + y;
    }

}
