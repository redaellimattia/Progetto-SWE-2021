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

    public void addGreen(int green){
        this.green += green;
    }
    public void removeGreen(int green){
        this.green -= green;
    }
    public void addYellow(int yellow){
        this.yellow += yellow;
    }
    public void removeYellow(int yellow){
        this.yellow -= yellow;
    }
    public void addBlue(int blue){
        this.blue += blue;
    }
    public void removeBlue(int blue){
        this.blue -= blue;
    }
    public void addPurple(int purple){
        this.purple += purple;
    }
    public void removePurple(int purple){
        this.purple -= purple;
    }

    public void addColours(int green, int yellow, int blue, int purple){
        this.green += green;
        this.yellow += yellow;
        this.blue += blue;
        this.purple += purple;
    }

    public void removeColours(int green, int yellow, int blue, int purple){
        this.green -= green;
        this.yellow -= yellow;
        this.blue -= blue;
        this.purple -= purple;
    }
}
