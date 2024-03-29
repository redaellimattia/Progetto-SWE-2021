package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceCountTest {

    @Test
    void removeSingleParameter(){
        ResourceCount count = new ResourceCount(1,2,4,3,0);
        ResourceCount result = new ResourceCount(0,0,0,0,0);
        count.removeCoins(1);
        count.removeRocks(2);
        count.removeServants(4);
        count.removeShields(3);
        assertEquals(count,result);
    }

    @Test
    void addSingleParameter(){
        ResourceCount count = new ResourceCount(0,0,0,0,0);
        ResourceCount result = new ResourceCount(1,2,4,3,0);
        count.addCoins(1);
        count.addRocks(2);
        count.addServants(4);
        count.addShields(3);
        assertEquals(count,result);
    }

    @Test
    void removeColours(){
        ResourceCount count = new ResourceCount(3,4,1,5,0);
        ResourceCount check = new ResourceCount(2,3,0,4,0);
        count.removeResources(1,1,1,1,0);
        assertEquals(check,count);
    }

    @Test
    void addResources() {
        ResourceCount count = new ResourceCount(3,4,1,5,0);
        ResourceCount check = new ResourceCount(4,5,2,6,1);
        count.addResources(1,1,1,1,1);
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
    @Test
    void subCounts(){
        ResourceCount count = new ResourceCount(5,5,5,5,5);
        ResourceCount sub = new ResourceCount(2,3,4,1,0);
        ResourceCount result = new ResourceCount(3,2,1,4,5);
        count.subCounts(sub);
        assertEquals(count,result);
        count.sumCounts(null);
        assertEquals(count,count);
    }
    @Test
    void hasMoreOrEqualsResources(){
        ResourceCount count = new ResourceCount(5,5,5,5,5);
        ResourceCount count2 = new ResourceCount(2,3,4,1,0);
        assertTrue(count.hasMoreOrEqualsResources(count2));
        assertFalse(count2.hasMoreOrEqualsResources(count));
        assertTrue(count.hasMoreOrEqualsResources(count));
    }

    @Test
    void getTotal(){
        ResourceCount rc1 = new ResourceCount(5,5,5,5,0);
        ResourceCount rc2 = new ResourceCount(2,3,4,1,0);
        ResourceCount result = new ResourceCount(7,8,9,6,0);
        assertEquals(rc2,ResourceCount.getTotal(null,rc2)); //Returns rc2 if rc1 = null
        assertEquals(rc1,ResourceCount.getTotal(rc1,null));//Returns rc1 if rc2 = null
        assertEquals(rc1,ResourceCount.getTotal(rc1,new ResourceCount(0,0,0,0,0))); //Doesnt sum up
        assertEquals(result,ResourceCount.getTotal(rc1,rc2)); //Correct total
        assertNull(ResourceCount.getTotal(null,null)); //Returns null if rc1 = null AND rc2 = null
    }
}