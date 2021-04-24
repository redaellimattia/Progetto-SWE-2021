package it.polimi.ingsw.model;


import java.util.Optional;

public class Production {
    private ResourceCount input;
    private ResourceCount output;

    /**
     *
     * @param input resources needed to start the production
     * @param output resources produced by the production
     */
    public Production(ResourceCount input, ResourceCount output) {
        this.input = input;
        this.output = output;
    }

    /**
     *
     * @return cost for the production
     */
    public ResourceCount getInput() {
        return input;
    }

    /**
     *
     * @return resources produced by the production
     */
    public ResourceCount getOutput() {
        return output;
    }
    /**
     *
     * @param count resources chosen by the player as payment for the production
     * @return output if the production is doable, null the other way
     */
    //CHECK IF PRODUCTION IS DOABLE WITH THE RESOURCES PASSED AND EVENTUALLY RETURNS THE OUTPUT;
    public Optional<ResourceCount> useProduction(ResourceCount count){
        if(input.equals(count))
            return Optional.of(output);
        else
            return Optional.empty();
    }
    /**
     *
     * @param o passed Object
     * @return true if o is the same object of this, or it has the same number of colours
     */
    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof Production)) //Check if o is instanceOf ResourceCount
            return false;

        //Check if same values
        Production p = (Production) o; //Cast to ResourceCount
        return this.input.equals(p.getInput()) && this.output.equals(p.getOutput());
    }
}
