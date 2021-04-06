package it.polimi.ingsw.model.enumeration;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.Card;
import it.polimi.ingsw.model.card.ColourCount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardColourTest {
    @Test
    void add() {
        ColourCount count = new ColourCount(1,2,3,4);
        //ADD GREEN
        ColourCount result = new ColourCount(2,2,3,4);
        CardColour type = CardColour.GREEN;
        type.add(count,1);
        assertEquals(count,result);
        //ADD YELLOW
        count = new ColourCount(1,2,3,4);
        result = new ColourCount(1,3,3,4);
        type = CardColour.YELLOW;
        type.add(count,1);
        assertEquals(count,result);
        //ADD BLUE
        count = new ColourCount(1,2,3,4);
        result = new ColourCount(1,2,4,4);
        type = CardColour.BLUE;
        type.add(count,1);
        assertEquals(count,result);
        //ADD PURPLE
        count = new ColourCount(1,2,3,4);
        result = new ColourCount(1,2,3,5);
        type = CardColour.PURPLE;
        type.add(count,1);
        assertEquals(count,result);
    }

    @Test
    void remove() {
        ColourCount count = new ColourCount(1,2,3,4);
        //REMOVE GREEN
        ColourCount result = new ColourCount(0,2,3,4);
        CardColour type = CardColour.GREEN;
        type.remove(count,1);
        assertEquals(count,result);
        //REMOVE YELLOW
        count = new ColourCount(1,2,3,4);
        result = new ColourCount(1,1,3,4);
        type = CardColour.YELLOW;
        type.remove(count,1);
        assertEquals(count,result);
        //REMOVE BLUE
        count = new ColourCount(1,2,3,4);
        result = new ColourCount(1,2,2,4);
        type = CardColour.BLUE;
        type.remove(count,1);
        assertEquals(count,result);
        //REMOVE PURPLE
        count = new ColourCount(1,2,3,4);
        result = new ColourCount(1,2,3,3);
        type = CardColour.PURPLE;
        type.remove(count,1);
        assertEquals(count,result);
    }

    @Test
    void get() {
        ColourCount count = new ColourCount(1,2,3,4);
        //GET GREEN
        CardColour type = CardColour.GREEN;
        assertSame(1,type.get(count));
        //GET YELLOW
        count = new ColourCount(1,2,3,4);
        type = CardColour.YELLOW;
        assertSame(2,type.get(count));
        //GET BLUE
        count = new ColourCount(1,2,3,4);
        type = CardColour.BLUE;
        assertSame(3,type.get(count));
        //GET PURPLE
        count = new ColourCount(1,2,3,4);
        type = CardColour.PURPLE;
        assertSame(4,type.get(count));
    }

    @Test
    void getColumn() {
        CardColour type = CardColour.GREEN;
        assertSame(0,type.getColumn());
        type = CardColour.BLUE;
        assertSame(1,type.getColumn());
        type = CardColour.YELLOW;
        assertSame(2,type.getColumn());
        type = CardColour.PURPLE;
        assertSame(3,type.getColumn());
    }

}