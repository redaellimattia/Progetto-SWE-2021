package it.polimi.ingsw.model.card;

import it.polimi.ingsw.view.cli.CliColours;

public class ColourCount {
    private int green;
    private int yellow;
    private int blue;
    private int purple;

    /**
     *
     * @param green CardColour.GREEN counter
     * @param yellow CardColour.YELLOW counter
     * @param blue CardColour.BLUE counter
     * @param purple CardColour.PURPLE counter
     */
    public ColourCount(int green, int yellow, int blue, int purple) {
        this.green = green;
        this.yellow = yellow;
        this.blue = blue;
        this.purple = purple;
    }

    //GETTERS

    /**
     *
     * @return CardColour.GREEN counter
     */
    public int getGreen() {
        return green;
    }

    /**
     *
     * @return CardColour.YELLOW counter
     */
    public int getYellow() {
        return yellow;
    }

    /**
     *
     * @return CardColour.BLUE counter
     */
    public int getBlue() {
        return blue;
    }

    /**
     *
     * @return CardColour.PURPLE counter
     */
    public int getPurple() {
        return purple;
    }


    //ADD ON A SINGLE PARAMETER

    /**
     *
     * @param green CardColour.GREEN counter counter incremented of green
     */
    public void addGreen(int green){
        this.green += green;
    }

    /**
     *
     * @param yellow CardColour.YELLOW counter counter incremented of yellow
     */
    public void addYellow(int yellow){
        this.yellow += yellow;
    }

    /**
     *
     * @param blue CardColour.BLUE counter counter incremented of blue
     */
    public void addBlue(int blue){
        this.blue += blue;
    }

    /**
     *
     * @param purple CardColour.PURPLE counter counter incremented of purple
     */
    public void addPurple(int purple){
        this.purple += purple;
    }

    //REMOVE ON A SINGLE PARAMETER

    /**
     *
     * @param green CardColour.GREEN counter counter reduced of green
     */
    public void removeGreen(int green){
        this.green -= green;
    }

    /**
     *
     * @param yellow CardColour.YELLOW counter counter reduced of yellow
     */
    public void removeYellow(int yellow){
        this.yellow -= yellow;
    }

    /**
     *
     * @param blue CardColour.BLUE counter counter reduced of blue
     */
    public void removeBlue(int blue){
        this.blue -= blue;
    }

    /**
     *
     * @param purple CardColour.PURPLE counter counter reduced of purple
     */
    public void removePurple(int purple){
        this.purple -= purple;
    }


    /**
     * Add colours, to modify more than one parameter at once
     *
     * @param green CardColour.GREEN counter incremented of green
     * @param yellow CardColour.YELLOW counter incremented of yellow
     * @param blue CardColour.BLUE counter incremented of blue
     * @param purple CardColour.PURPLE counter incremented of purple
     */
    public void addColours(int green, int yellow, int blue, int purple){
        this.green += green;
        this.yellow += yellow;
        this.blue += blue;
        this.purple += purple;
    }

    /**
     * Remove colours, to modify more than one parameter at once
     *
     * @param green CardColour.GREEN counter reduced of green
     * @param yellow CardColour.YELLOW counter reduced of yellow
     * @param blue CardColour.BLUE counter reduced of blue
     * @param purple CardColour.PURPLE counter reduced of purple
     */
    public void removeColours(int green, int yellow, int blue, int purple){
        this.green -= green;
        this.yellow -= yellow;
        this.blue -= blue;
        this.purple -= purple;
    }

    /**
     * Sum 2 colourcount
     *
     * @param add passed ColourCount
     */
    public void sumCounts(ColourCount add){
        if(add!=null)
            this.addColours(add.getGreen(),add.getYellow(), add.getBlue(), add.getPurple());
    }

    /**
     * Equals
     *
     * @param o passed Object
     * @return true if o is the same object of this, or it has the same number of colours
     */
    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof ColourCount)) //Check if o is instanceOf ResourceCount
            return false;

        //Check if same values
        ColourCount c = (ColourCount) o; //Cast to ResourceCount
        return this.getGreen() == c.getGreen() && this.getYellow() == c.getYellow() &&
                this.getBlue() == c.getBlue() && this.getPurple() == c.getPurple();
    }

    public String toString(){
        String count = "";
        if(this.getGreen() !=0)
           count +=(CliColours.GREEN+ " GREEN: " + this.getGreen()+" "+CliColours.RESET);
        if(this.getBlue() !=0)
            count +=(CliColours.BLUE+" BLUE: " + this.getBlue()+" "+CliColours.RESET);
        if(this.getPurple() !=0)
            count +=(CliColours.PURPLE+" PURPLE: " + this.getPurple()+" "+CliColours.RESET);
        if(this.getYellow() !=0)
            count += (CliColours.YELLOW+" YELLOW: " + this.getYellow()+" "+CliColours.RESET);

        return count;
    }
}
