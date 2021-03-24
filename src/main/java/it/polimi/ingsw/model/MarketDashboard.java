package it.polimi.ingsw.model;

public class MarketDashboard {
    private MarketMarble[][] structure = new MarketMarble[3][4];
    private MarketMarble freeMarble;

    public MarketMarble getFreeMarble(){
        return freeMarble;
    }

    public void fixGrid(){

    }

    // Returns a copy of the selected row
    public MarketMarble[] getRow(int row) throws IllegalArgumentException {
        MarketMarble[] selectedRow = new MarketMarble[4];
        if(row < 1 || row > 3) {
            throw new IllegalArgumentException();
        }
        int cur;
        for(cur = 0; cur < 4; cur++) {
            selectedRow[cur] = structure[row-1][cur];
        }
        return selectedRow;
    }

    // Returns a copy of the selected column
    public MarketMarble[] getColumn(int col) throws IllegalArgumentException {
        MarketMarble[] selectedCol = new MarketMarble[3];
        if(col < 1 || col > 4) {
            throw new IllegalArgumentException();
        }
        int cur;
        for(cur = 0; cur < 3; cur++) {
            selectedCol[cur] = structure[cur][col-1];
        }
        return selectedCol;
    }

    // TO-DO: Return a copy of the structure instead of the structure itself
    public MarketMarble[][] getStructure() {
        return structure;
    }

    public void swapStructure(){

    }

    public void convertChosen(){

    }
}
