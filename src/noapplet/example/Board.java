package noapplet.example;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Player[][] cells; // [col][row]
    private int size;
    private List<Place> win = new ArrayList<>();

    public Board() {
        this.size = 15;
        this.cells = new Player[size][size];
        // Cells initialized as null
        this.clear();
    }

    /** Create a new board of the specified size.
     * @param size the intended size of the omok board
     */
    public Board(int size) {
        // Design Choice: size is bound 15 - 100
        if (size < 15 || size > 100) size = 15;
        this.size = size;
        this.cells = new Player[size][size];

        // Cells initialized as null
        this.clear();
    }

    /**Return the size of this board.
     * @return  the size of the omok board
     */
    public int size() {
        return size;
    }

    public Player[][] cells() {
        return cells;
    }
    /** Removes all the stones placed on the board, effectively
     * resetting the board to its original state.
     */
    public void clear() {
        //if(cells == null) return;
        // Cells initialized as null
        for (int col = 0; col < this.size; col++) {
            for (int row = 0; row < this.size; row++) {
                cells[col][row] = null;
            }
        }
    }

    public String validateMove(int x, int y, Player player) {
        if(isEmpty(x, y) && isValid(x,y)){
            placeStone(x, y, player);
            if(isWonBy(player)){
                return "PLAYER_WIN";
            }
            else if(isFull()){
                return "BOARD_FULL";
            }
            return "STONE_PLACED";
        }
        return "CELL_UNAVAILABLE";
    }

    /** Return a boolean value indicating whether all the intersections
     * on the board are occupied or not.
     * @return boolean based on if the omok board is full
     */
    public boolean isFull() {
        for (int col = 0; col < this.size; col++) {
            for (int row = 0; row < this.size; row++) {
                if (cells[col][row] == null) return false;
            }
        }
        return true;
    }
    /**Place a stone for the specified player at a specified
     * intersection (x, y) on the board.
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @param player Player whose stone is to be placed
     */
    public void placeStone(int x, int y, Player player) {
        /*assume that x and y are valid placements
        - they are within the board bounds
        - they are empty
        assume that player is not null
        Such tests mentioned above is checked in consoleUI before method
        is called
         */
        cells[x][y] = player; //switch later? y x

    }
    /**Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is empty or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @return boolean if omok board is empty or not
     */
    public boolean isEmpty(int x, int y) {
        return cells[x][y] == null;
    }
    /**Return a boolean value indicating whether the given x and y value
     * is in the bounds of the omok board (based on 0 indexing)
     *
     * @param x desired x index
     * @param y desired y index
     * @return boolean if x and y is in bounds
     */
    public boolean isValid(int x, int y){
        if(x >= 0 && x < size && y >= 0 && y < size) return true;
        return false;
    }
    /**Return a boolean value indicating whether the given player
     * has a winning row on the board. A winning row is a consecutive
     * sequence of five or more stones placed by the same player in
     * a horizontal, vertical, or diagonal direction.
     * @param player
     * @return boolean if the given player won the omok game
     */
    public boolean isWonBy(Player player) {
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(cells[i][j] == null) continue;
                if(checkDirection(i, j, 1, 0, player) ||
                        checkDirection(i, j, 0, 1, player) ||
                        checkDirection(i, j, 1, 1, player) ||
                        checkDirection(i, j, 1, -1, player))//might need to change
                    return true;
            }
        }
        return false;
    }

    /**Return a boolean indicating whether there is a win in a specific
     * direction (row, column, & diagonals)
     *
     * @param x index that is going to be checked
     * @param y index that is going to be checked
     * @param dirX the x direction that will be checked
     * @param dirY the y direction that will be checked
     * @param player that possibly has a win
     * @return boolean based on whether there is a win
     */
    public boolean checkDirection(int x, int y, int dirX, int dirY, Player player){
        for(int i = 0; i < 5; i++){
            int newX = x + i * dirX;
            int newY = y + i * dirY;
            if(newX < 0 || newX >= size || newY < 0 || newY >= size) return false;
            if(cells[newX][newY] != player){
                win.clear();//clears List if false, in other words no win
                return false;
            }
            win.add(new Place(newX, newY));//adds stone to list
        }
        return true;//the list win should contain five places for the winning row
    }

    /** Return the winning row.
     *
     * @return List<Place>
     */
    public List<Place> winningRow() {//assuming isWonBy is called before winningRow
        //if there is no winner, the list win should be empty
        return win;
    }

}