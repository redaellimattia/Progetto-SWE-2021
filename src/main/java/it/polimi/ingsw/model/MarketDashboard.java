package it.polimi.ingsw.model;

public class MarketDashboard {
    private MarketMarble[][] structure = new MarketMarble[3][4];
    private MarketMarble freeMarble;

    public MarketMarble getFreeMarble(){
        return freeMarble;
    }

    // type = 0: row to be fixed; type = 1: column to be fixed
    // pos: number of the row/column (starting from 1)
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
            freeMarble = temp;
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
            freeMarble = temp;
        }
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

    public void swapStructure(){

    }

    public void convertChosen(){

    }
}
