package it.polimi.ingsw.model;

public abstract class Requirement {
    private RequirementsType type;

    public Requirement(RequirementsType type) {
        this.type = type;
    }

    public RequirementsType getType() {
        return type;
    }
}
