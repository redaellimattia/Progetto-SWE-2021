package it.polimi.ingsw.model;

public interface StorageObserver {
    void updateFirstRow(CounterTop firstRow);
    void updateSecondRow(CounterTop secondRow);
    void updateThirdRow(CounterTop thirdRow);
}
