package it.polimi.ingsw.model;

public class Storage {
    private Resource firstRow;
    private Resource[] secondRow = new Resource[2];
    private Resource[] thirdRow = new Resource[3];

    public Resource getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(Resource firstRow) {
        this.firstRow = firstRow;
    }

    public Resource[] getSecondRow() {
        return secondRow;
    }

    public void setSecondRow(Resource[] secondRow) {
        this.secondRow = secondRow;
    }

    public Resource[] getThirdRow() {
        return thirdRow;
    }

    public void setThirdRow(Resource[] thirdRow) {
        this.thirdRow = thirdRow;
    }
}
