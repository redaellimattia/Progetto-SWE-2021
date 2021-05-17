package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.DeckDashboard;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.CardColour;

import java.util.ArrayList;

public class TypeOfCardRequirement implements Requirement {
    private ColourCount cardColours;

    /**
     *
     * @param cardColours keeps card requirement as ColourCount
     */
    public TypeOfCardRequirement(ColourCount cardColours) {
        this.cardColours = cardColours;
    }

    /**
     *
     * @return value of cardColours
     */
    public ColourCount getCardColours() {
        return cardColours;
    }

    /**
     *
     * @param player player trying to play the card
     * @return true if playing that leader is possible
     */
    @Override
    public boolean isPlayable(PlayerDashboard player) { //True if there are enough card colours
        DeckDashboard[] devCards = player.getDevCards();
        ColourCount countColourDevCards = new ColourCount(0,0,0,0);
        countColourDevCards(countColourDevCards,devCards);
        //Now we have countColourDevCards that is filled with all player's devCards colours
        return cardColours.getGreen() <= countColourDevCards.getGreen() && cardColours.getYellow() <= countColourDevCards.getYellow() &&
                cardColours.getBlue() <= countColourDevCards.getBlue() && cardColours.getPurple() <= countColourDevCards.getPurple(); //True if there are enough card colours
    }

    /**
     * COUNTS NUMBER OF PLAYER'S DEVCARDS COLOURS
     *
     * @param count will be equal to sum of DevCards colours
     * @param devCards needed to sum up to the total
     */
    private void countColourDevCards(ColourCount count, DeckDashboard[] devCards){ //Creating count (Total of devCards colours)
        ArrayList<DevelopmentCard> devCardSlot;
        for (DeckDashboard devCard : devCards) { //Reading player's devCards
            devCardSlot = devCard.getDeck();
            for (DevelopmentCard developmentCard : devCardSlot) //Reading single deck of player's devCards
                developmentCard.getColour().add(count, 1); //Read colour then add to count
        }
    }

    /**
     * EQUALS
     *
     * @param o passed Object
     * @return true if o is the same object of this, or it has the same ColourCount cost
     */
    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof TypeOfCardRequirement))
            return false;

        //Check if same values
        TypeOfCardRequirement c = (TypeOfCardRequirement) o;
        return this.cardColours.equals(c.getCardColours()); // true if same colourCount
    }
    @Override
    public String toString(){
        return "You need these type of cards: " + cardColours.toString() + " to play this card.";
    }
}
