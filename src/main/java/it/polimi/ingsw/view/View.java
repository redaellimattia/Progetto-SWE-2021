package it.polimi.ingsw.view;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.client.PlayerPoints;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;

import java.util.ArrayList;

public interface View {
    void start();
    void printLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies);
    void createNewGame();
    void joinExistingGame(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies);
    void printMsg(String msg);
    void preGameChoice(ArrayList<LeaderCard> leaders, int numberOfResources);
    void waitingForTurn();
    void yourTurn();
    void endGame(ArrayList<PlayerPoints> scoreboard);
    void endGame(boolean lorenzoWin,int playerPoints);
    void endTurn();
    void buyCard();
    void takeResourcesFromMarket();
    void startProduction();
    void leaderAction(ArrayList<LeaderCard> passedLeader,boolean isDiscard);
    void organizeResources();
    void vaticanReportActivated(int victoryPoints,ArrayList<String> nicknames);
    void clearView();
}