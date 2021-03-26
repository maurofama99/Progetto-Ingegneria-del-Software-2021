package it.polimi.ingsw.model.resources;

import java.util.HashSet;

public class ResourceTray {
    private HashSet<Resource> resources;

    public HashSet<Resource> getResources() {
        return resources;
    }

    public void setResources(HashSet<Resource> resources) {
        this.resources = resources;
    }
}
