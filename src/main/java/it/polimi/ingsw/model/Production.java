package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.enumeration.ResourceProduction;

import java.util.ArrayList;

public class Production {
    private ArrayList<Resource> input = new ArrayList<Resource>();
    private ArrayList<ResourceProduction> output = new ArrayList<ResourceProduction>();

    public Production(ArrayList<Resource> input, ArrayList<ResourceProduction> output) {
        this.input = input;
        this.output = output;
    }

    public ArrayList<Resource> getInput() {
        return input;
    }

    public ArrayList<ResourceProduction> getOutput() {
        return output;
    }
}
