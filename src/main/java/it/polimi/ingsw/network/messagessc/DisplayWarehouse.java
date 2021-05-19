package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class DisplayWarehouse extends Message {
    private final SerializableWarehouse warehouse;

    public DisplayWarehouse(SerializableWarehouse warehouse) {
        super("server", "client", Content.DISPLAY_WAREHOUSE);
        this.warehouse = warehouse;
    }

    public SerializableWarehouse getWarehouse() {
        return warehouse;
    }
}
