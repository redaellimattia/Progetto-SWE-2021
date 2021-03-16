package it.polimi.ingsw.model;

public class ResourceRequirement extends Requirement{
    private ResourceCount resources;

    public ResourceRequirement(RequirementsType type,ResourceCount resources) {
        super(type);
        this.resources = resources;
    }

    public ResourceCount getResources() {
        return resources;
    }
}
