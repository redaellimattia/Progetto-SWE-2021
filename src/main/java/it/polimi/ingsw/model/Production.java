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

    public ResourceCount useProduction(ResourceCount count){ //Activate production and return output;
        if(input.getCoins()<=count.getCoins()&&input.getRocks()<=count.getRocks()&&
           input.getServants()<=count.getServants()&&input.getShields()<=count.getShields()) {
            count.removeResources(input.getCoins(), input.getRocks(), input.getServants(), input.getShields());
            return output;
        }
        else
            return null;
    }

}
