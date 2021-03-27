package it.polimi.ingsw.model;

public class Production {
    private ResourceCount input;
    private ResourceCount output;

    public Production(ResourceCount input, ResourceCount output) {
        this.input = input;
        this.output = output;
    }

    public ResourceCount getInput() {
        return input;
    }

    public ResourceCount getOutput() {
        return output;
    }

    public boolean useProduction(ResourceCount count){ //True if production is possible
        if(input.getCoins()<=count.getCoins()&&input.getRocks()<=count.getRocks()&&
           input.getServants()<=count.getServants()&&input.getShields()<=count.getShields())
            return true;
        else
            return false;
    }
}
