package it.polimi.ingsw.model.enumeration;

import it.polimi.ingsw.model.ResourceCount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {

    @Test
    void add() {
        ResourceCount count = new ResourceCount(1,2,3,4,0);
        //ADD COIN
        ResourceCount result = new ResourceCount(2,2,3,4,0);
        Resource type = Resource.COIN;
        type.add(count,1);
        assertEquals(count,result);
        //ADD ROCK
        count = new ResourceCount(1,2,3,4,0);
        result = new ResourceCount(1,3,3,4,0);
        type = Resource.ROCK;
        type.add(count,1);
        assertEquals(count,result);
        //ADD SERVANT
        count = new ResourceCount(1,2,3,4,0);
        result = new ResourceCount(1,2,4,4,0);
        type = Resource.SERVANT;
        type.add(count,1);
        assertEquals(count,result);
        //ADD SHIELD
        count = new ResourceCount(1,2,3,4,0);
        result = new ResourceCount(1,2,3,5,0);
        type = Resource.SHIELD;
        type.add(count,1);
        assertEquals(count,result);
    }

    @Test
    void remove() {
        ResourceCount count = new ResourceCount(1,2,3,4,0);
        //REMOVE COIN
        ResourceCount result = new ResourceCount(0,2,3,4,0);
        Resource type = Resource.COIN;
        type.remove(count,1);
        assertEquals(count,result);
        //REMOVE ROCK
        count = new ResourceCount(1,2,3,4,0);
        result = new ResourceCount(1,1,3,4,0);
        type = Resource.ROCK;
        type.remove(count,1);
        assertEquals(count,result);
        //REMOVE SERVANT
        count = new ResourceCount(1,2,3,4,0);
        result = new ResourceCount(1,2,2,4,0);
        type = Resource.SERVANT;
        type.remove(count,1);
        assertEquals(count,result);
        //REMOVE SHIELD
        count = new ResourceCount(1,2,3,4,0);
        result = new ResourceCount(1,2,3,3,0);
        type = Resource.SHIELD;
        type.remove(count,1);
        assertEquals(count,result);
    }

    @Test
    void get() {
        ResourceCount count = new ResourceCount(1,2,3,4,0);
        //GET COIN
        Resource type = Resource.COIN;
        assertSame(1,type.get(count));
        //GET ROCK
        count = new ResourceCount(1,2,3,4,0);
        type = Resource.ROCK;
        assertSame(2,type.get(count));
        //GET SERVANT
        count = new ResourceCount(1,2,3,4,0);
        type = Resource.SERVANT;
        assertSame(3,type.get(count));
        //GET SHIELD
        count = new ResourceCount(1,2,3,4,0);
        type = Resource.SHIELD;
        assertSame(4,type.get(count));
    }
}