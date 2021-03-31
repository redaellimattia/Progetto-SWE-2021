package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceCountTest {

    @Test
    void addResources() {
        ResourceCount count = new ResourceCount(3,4,1,5,0);
        ResourceCount check = new ResourceCount(4,5,2,6,0);
        count.addResources(1,1,1,1);
        //assertEquals(check,count);
        //assertSame(check.getCoins(),count.getCoins());
        assertEquals(5,count.getRocks());
        assertEquals(2,count.getServants());
        assertEquals(6,count.getShields());
        assertEquals(0,count.getFaith());
        //assertSame(check,count);
        //assertTrue(5>1);
    }
}