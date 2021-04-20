package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductionAbilityTest {

    @Test
    void useProductionAbilityWithCoin() { //COIN
        ProductionAbility prod;
        ResourceCount enough = new ResourceCount(1, 0, 0, 0, 0);
        ResourceCount notEnough = new ResourceCount(0, 0, 0, 0, 0);
        prod = new ProductionAbility(Resource.COIN);
        assertTrue(prod.useProductionAbility(enough)); //Enough
        assertFalse(prod.useProductionAbility(null));
        assertFalse(prod.useProductionAbility(notEnough));
    }

    @Test
    void useProductionAbilityWithRock() { //ROCK
        ProductionAbility prod;
        ResourceCount enough = new ResourceCount(1, 0, 0, 0, 0);
        ResourceCount notEnough = new ResourceCount(0, 0, 0, 0, 0);
        prod = new ProductionAbility(Resource.ROCK);
        enough = new ResourceCount(0,1,0,0,0);
        assertTrue(prod.useProductionAbility(enough)); //Enough
        assertFalse(prod.useProductionAbility(null));
        assertFalse(prod.useProductionAbility(notEnough));
    }

    @Test
    void useProductionAbilityWithServant() { //SERVANT
        ProductionAbility prod;
        ResourceCount enough = new ResourceCount(1, 0, 0, 0, 0);
        ResourceCount notEnough = new ResourceCount(0, 0, 0, 0, 0);
        prod = new ProductionAbility(Resource.SERVANT);
        enough = new ResourceCount(0,0,1,0,0);
        assertTrue(prod.useProductionAbility(enough)); //Enough
        assertFalse(prod.useProductionAbility(null));
        assertFalse(prod.useProductionAbility(notEnough));
    }

    @Test
    void useProductionAbilityWithShield() { //SHIELD
        ProductionAbility prod;
        ResourceCount enough = new ResourceCount(1, 0, 0, 0, 0);
        ResourceCount notEnough = new ResourceCount(0, 0, 0, 0, 0);
        prod = new ProductionAbility(Resource.SHIELD);
        enough = new ResourceCount(0,0,0,1,0);
        assertTrue(prod.useProductionAbility(enough)); //Enough
        assertFalse(prod.useProductionAbility(null));
        assertFalse(prod.useProductionAbility(notEnough));
    }
}