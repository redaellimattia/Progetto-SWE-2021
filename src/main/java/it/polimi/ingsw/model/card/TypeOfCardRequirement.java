package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.DeckDashboard;
import it.polimi.ingsw.model.PlayerDashboard;

import java.util.ArrayList;

public class TypeOfCardRequirement implements Requirement {
    private ColourCount cardColours;

    public TypeOfCardRequirement(ColourCount cardColours) {
        this.cardColours = cardColours;
    }

    @Override
    public boolean isPlayable(PlayerDashboard player) { //True if there are enough card colours
        DeckDashboard devCards[] = player.getDevCards();
        ColourCount countColourDevCards = new ColourCount(0,0,0,0);
        countColourDevCards(countColourDevCards,devCards);
        if(cardColours.getGreen()<=countColourDevCards.getGreen()&&cardColours.getYellow()<=countColourDevCards.getYellow()&&
           cardColours.getBlue()<= countColourDevCards.getBlue()&&cardColours.getPurple()<= countColourDevCards.getPurple())
            return true;
        else
            return false;
    }
    private void countColourDevCards(ColourCount count,DeckDashboard devCards[]){ //Creating count (Total of devCards colours)
        ArrayList<DevelopmentCard> devCardSlot;
        for(int i=0;i<devCards.length;i++){
            devCardSlot = devCards[i].getDeck();
            for(int j=0;j<devCardSlot.size();j++){
                devCardSlot.get(j).getColour().add(count,1);
            }
        }
    }
}
