package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;

public class ResourceRequirement implements Requirement {
    private ResourceCount resources;

    public ResourceRequirement(ResourceCount resources) {
        this.resources = resources;
    }

    @Override
    public boolean isPlayable() {
        return true;
    }
}
