package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CounterTopOverloadException;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.ServerLobby;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @Test
    void setFirstRow() throws CounterTopOverloadException{
        Storage storage = createStorage();
        CounterTop newFirst = new CounterTop(Resource.SERVANT, 1);
        storage.setFirstRow(newFirst);
        assertEquals(storage.getFirstRow(),newFirst);
    }

    @Test
    void setSecondRow() throws CounterTopOverloadException{
        Storage storage = createStorage();
        CounterTop newSecond = new CounterTop(Resource.SERVANT, 2);

        storage.setSecondRow(newSecond);
        assertEquals(storage.getSecondRow(),newSecond);
    }
    @Test
    void setSecondRowNotFull() throws CounterTopOverloadException{
        Storage storage = createStorage();
        CounterTop newSecond = new CounterTop(Resource.SERVANT, 1);

        storage.setSecondRow(newSecond);
        assertEquals(storage.getSecondRow(),newSecond);
    }
    @Test
    void setThirdRow() throws CounterTopOverloadException{
        Storage storage = createStorage();
        CounterTop newThird = new CounterTop(Resource.SERVANT, 3);

        storage.setThirdRow(newThird);
        assertEquals(storage.getThirdRow(),newThird);
    }
    @Test
    void setThirdRowNotFull() throws CounterTopOverloadException{
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
        assertEquals(1, storage.getFirstRow().getContent());
    }
    @Test //
    void addToFirstRow1() {
        Storage storage = createStorage();
        storage.addToFirstRow(1);
        assertEquals(1, storage.addToFirstRow(1));
    }
    @Test
    void addToSecondRow() {
        Storage storage = createStorage();

        storage.addToSecondRow(2);
        assertEquals(2, storage.getSecondRow().getContent());
    }
    @Test
    void addToSecondRow1() {
        Storage storage = createStorage();

        storage.addToSecondRow(2);
        assertEquals(2, storage.getSecondRow().getContent());
        assertEquals(1, storage.addToSecondRow(1));
    }
    @Test
    void addToSecondRow2() {
        Storage storage = createStorage();

        storage.addToSecondRow(2);
        assertEquals(2, storage.getSecondRow().getContent());
        assertEquals(3, storage.addToSecondRow(3));
    }
    @Test
    void addToThirdRow() {
        Storage storage = createStorage();

        storage.addToThirdRow(3);
        assertEquals(3, storage.getThirdRow().getContent());
    }
    @Test
    void addToThirdRow1() {
        Storage storage = createStorage();

        storage.addToThirdRow(3);
        assertEquals(3, storage.getThirdRow().getContent());
        assertEquals(1, storage.addToThirdRow(1));
    }

    @Test
    void generalAddRemove() throws CounterTopOverloadException{
        Storage storage = createStorage();
        CounterTop newThird = new CounterTop(Resource.SERVANT, 3);
        storage.setThirdRow(newThird);
        assertEquals(1, storage.addToThirdRow(1));
        CounterTop newThird1 = new CounterTop(Resource.SERVANT, 1);
        storage.setThirdRow(newThird1);
        assertEquals(1, storage.getThirdRow().getContent());
        assertEquals(0, storage.addToThirdRow(2));
    }

    @Test
    void readStorage() throws CounterTopOverloadException{
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

    @Test
    void swap(){
        Storage storage= createStorage();
        Resource coins = Resource.COIN;
        Resource rocks = Resource.ROCK;
        storage.swap(0,1);
        assertEquals(rocks,storage.getFirstRow().getResourceType());
        assertEquals(coins,storage.getSecondRow().getResourceType());
    }
    @Test
    void swapSecondCase(){
        Storage storage= createStorage();
        Resource shields = Resource.SHIELD;
        Resource rocks = Resource.ROCK;
        storage.addToFirstRow(1);
        storage.addToSecondRow(2);
        storage.addToThirdRow(1);
        storage.swapRows(2,3);
        assertEquals(rocks,storage.getThirdRow().getResourceType());
        assertEquals(shields,storage.getSecondRow().getResourceType());
    }
    PlayerDashboard createPlayer(){
        String nickname = "Prova";
        CounterTop firstRow = new CounterTop(Resource.COIN,1);
        CounterTop secondRow = new CounterTop(Resource.ROCK,2);
        CounterTop thirdRow = new CounterTop(Resource.SERVANT,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        ServerLobby playerObserver = new ServerLobby(2,1);
        ResourceCount chest = new ResourceCount(5,5,0,0,0);
        DeckDashboard[] devCards = new DeckDashboard[3];

        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        PlayerDashboard player = new PlayerDashboard(storage,chest,devCards,leaderCards,nickname,2,false);
        player.addObserver(playerObserver);
        player.getStorage().addObserver(player);
        return player;
    }
    public Storage createStorage(){
        PlayerDashboard player = createPlayer();
        Resource coins = Resource.COIN;
        Resource rocks = Resource.ROCK;
        Resource shields = Resource.SHIELD;
        CounterTop firstRow = new CounterTop(coins,0);
        CounterTop secondRow = new CounterTop(rocks,0);
        CounterTop thirdRow = new CounterTop(shields,0);
        Storage storage = new Storage(firstRow,secondRow,thirdRow);
        storage.addObserver(player);
        return storage;
    }
    @Test
    void getShelvesArray(){
        Storage storage = createStorage();
        ArrayList<CounterTop> array = storage.getShelvesArray();
        storage.addToFirstRow(1);
        assertEquals(storage.getFirstRow(),array.get(0));
        assertEquals(storage.getSecondRow(),array.get(1));
        assertEquals(storage.getThirdRow(),array.get(2));

    }
}