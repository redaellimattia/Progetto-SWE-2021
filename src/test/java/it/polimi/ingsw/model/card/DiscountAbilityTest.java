package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountAbilityTest {

    @Test
    void useDiscountAbilityWithCoin() { //COIN
        ResourceCount cost = new ResourceCount(2, 2, 2, 2, 0);
        ResourceCount expected = new ResourceCount(1, 2, 2, 2, 0);
        DiscountAbility discountAbility = initDiscountAbility(Resource.COIN);
        assertTrue(discountAbility.useDiscountAbility(cost)); //Returns true and decreases cost
        assertEquals(expected, cost); //Check if cost is decreased
    }

    @Test
    void useDiscountAbilityWithRock() { //ROCK
        ResourceCount cost = new ResourceCount(1, 2, 2, 2, 0);
        ResourceCount expected = new ResourceCount(1, 1, 2, 2, 0);
        DiscountAbility discountAbility = initDiscountAbility(Resource.ROCK);
        assertTrue(discountAbility.useDiscountAbility(cost)); //Returns true and decreases cost
        assertEquals(expected,cost); //Check if cost is decreased
    }

    @Test
    void useDiscountAbilityWithServant() { //SERVANT
        ResourceCount cost = new ResourceCount(1, 1, 2, 2, 0);
        ResourceCount expected = new ResourceCount(1, 1, 1, 2, 0);
        DiscountAbility discountAbility = initDiscountAbility(Resource.SERVANT);
        assertTrue(discountAbility.useDiscountAbility(cost)); //Returns true and decreases cost
        assertEquals(expected,cost); //Check if cost is decreased
    }

    @Test
    void useDiscountAbilityWithShield() { //SHIELD
        ResourceCount cost = new ResourceCount(1, 1, 1, 2, 0);
        ResourceCount expected = new ResourceCount(1, 1, 1, 1, 0);
        DiscountAbility discountAbility = initDiscountAbility(Resource.SHIELD);
        assertTrue(discountAbility.useDiscountAbility(cost)); //Returns true and decreases cost
        assertEquals(expected,cost); //Check if cost is decreased
    }

    DiscountAbility initDiscountAbility(Resource res){
        return new DiscountAbility(res);
    }
}