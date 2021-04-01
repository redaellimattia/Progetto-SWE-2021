package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;

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

    //GETTERS
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


    //ADD ON A SINGLE PARAMETER
    public void addGreen(int green){
        this.green += green;
    }
    public void addYellow(int yellow){
        this.yellow += yellow;
    }
    public void addBlue(int blue){
        this.blue += blue;
    }
    public void addPurple(int purple){
        this.purple += purple;
    }

    //REMOVE ON A SINGLE PARAMETER
    public void removeGreen(int green){
        this.green -= green;
    }
    public void removeYellow(int yellow){
        this.yellow -= yellow;
    }
    public void removeBlue(int blue){
        this.blue -= blue;
    }
    public void removePurple(int purple){
        this.purple -= purple;
    }


    //ADD COLOURS, TO MODIFY MORE THAN ONE PARAMETER AT ONCE
    public void addColours(int green, int yellow, int blue, int purple){
        this.green += green;
        this.yellow += yellow;
        this.blue += blue;
        this.purple += purple;
    }

    //REMOVE COLOURS, TO MODIFY MORE THAN ONE PARAMETER AT ONCE
    public void removeColours(int green, int yellow, int blue, int purple){
        this.green -= green;
        this.yellow -= yellow;
        this.blue -= blue;
        this.purple -= purple;
    }

    //EQUALS
    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof ColourCount)) //Check if o is instanceOf ResourceCount
            return false;

        //Check if same values
        ColourCount c = (ColourCount) o; //Cast to ResourceCount
        if(this.getGreen()==c.getGreen()&&this.getYellow()==c.getYellow()&&
                this.getBlue()==c.getBlue()&&this.getPurple()==c.getPurple())
            return true;
        return false;
    }
}
