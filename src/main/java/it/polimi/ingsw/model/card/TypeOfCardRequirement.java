package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.DeckDashboard;
import it.polimi.ingsw.model.PlayerDashboard;

import java.util.ArrayList;

public class TypeOfCardRequirement implements Requirement {
    private ColourCount cardColours;

    public TypeOfCardRequirement(ColourCount cardColours) {
        this.cardColours = cardColours;
    }

    public ColourCount getCardColours() {
        return cardColours;
    }

    @Override
    //TRUE IF THE PLAYER CAN ACTUALLY PLAY THE CARD
    public boolean isPlayable(PlayerDashboard player) { //True if there are enough card colours
        DeckDashboard devCards[] = player.getDevCards();
        ColourCount countColourDevCards = new ColourCount(0,0,0,0);
        countColourDevCards(countColourDevCards,devCards);
        //Now we have countColourDevCards that is filled with all player's devCards colours
        if(cardColours.getGreen()<=countColourDevCards.getGreen()&&cardColours.getYellow()<=countColourDevCards.getYellow()&&
           cardColours.getBlue()<= countColourDevCards.getBlue()&&cardColours.getPurple()<= countColourDevCards.getPurple())
            return true; //True if there are enough card colours
        else
            return false;
    }

    //COUNTS NUMBER OF PLAYER'S DEVCARDS COLOURS
    private void countColourDevCards(ColourCount count, DeckDashboard[] devCards){ //Creating count (Total of devCards colours)
        ArrayList<DevelopmentCard> devCardSlot;
        for(int i=0;i<devCards.length;i++){ //Reading player's devCards
            devCardSlot = devCards[i].getDeck();
            for(int j=0;j<devCardSlot.size();j++){ //Reading single deck of player's devCards
                devCardSlot.get(j).getColour().add(count,1); //Read colour then add to count
            }
        }
    }
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
}
