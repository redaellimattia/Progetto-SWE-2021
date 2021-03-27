package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.DeckDashboard;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.CardColour;

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
        CardColour devCardColour;
        for(int i=0;i<devCards.length;i++){
            devCardSlot = devCards[i].getDeck();
            for(int j=0;j<devCardSlot.size();j++){
                devCardColour = devCardSlot.get(j).getColour();
                switch(devCardColour){
                    case GREEN: count.addColours(1,0,0,0);
                                break;
                    case YELLOW:count.addColours(0,1,0,0);
                                break;
                    case BLUE: count.addColours(0,0,1,0);
                               break;
                    case PURPLE: count.addColours(0,0,0,1);
                                 break;
                }
            }
        }
    }
}
