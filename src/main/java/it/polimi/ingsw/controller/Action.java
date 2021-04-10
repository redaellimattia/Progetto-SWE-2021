package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;

public abstract class Action {
    public boolean deleteRes(){
        return true;
    }
    public boolean endAction(){
        return false;
    }
}
