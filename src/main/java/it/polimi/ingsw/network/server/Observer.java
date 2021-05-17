package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;

public interface Observer {
    void updateShop(DeckShop[][] shopGrid); //Shop update
    void updateMarket(MarketMarble[][] structure,MarketMarble freeMarble); //Market update


    //PlayerUpdate
    void updateChest(String nickname,ResourceCount chest); //Chest update
    void updateBufferProduction(String nickname,ResourceCount chest); //BufferProduction update
    void updateArrayDeposit(String nickname, ArrayList<CounterTop> arrayDeposit);
    void updateInitArrayDeposit(String nickname, Resource res);
    void updateDevCards(String nickname, DevelopmentCard card, int position);
    void updateRemoveLeader(String nickname, int position);
    void updatePathPosition(PlayerDashboard player, int position);
    void updateInGameLeader(String nickname, int position);
    void updateFirstRow(String nickname,CounterTop firstRow);
    void updateSecondRow(String nickname,CounterTop secondRow);
    void updateThirdRow(String nickname,CounterTop thirdRow);
    void updateVictoryPoints(String nickname,int points);
    void updateVaticanReport(int victoryPoints,ArrayList<String> nicknames);
    void updateStartGame();
    void updateEndGame();
    void setGameMustEnd();
}
