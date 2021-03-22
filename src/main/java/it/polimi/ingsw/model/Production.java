package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;

public class Production {
    private ArrayList<Resource> input = new ArrayList<Resource>();
    private ArrayList<Resource> output = new ArrayList<Resource>();

    public Production(ArrayList<Resource> input, ArrayList<Resource> output) {
        this.input = input;
        this.output = output;
    }

    public ArrayList<Resource> getInput() {
        return input;
    }

    public ArrayList<Resource> getOutput() {
        return output;
    }
}
