package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductionAbilityTest {

    @Test
    void useProductionAbility() {
        ProductionAbility prod;
        ResourceCount enough = new ResourceCount(2,2,2,2,0);
        ResourceCount notEnough = new ResourceCount(0,0,0,0,0);
        ResourceCount expected = new ResourceCount(1,2,2,2,0);
        //COIN
        prod = new ProductionAbility(Resource.COIN);
        assertTrue(prod.useProductionAbility(enough)); //Enough
        assertFalse(prod.useProductionAbility(null));
        assertFalse(prod.useProductionAbility(notEnough));
        assertEquals(expected,enough); //ResourceCount is Decreased
        //ROCK
        prod = new ProductionAbility(Resource.ROCK);
        expected.removeRocks(1);
        assertTrue(prod.useProductionAbility(enough)); //Enough
        assertFalse(prod.useProductionAbility(null));
        assertFalse(prod.useProductionAbility(notEnough));
        assertEquals(expected,enough); //ResourceCount is Decreased
        //SERVANT
        prod = new ProductionAbility(Resource.SERVANT);
        expected.removeServants(1);
        assertTrue(prod.useProductionAbility(enough)); //Enough
        assertFalse(prod.useProductionAbility(null));
        assertFalse(prod.useProductionAbility(notEnough));
        assertEquals(expected,enough); //ResourceCount is Decreased
        //SHIELD
        prod = new ProductionAbility(Resource.SHIELD);
        expected.removeShields(1);
        assertTrue(prod.useProductionAbility(enough)); //Enough
        assertFalse(prod.useProductionAbility(null));
        assertFalse(prod.useProductionAbility(notEnough));
        assertEquals(expected,enough); //ResourceCount is Decreased
    }
}