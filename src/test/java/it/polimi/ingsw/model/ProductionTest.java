package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductionTest {

    @Test //USEPRODUCTION DOES THE RIGHT CHECK ON THE RESOURCES PASSED
    void useProduction() {
        Production production = createProduction();
        ResourceCount enoughResources = new ResourceCount(1,0,2,0,0);
        assertEquals(production.useProduction(enoughResources), Optional.of(production.getOutput()));
    }
    @Test//RETURN EMPTY OPTIONAL IF THE RESOURCES AREN'T ENOUGH TO START THE PRODUCTION
    void useProduction1() {
        Production production = createProduction();
        ResourceCount enoughResources = new ResourceCount(0,0,2,0,0);
        assertTrue(production.useProduction(enoughResources).isEmpty());
    }

    Production createProduction(){
        ResourceCount input = new ResourceCount(1,0,2,0,0);
        ResourceCount output = new ResourceCount(1,1,1,1,0);
        return new Production(input,output);
    }
}