package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;

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

    //CHECK IF PRODUCTION IS DOABLE WITH THE RESOURCES PASSED AND EVENTUALLY RETURNS THE OUTPUT;
    public ResourceCount useProduction(ResourceCount count){
        if(input.equals(count))
            return output;
        else
            return null;
    }
    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof Production)) //Check if o is instanceOf ResourceCount
            return false;

        //Check if same values
        Production p = (Production) o; //Cast to ResourceCount
        if(this.input.equals(p.getInput())&&this.output.equals(p.getOutput()))
            return true;
        return false;
    }
}
