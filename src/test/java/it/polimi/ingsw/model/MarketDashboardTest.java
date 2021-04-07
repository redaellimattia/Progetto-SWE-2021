package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.MarbleColour;
//import org.junit.Test;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketDashboardTest {

    MarketDashboard buildDashboard() {
        MarketMarble[][] testStructure = new MarketMarble[3][4];
        testStructure[0][0] = new MarketMarble(MarbleColour.PURPLE);
        testStructure[0][1] = new MarketMarble(MarbleColour.BLUE);
        testStructure[0][2] = new MarketMarble(MarbleColour.PURPLE);
        testStructure[0][3] = new MarketMarble(MarbleColour.GREY);
        testStructure[1][0] = new MarketMarble(MarbleColour.WHITE);
        testStructure[1][1] = new MarketMarble(MarbleColour.RED);
        testStructure[1][2] = new MarketMarble(MarbleColour.WHITE);
        testStructure[1][3] = new MarketMarble(MarbleColour.WHITE);
        testStructure[2][0] = new MarketMarble(MarbleColour.WHITE);
        testStructure[2][1] = new MarketMarble(MarbleColour.GREY);
        testStructure[2][2] = new MarketMarble(MarbleColour.YELLOW);
        testStructure[2][3] = new MarketMarble(MarbleColour.YELLOW);
        MarketMarble testFreeMarble = new MarketMarble(MarbleColour.BLUE);
        MarketDashboard testDashboard = new MarketDashboard(testStructure, testFreeMarble);
        return testDashboard;
    }

    @Test
    void getRow() {
        MarketDashboard testDashboard = buildDashboard();
        MarketMarble[] testRow = new MarketMarble[4];
        testRow[0] = new MarketMarble(MarbleColour.PURPLE);
        testRow[1] = new MarketMarble(MarbleColour.BLUE);
        testRow[2] = new MarketMarble(MarbleColour.PURPLE);
        testRow[3] = new MarketMarble(MarbleColour.GREY);
        for(int i=0; i<4; i++) {
            assertEquals(testDashboard.getRow(1)[i].getColour(), testRow[i].getColour());
        }
    }
}