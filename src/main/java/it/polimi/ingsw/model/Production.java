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

    public ResourceCount useProduction(ResourceCount chest, Storage storage) throws Exception{
        ResourceCount count = storage.readStorage(); //initialize count to the content of storage
        count.addResources(chest.getCoins(), chest.getRocks(), chest.getServants(), chest.getShields()); //add chest resources to count
        //count is now equals to all the resources available to the player
        if(input.getCoins()<=count.getCoins()&&input.getRocks()<=count.getRocks()&&input.getServants()<=count.getServants()&&input.getShields()<=count.getShields())
            return output;
        else
            throw new Exception();
    }
}
