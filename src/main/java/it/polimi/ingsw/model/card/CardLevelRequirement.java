package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.CardColour;
public class CardLevelRequirement implements Requirement {
    private CardColour colour;
    private int level;
    private int number;

    public CardLevelRequirement(CardColour colour, int level, int number) {
        this.colour = colour;
        this.level = level;
        this.number = number;
    }

    @Override
    public boolean isPlayable(PlayerDashboard player){
        return true;
    }
}
