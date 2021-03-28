package it.polimi.ingsw.model;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class CounterTop {
    private Resource resourceType;
    private int content;

    public Resource getResourceType() {
        return resourceType;
    }

    public int getContent() {
        return content;
    }

    public void setResourceType(Resource resourceType) {
        this.resourceType = resourceType;
    }

    public void setContent(int content) {
        this.content = content;
    }
}
