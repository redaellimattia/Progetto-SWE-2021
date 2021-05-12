package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.DeckDashboard;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.CardColour;

import java.util.ArrayList;

public class CardLevelRequirement implements Requirement {
    private CardColour colour;
    private int level; //Assuming level goes from 1 to 3

    /**
     *
     * @param colour colour as CardColour
     * @param level value
     */
    public CardLevelRequirement(CardColour colour, int level) {
        this.colour = colour;
        this.level = level;
    }

    /**
     *
     * @return colour as CardColour
     */
    public CardColour getColour() {
        return colour;
    }

    /**
     *
     * @return level value
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @param player player trying to play the card
     * @return true if playing that leader is possible
     */
    @Override
    public boolean isPlayable(PlayerDashboard player){
        DeckDashboard[] devCards = player.getDevCards();
        ArrayList<DevelopmentCard> devCardSlot;
        CardColour devCardColour;
        int devCardLevel;
        for (DeckDashboard devCard : devCards) { //Reading devCards array
            devCardSlot = devCard.getDeck();
            for (DevelopmentCard developmentCard : devCardSlot) { //Reading the devCardSlot(Reading single arrayList slot)
                devCardColour = developmentCard.getColour(); //Reading devCard Colour
                devCardLevel = developmentCard.getLevel(); //Reading devCard Level
                if (devCardColour.equals(this.colour) && devCardLevel == this.level)
                    return true;
            }
        }
        return false;
    }

    /**
     * EQUALS
     *
     * @param o passed Object
     * @return true if o is the same object of this, or it has the same colour and level values
     */
    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof CardLevelRequirement))
            return false;

        //Check if same values
        CardLevelRequirement c = (CardLevelRequirement) o;
        return this.colour.equals(c.getColour()) && this.level == c.getLevel(); //True if same values
    }
    public ColourCount getCardColours() {
        return null;
    }
    public ResourceCount getResources() {
        return null;
    }
}
