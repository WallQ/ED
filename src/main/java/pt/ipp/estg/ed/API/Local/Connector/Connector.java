package pt.ipp.estg.ed.API.Local.Connector;

import pt.ipp.estg.data.structures.Map.HashMap;
import pt.ipp.estg.data.structures.Map.MapADT;
import pt.ipp.estg.ed.API.Config.Config;
import pt.ipp.estg.ed.API.Local.Coordinates;
import pt.ipp.estg.ed.API.Local.Local;
import pt.ipp.estg.ed.API.Player.Player;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The `Connector` class represents a connector in the game.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 * @extends Local
 * @implements IConnector
 */
public class Connector extends Local implements IConnector {
    private final Integer energy;
    private final MapADT<Player, LocalDateTime> cooldown;
    private Boolean trap;

    public Connector() {
        super();
        this.energy = Config.CONNECTOR_AMOUNT_ENERGY_RECHARGEABLE;
        this.cooldown = new HashMap<>();
    }

    public Connector(Coordinates coordinates) {
        super(coordinates);
        this.energy = Config.CONNECTOR_AMOUNT_ENERGY_RECHARGEABLE;
        this.cooldown = new HashMap<>();
        this.trap = false;
    }

    public Connector(String id, Coordinates coordinates, Integer energy, MapADT<Player, LocalDateTime> cooldown, Boolean trap) {
        super(id, coordinates);
        this.energy = energy;
        this.cooldown = cooldown;
        this.trap = trap;
    }

    public Integer getEnergy() {
        return energy;
    }

    public MapADT<Player, LocalDateTime> getCooldown() {
        return cooldown;
    }

    public Boolean getTrap() {
        return trap;
    }

    public void setTrap(Boolean trap) {
        this.trap = trap;
    }

    /**
     * Recharges the player's energy by the amount of energy that the connector has.
     * If the player already has the maximum energy, the recharge will not be done.
     * If the player is on cooldown, the recharge will not be done.
     *
     * @param player The player to recharge the energy
     */
    public void rechargePlayerEnergy(Player player) {
        if (Objects.isNull(player)) throw new IllegalArgumentException("The player cannot be null.");

        if (Objects.equals(player.getEnergy(), player.getMaxEnergy())) {
            System.out.println("The player " + player.getName() + " tried to recharge energy but he already has the maximum energy.");
            return;
        }

        if (this.isPlayerInCooldown(player)) {
            System.out.println("The player " + player.getName() + " tried to recharge energy but he is in cooldown for " + Duration.between(LocalDateTime.now(), this.cooldown.get(player)).toSeconds() + " seconds.");
            return;
        }

        player.increaseEnergy(this.energy);
        player.increaseExperience(Config.PLAYER_RECHARGE_ENERGY_EXPERIENCE);
        cooldown.put(player, LocalDateTime.now().plusSeconds(Config.CONNECTOR_COOLDOWN_IN_SECONDS));

        System.out.println("The player " + player.getName() + " recharged " + this.energy + " energy, having now " + Math.round(player.getEnergy() * 100.0) / 100.0 + " energy.");
    }

    /**
     * Verifies if the player's is on cooldown.
     * If the player is on cooldown, the recharge will not be done.
     * If the player is not on cooldown, the recharge will be done.
     *
     * @param player The player to verify if he is on cooldown
     */
    private boolean isPlayerInCooldown(Player player) {
        if (!cooldown.containsKey(player)) return false;

        if (LocalDateTime.now().isBefore(cooldown.get(player))) return true;

        cooldown.remove(player);

        return false;
    }

    /**
     * Reduces the player's energy by a percentage of the amount of energy that the connector has.
     * If the connector does not have a trap, the ambush will not be done.
     *
     * @param player The player to be ambushed
     */
    public void ambushPlayer(Player player) {
        if (Objects.isNull(player)) throw new IllegalArgumentException("The player cannot be null.");

        if (!this.trap) return;

        int energyLost = (int) (player.getEnergy() * Config.CONNECTOR_AMOUNT_PUNISH_ENERGY);

        player.decreaseEnergy(energyLost);
        setTrap(false);

        System.out.println("The player " + player.getName() + " was ambushed and lost " + Math.round(energyLost * 100.0) / 100.0 + " energy, having now " + Math.round(player.getEnergy() * 100.0) / 100.0 + " energy.");
    }

    public String toString() {
        return "Connector{" +
                "energy=" + energy +
                ", cooldown=" + cooldown +
                ", trap=" + trap +
                '}';
    }
}
