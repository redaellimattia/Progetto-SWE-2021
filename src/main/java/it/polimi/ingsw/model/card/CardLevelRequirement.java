package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.DeckDashboard;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.CardColour;

import java.util.ArrayList;

public class CardLevelRequirement implements Requirement {
    private CardColour colour;
    private int level; //Assuming level goes from 1 to 3

    public CardLevelRequirement(CardColour colour, int level) {
        this.colour = colour;
        this.level = level;
    }

    @Override
    //TRUE IF THE PLAYER CAN ACTUALLY PLAY THE CARD
    public boolean isPlayable(PlayerDashboard player){
        DeckDashboard devCards[] = player.getDevCards();
        ArrayList<DevelopmentCard> devCardSlot;
        CardColour devCardColour;
        int devCardLevel;
        for(int i=0;i<devCards.length;i++){ //Reading devCards array
            devCardSlot = devCards[i].getDeck();
            for(int j=0;j<devCardSlot.size();j++){ //Reading the devCardSlot(Reading single arrayList slot)
                devCardColour = devCardSlot.get(j).getColour(); //Reading devCard Colour
                devCardLevel = devCardSlot.get(j).getLevel(); //Reading devCard Level
                if(devCardColour.equals(this.colour)&&devCardLevel==this.level)
                    return true;
            }
        }
        return false;
    }
}
