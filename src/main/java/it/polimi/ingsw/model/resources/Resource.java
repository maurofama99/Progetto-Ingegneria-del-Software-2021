package it.polimi.ingsw.model.resources;

public class Resource {
    private int qnt;
    private ResourceType type;

    public Resource(int qnt, ResourceType type) {
        this.qnt = qnt;
        this.type = type;
    }

    @Override
    public String toString() {
        if (type.equals(ResourceType.SHIELD)) return "SHIELD";
        else if (type.equals(ResourceType.COIN)) return "COIN";
        else if (type.equals(ResourceType.STONE)) return "STONE";
        else if (type.equals(ResourceType.SERVANT)) return "SERVANT";
        else if (type.equals(ResourceType.FAITHPOINT)) return "FAITHPOINT";
        else if (type.equals(ResourceType.WHITERESOURCE)) return "WHITE";
        else return "Error in Resource toString()";
    }
}
