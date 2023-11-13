package noapplet.example;

import java.awt.*;
import java.util.Objects;

public abstract class Player {
    private String name;
    private Color color;

    Player(String name, Color color){
        this.name = name;
        this.color = color;
    }

    public abstract String requestMove(Board board, int x, int y);//Every abstract class must have at least one abstract method

    //Getters & Setter
    public String getName() {
        return name;
    }
    public Color getColor() {
        return this.color;
    }

    public void setColor(String symbol) {
        this.color = color;
    }

    @Override
    public boolean equals(Object other) {
        //Intellij automatically creates an override for equals
        if (this == other) return true;
        if (!(other instanceof Player player)) return false;
        return Objects.equals(getName(), player.getName()) && Objects.equals(getColor(), player.getColor());
    }

    @Override
    public int hashCode() {
        //Intellij automatically creates an override for hashCode
        return Objects.hash(getName(), getColor());
    }
}
