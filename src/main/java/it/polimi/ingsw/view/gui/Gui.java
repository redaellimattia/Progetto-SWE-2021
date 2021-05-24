package it.polimi.ingsw.view.gui;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.client.PlayerPoints;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;


public class Gui implements View {

    @Override
    public void start() {

    }

    @Override
    public void printLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies) {

    }

    @Override
    public void createNewGame() {

    }

    @Override
    public void joinExistingGame(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies) {

    }

    @Override
    public void printMsg(String msg) {

    }

    @Override
    public void preGameChoice(ArrayList<LeaderCard> leaders, int numberOfResources) {

    }

    @Override
    public void waitingForTurn() {

    }

    @Override
    public void yourTurn() {

    }

    @Override
    public void endGame(ArrayList<PlayerPoints> scoreboard) {

    }

    @Override
    public void endGame(boolean lorenzoWin, int playerPoints) {

    }

    @Override
    public void endTurn() {

    }

    @Override
    public void buyCard() {

    }

    @Override
    public void takeResourcesFromMarket() {

    }

    @Override
    public void startProduction() {

    }

    @Override
    public void leaderAction(ArrayList<LeaderCard> passedLeader, boolean isDiscard) {

    }

    @Override
    public void organizeResources() {

    }

    @Override
    public void vaticanReportActivated(int victoryPoints, ArrayList<String> nicknames) {

    }

    @Override
    public void clearView() {

    }
}
