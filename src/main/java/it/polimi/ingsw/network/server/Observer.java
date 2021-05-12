package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;

import java.util.ArrayList;

public interface Observer {
    void updateShop(Deck[][] shopGrid); //Shop update
    void updateMarket(MarketMarble[][] structure,MarketMarble freeMarble); //Market update


    //PlayerUpdate
    void updateChest(String nickname,ResourceCount chest); //Chest update
    void updateBufferProduction(String nickname,ResourceCount chest); //BufferProduction update
    void updateArrayDeposit(String nickname, ArrayList<CounterTop> arrayDeposit);
    void updateInitArrayDeposit(String nickname, CounterTop newCounterTop);
    void updateDevCards(String nickname, DevelopmentCard card, int position);
    void updateRemoveLeader(String nickname, int position);
    void updatePathPosition(String nickname,int position);
    void updateInGameLeader(String nickname, int position);
    void updateFirstRow(String nickname,CounterTop firstRow);
    void updateSecondRow(String nickname,CounterTop secondRow);
    void updateThirdRow(String nickname,CounterTop thirdRow);
}
