package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountAbilityTest {

    @Test
    void useDiscountAbility() {
        DiscountAbility discountAbility;
        ResourceCount cost = new ResourceCount(2,2,2,2,0);
        ResourceCount expected = new ResourceCount(1,2,2,2,0);
        //COIN
        discountAbility = new DiscountAbility(Resource.COIN);
        assertTrue(discountAbility.useDiscountAbility(cost)); //Returns true and decreases cost
        assertEquals(expected,cost); //Check if cost is decreased
        //ROCK
        discountAbility = new DiscountAbility(Resource.ROCK);
        expected.removeRocks(1);
        assertTrue(discountAbility.useDiscountAbility(cost)); //Returns true and decreases cost
        assertEquals(expected,cost); //Check if cost is decreased
        //SERVANT
        discountAbility = new DiscountAbility(Resource.SERVANT);
        expected.removeServants(1);
        assertTrue(discountAbility.useDiscountAbility(cost)); //Returns true and decreases cost
        assertEquals(expected,cost); //Check if cost is decreased
        //SHIELD
        discountAbility = new DiscountAbility(Resource.SHIELD);
        expected.removeShields(1);
        assertTrue(discountAbility.useDiscountAbility(cost)); //Returns true and decreases cost
        assertEquals(expected,cost); //Check if cost is decreased
    }
}