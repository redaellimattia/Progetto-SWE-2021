package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;
import org.w3c.dom.css.Counter;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @Test
    void setFirstRow() {
        Storage storage = createStorage();
        CounterTop newFirst = new CounterTop(Resource.SERVANT, 1);

        storage.setFirstRow(newFirst);
        assertEquals(storage.getFirstRow(),newFirst);
    }

    @Test
    void setSecondRow() {
        Storage storage = createStorage();
        CounterTop newSecond = new CounterTop(Resource.SERVANT, 2);

        storage.setSecondRow(newSecond);
        assertEquals(storage.getSecondRow(),newSecond);
    }
    @Test
    void setSecondRowNotFull() {
        Storage storage = createStorage();
        CounterTop newSecond = new CounterTop(Resource.SERVANT, 1);

        storage.setSecondRow(newSecond);
        assertEquals(storage.getSecondRow(),newSecond);
    }
    @Test
    void setThirdRow() {
            Storage storage = createStorage();
            CounterTop newThird = new CounterTop(Resource.SERVANT, 3);

            storage.setThirdRow(newThird);
            assertEquals(storage.getThirdRow(),newThird);
    }
    @Test
    void setThirdRowNotFull() {
        Storage storage = createStorage();
        CounterTop newThird2 = new CounterTop(Resource.SERVANT, 2);

        storage.setThirdRow(newThird2);
        assertEquals(storage.getThirdRow(),newThird2);

        CounterTop newThird1 = new CounterTop(Resource.SERVANT, 1);

        storage.setThirdRow(newThird1);
        assertEquals(storage.getThirdRow(),newThird1);

        CounterTop newThird0 = new CounterTop(Resource.SERVANT, 0);

        storage.setThirdRow(newThird0);
        assertEquals(storage.getThirdRow(),newThird0);
    }

    @Test //BASIC ADD WORKS
    void addToFirstRow() {
        Storage storage = createStorage();

        storage.addToFirstRow(1);
        assertTrue(storage.getFirstRow().getContent() == 1);
    }
    @Test //
    void addToFirstRow1() {
        Storage storage = createStorage();
        storage.addToFirstRow(1);
        assertTrue(storage.addToFirstRow(1) == 1);
    }
    @Test
    void addToSecondRow() {
        Storage storage = createStorage();

        storage.addToSecondRow(2);
        assertTrue(storage.getSecondRow().getContent() == 2);
    }
    @Test
    void addToSecondRow1() {
        Storage storage = createStorage();

        storage.addToSecondRow(2);
        assertTrue(storage.getSecondRow().getContent() == 2);
        assertTrue(storage.addToSecondRow(1) == 1);
    }
    @Test
    void addToSecondRow2() {
        Storage storage = createStorage();

        storage.addToSecondRow(2);
        assertTrue(storage.getSecondRow().getContent() == 2);
        assertTrue(storage.addToSecondRow(3) == 3);
    }
    @Test
    void addToThirdRow() {
        Storage storage = createStorage();

        storage.addToThirdRow(3);
        assertTrue(storage.getThirdRow().getContent() == 3);
    }
    @Test
    void addToThirdRow1() {
        Storage storage = createStorage();

        storage.addToThirdRow(3);
        assertTrue(storage.getThirdRow().getContent() == 3);
        assertTrue(storage.addToThirdRow(1) == 1);
    }

    @Test
    void generalAddRemove(){
        Storage storage = createStorage();
        CounterTop newThird = new CounterTop(Resource.SERVANT, 3);
        storage.setThirdRow(newThird);
        assertTrue(storage.addToThirdRow(1) == 1);
        CounterTop newThird1 = new CounterTop(Resource.SERVANT, 1);
        storage.setThirdRow(newThird1);
        assertTrue(storage.getThirdRow().getContent() == 1);
        assertTrue(storage.addToThirdRow(2) == 0);
    }

    @Test
    void readStorage() {
        Storage storage = createStorage();
        CounterTop newFirst = new CounterTop(Resource.ROCK, 1);
        CounterTop newSecond = new CounterTop(Resource.SHIELD, 1);
        CounterTop newThird = new CounterTop(Resource.SERVANT, 3);
        storage.setFirstRow(newFirst);
        storage.setSecondRow(newSecond);
        storage.setThirdRow(newThird);
        ResourceCount check = new ResourceCount(0,1,3,1,0);
        assertEquals(storage.readStorage(),check);
    }

    public Storage createStorage(){
        Resource coins = Resource.COIN;
        Resource rocks = Resource.ROCK;
        Resource shields = Resource.SHIELD;
        CounterTop firstRow = new CounterTop(coins,0);
        CounterTop secondRow = new CounterTop(rocks,0);
        CounterTop thirdRow = new CounterTop(shields,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);

        return storage;
    }
    @Test
    void getShelvesArray(){
        Storage storage = createStorage();
        CounterTop[] array = storage.getShelvesArray();
        storage.addToFirstRow(1);
        assertEquals(storage.getFirstRow(),array[0]);
        assertEquals(storage.getSecondRow(),array[1]);
        assertEquals(storage.getThirdRow(),array[2]);

    }
}