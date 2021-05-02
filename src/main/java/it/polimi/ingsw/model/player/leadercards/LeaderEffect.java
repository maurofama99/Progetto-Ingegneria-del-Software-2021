package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.player.Player;

public abstract class LeaderEffect {

    private EffectType effectType;

    public LeaderEffect(EffectType effectType) {
        this.effectType = effectType;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    /**
     * Special getter
     * @return a characteristic of the leader card effect.
     */
    public abstract Object getObject();

    /**
     * Checks if the player can actually place the leader card
     * @param player owner of the card
     * @return true if the card can be placed, false if not
     */
    public abstract boolean checkRequirementsLeaderCard(Player player);


}
