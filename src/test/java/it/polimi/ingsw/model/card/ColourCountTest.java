package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColourCountTest {

    @Test
    void removeSingleParameter(){
        ColourCount count = new ColourCount(1,2,4,3);
        ColourCount result = new ColourCount(0,0,0,0);
        count.removeGreen(1);
        count.removeYellow(2);
        count.removeBlue(4);
        count.removePurple(3);
        assertEquals(count,result);
    }

    @Test
    void addSingleParameter(){
        ColourCount count = new ColourCount(0,0,0,0);
        ColourCount result = new ColourCount(1,2,4,3);
        count.addGreen(1);
        count.addYellow(2);
        count.addBlue(4);
        count.addPurple(3);
        assertEquals(count,result);
    }

    @Test
    void removeColours(){
        ColourCount count = new ColourCount(3,4,1,5);
        ColourCount check = new ColourCount(2,3,0,4);
        count.removeColours(1,1,1,1);
        assertEquals(check,count);
    }
    @Test
    void addColours() {
        ColourCount count = new ColourCount(3,4,1,5);
        ColourCount check = new ColourCount(4,5,2,6);
        count.addColours(1,1,1,1);
        assertEquals(check,count);
    }

    @Test
    void sumCounts(){
        ColourCount count = new ColourCount(1,2,3,4);
        ColourCount sum = new ColourCount(2,2,2,2);
        ColourCount result = new ColourCount(3,4,5,6);
        count.sumCounts(sum);
        assertEquals(count,result);
        count.sumCounts(null);
        assertEquals(count,count);
    }
}