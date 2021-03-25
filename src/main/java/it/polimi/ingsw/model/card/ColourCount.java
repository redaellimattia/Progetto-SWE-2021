package it.polimi.ingsw.model.card;

public class ColourCount {
    private int green;
    private int yellow;
    private int blue;
    private int purple;

    public ColourCount(int green, int yellow, int blue, int purple) {
        this.green = green;
        this.yellow = yellow;
        this.blue = blue;
        this.purple = purple;
    }

    public int getGreen() {
        return green;
    }

    public int getYellow() {
        return yellow;
    }

    public int getBlue() {
        return blue;
    }

    public int getPurple() {
        return purple;
    }
}