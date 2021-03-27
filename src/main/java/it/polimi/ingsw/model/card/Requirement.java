package it.polimi.ingsw.model.card;
import it.polimi.ingsw.model.PlayerDashboard;

public interface Requirement {

    public boolean isPlayable(PlayerDashboard player);
}
