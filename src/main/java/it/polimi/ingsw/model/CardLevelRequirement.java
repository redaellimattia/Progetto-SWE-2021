package it.polimi.ingsw.model;

public class CardLevelRequirement extends Requirement{
    private CardColour colour;
    private int level;
    private int number;

    public CardLevelRequirement(RequirementsType type, CardColour colour, int level, int number) {
        super(type);
        this.colour = colour;
        this.level = level;
        this.number = number;
    }

    public CardColour getColour() {
        return colour;
    }

    public int getLevel() {
        return level;
    }

    public int getNumber() {
        return number;
    }
}
