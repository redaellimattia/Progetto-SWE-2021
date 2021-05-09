package it.polimi.ingsw.view;

import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;

import java.util.ArrayList;

public interface View {
    void start();
    void printLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies);
    void createNewGame();
    void joinExistingGame(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies);
    void printMsg(String msg);
}