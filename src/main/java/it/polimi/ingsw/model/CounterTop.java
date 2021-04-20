package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.Resource;

public class CounterTop {
    private Resource resourceType;
    private int content;

    /**
     *
     * @param resourceType of this countertop
     * @param content number of elements contained
     */
    public CounterTop(Resource resourceType, int content) {
        this.resourceType = resourceType;
        this.content = content;
    }
    //GETTERS

    /**
     *
     * @return the resourceType of this CounterTop
     */
    public Resource getResourceType() {
        return resourceType;
    }

    /**
     *
     * @return number of elements contained
     */
    public int getContent() {
        return content;
    }

    //SETTERS

    /**
     *
     * @param resourceType set this CounterTop.resourceType
     */
    public void setResourceType(Resource resourceType) {
        this.resourceType = resourceType;
    }

    /**
     *
     * @param content set this CounterTop.content
     */
    public void setContent(int content) {
        this.content = content;
    }

    /**
     *
     * @param n elements to add to content
     */
    //ADD N ELEMENT TO THE SHELF;
    public void addContent(int n){
        this.content += n;
    }

    /**
     *
     * @param n elements to remove from content
     */
    //REMOVE N ELEMENT FROM THE SHELF;
    public void removeContent(int n){
        this.content -= n;
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
        if (!(o instanceof CounterTop)) //Check if o is instanceOf ResourceCount
            return false;

        //Check if same values
        CounterTop c = (CounterTop) o; //Cast to ResourceCount
        if(this.getResourceType()==c.getResourceType()&&this.getContent()==c.getContent())
            return true;
        return false;
    }
}
