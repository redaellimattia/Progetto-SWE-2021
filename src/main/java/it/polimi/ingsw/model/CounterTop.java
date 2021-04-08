package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;

public class CounterTop {
    private Resource resourceType;
    private int content;

    public CounterTop(Resource resourceType, int content) {
        this.resourceType = resourceType;
        this.content = content;
    }
    //GETTERS
    public Resource getResourceType() {
        return resourceType;
    }
    public int getContent() {
        return content;
    }

    //SETTERS
    public void setResourceType(Resource resourceType) {
        this.resourceType = resourceType;
    }
    public void setContent(int content) {
        this.content = content;
    }

    //ADD ONE ELEMENT TO THE SHELF;
    public void addContent(int n){
        this.content += n;
    }

    //REMOVE ONE ELEMENT FROM THE SHELF;
    public void removeContent(int n){
        this.content -= n;
    }

}
