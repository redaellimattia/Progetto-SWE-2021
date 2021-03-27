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

    public boolean isPossible(ResourceCount temp){ //True if production is possible
        //count is now equals to all the resources available to the player
        if(input.getCoins()<=temp.getCoins()&&input.getRocks()<=temp.getRocks()&&input.getServants()<=temp.getServants()&&input.getShields()<=temp.getShields())
            return true; //Va decrement count
        else
            return false;
    }
}
