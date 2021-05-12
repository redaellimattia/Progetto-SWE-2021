package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.MarbleColour;
//import org.junit.Test;
import it.polimi.ingsw.network.server.ServerThread;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketDashboardTest {

    MarketDashboard buildDashboard() {
        MarketMarble[][] testStructure = new MarketMarble[3][4];
        ServerThread marketObserver = new ServerThread(2);
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
        MarketDashboard market = new MarketDashboard(testStructure, testFreeMarble);
        market.addObserver(marketObserver);
        return market;
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

    @Test
    void getColumn() {
        MarketDashboard testDashboard = buildDashboard();
        MarketMarble[] testColumn = new MarketMarble[3];
        testColumn[0] = new MarketMarble(MarbleColour.PURPLE);
        testColumn[1] = new MarketMarble(MarbleColour.WHITE);
        testColumn[2] = new MarketMarble(MarbleColour.WHITE);
        for(int i=0; i<3; i++) {
            assertEquals(testDashboard.getColumn(1)[i].getColour(), testColumn[i].getColour());
        }
    }

    @Test
    void fixGridRow() {
        MarketDashboard testDashboard = buildDashboard();
        assertEquals(testDashboard.getFreeMarble().getColour(), MarbleColour.BLUE);
        testDashboard.fixGrid(0, 1);
        assertEquals(testDashboard.getFreeMarble().getColour(), MarbleColour.PURPLE);
        assertEquals(testDashboard.getRow(1)[0].getColour(), MarbleColour.BLUE);
        assertEquals(testDashboard.getRow(1)[1].getColour(), MarbleColour.PURPLE);
        assertEquals(testDashboard.getRow(1)[2].getColour(), MarbleColour.GREY);
        assertEquals(testDashboard.getRow(1)[3].getColour(), MarbleColour.BLUE);
    }

    @Test
    void fixGridCol() {
        MarketDashboard testDashboard = buildDashboard();
        assertEquals(testDashboard.getFreeMarble().getColour(), MarbleColour.BLUE);
        testDashboard.fixGrid(1, 1);
        assertEquals(testDashboard.getFreeMarble().getColour(), MarbleColour.PURPLE);
        assertEquals(testDashboard.getColumn(1)[0].getColour(), MarbleColour.WHITE);
        assertEquals(testDashboard.getColumn(1)[1].getColour(), MarbleColour.WHITE);
        assertEquals(testDashboard.getColumn(1)[2].getColour(), MarbleColour.BLUE);
    }
}