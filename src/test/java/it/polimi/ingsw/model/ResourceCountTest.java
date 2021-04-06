package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceCountTest {

    @Test
    void addResources() {
        ResourceCount count = new ResourceCount(3,4,1,5,0);
        ResourceCount check = new ResourceCount(4,5,2,6,0);
        count.addResources(1,1,1,1);
        assertEquals(check,count);
    }

    @Test
    void sumCounts(){
        ResourceCount count = new ResourceCount(1,2,3,4,5);
        ResourceCount sum = new ResourceCount(2,2,2,2,0);
        ResourceCount result = new ResourceCount(3,4,5,6,5);
        count.sumCounts(sum);
        assertEquals(count,result);
        count.sumCounts(null);
        assertEquals(count,count);
    }
}