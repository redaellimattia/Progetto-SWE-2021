package it.polimi.ingsw.view.gui;

import java.util.ArrayList;

public interface GuiObserver {
    void updateShop();
    void updatePathPosition(String nickname);
    void updateLeaders(String nickname);
    void updateMarket();
    void updateArrayDeposits(String nickname);
    void updateChest(String nickname);
    void updateDevCards(String nickname);
    void updateStorage(String nickname);
    void updateVaticanReports();
    void updateBufferProduction(String nickname);
    void updateLogger(ArrayList<String> log);
}
