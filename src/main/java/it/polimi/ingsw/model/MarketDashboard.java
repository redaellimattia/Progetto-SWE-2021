package it.polimi.ingsw.model;

import it.polimi.ingsw.network.server.Observer;

public class MarketDashboard {
    private MarketMarble[][] structure;
    private MarketMarble freeMarble;
    private transient Observer observer;

    /**
     * Adds reference to the observer
     * @param observer ServerLobby that is observing the Market
     */
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    /**
     *
     * @param structure the matrix representing the MarketDashboard
     * @param freeMarble the marble that is not in the matrix
     */
    public MarketDashboard(MarketMarble[][] structure, MarketMarble freeMarble) {
        this.structure = structure;
        this.freeMarble = freeMarble;
    }

    /**
     * Sets the initial structure of the market
     * @param structure the market structure to use
     */
    public void setStructure(MarketMarble[][] structure) {
        this.structure = structure;
    }

    /**
     * Sets the free marble
     * @param freeMarble the marble that is not placed in the structure
     */
    public void setFreeMarble(MarketMarble freeMarble) {
        this.freeMarble = freeMarble;
    }

    /**
     *
     * @return the marble that is not in the matrix
     */
    public MarketMarble getFreeMarble(){
        return freeMarble;
    }

    /**
     *
     * @param type 0: row to be fixed; 1: column to be fixed
     * @param pos number of the row/column (starting from 1)
     */
    public void fixGrid(int type, int pos) throws IllegalArgumentException {
        MarketMarble temp;
        int cur;
        if(type == 0) {
            if(pos < 1 || pos > 3) {
                throw new IllegalArgumentException();
            }
            temp = structure[pos-1][0]; // the leftmost marble will be the new freeMarble
            for(cur = 0; cur < 3; cur++) {
                structure[pos-1][cur] = structure[pos-1][cur+1]; // left shift
            }
            structure[pos-1][3] = freeMarble;
        }
        else {
            if(pos < 1 || pos > 4) {
                throw new IllegalArgumentException();
            }
            temp = structure[0][pos-1]; // the first marble in the column will be the new freeMarble
            for(cur = 0; cur < 2; cur++) {
                structure[cur][pos-1] = structure[cur+1][pos-1];
            }
            structure[2][pos-1] = freeMarble;
        }
        freeMarble = temp;
        observer.updateMarket(structure,freeMarble);
    }

    /**
     *
     * @param row number of the row (starting from 1)
     * @return a copy of the selected row
     * @throws IllegalArgumentException if the selected row does not exist
     */
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

    /**
     *
     * @param col number of the column (starting from 1)
     * @return a copy of the selected column
     * @throws IllegalArgumentException if the selected column does not exist
     */
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

    /**
     *
     * @return a copy of the market structure
     */
    public MarketMarble[][] getStructure() {
        MarketMarble[][] marketCopy = new MarketMarble[3][4];
        int curRow, curCol;
        for(curRow = 0; curRow < 3; curRow++) {
            for(curCol = 0; curCol < 4; curCol++) {
                marketCopy[curRow][curCol] = structure[curRow][curCol];
            }
        }
        return marketCopy;
    }
}
