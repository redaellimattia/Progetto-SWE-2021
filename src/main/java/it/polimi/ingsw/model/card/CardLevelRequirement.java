package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.DeckDashboard;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.CardColour;

public class CardLevelRequirement implements Requirement {
    private CardColour colour;
    private int level; //Assuming level goes from 1 to 3

    public CardLevelRequirement(CardColour colour, int level, int number) {
        this.colour = colour;
        this.level = level;
    }

    @Override
    public boolean isPlayable(PlayerDashboard player) throws IndexOutOfBoundsException{
        DeckDashboard devCards[] = player.getDevCards();
        CardColour devCardColour;
        for(int i=0;i<devCards.length;i++){
            try {
                devCardColour = devCards[i].getDeck().get(level - 1).getColour(); //Getting devCardLevel of card that level is level
                if(devCardColour.equals(colour))
                    return true;
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace(); //Return false here?
            }
        }
        return false;
    }
}
