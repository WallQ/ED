package pt.ipp.estg.ed.API.Local.Portal;

import pt.ipp.estg.ed.API.Config.Config;
import pt.ipp.estg.ed.API.Local.Coordinates;
import pt.ipp.estg.ed.API.Local.Local;
import pt.ipp.estg.ed.API.Player.Player;
import pt.ipp.estg.ed.API.Team.Team;

import java.util.Objects;

/**
 * The `Portal` class represents a portal in the game.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 * @extends Local
 * @implements IPortal
 */
public class Portal extends Local implements IPortal {
    private String name;
    private Integer energy;
    private Integer maxEnergy;
    private Player conqueror;
    private Team team;

    public Portal() {
        super();
    }

    public Portal(String name, Coordinates coordinates) {
        super(coordinates);
        this.name = name;
        this.energy = Config.PORTAL_INITIAL_ENERGY;
        this.maxEnergy = Config.PORTAL_MAX_ENERGY;
        this.conqueror = null;
        this.team = null;
    }

    public Portal(String id, Coordinates coordinates, String name, Integer energy, Integer maxEnergy, Player conqueror, Team team) {
        super(id, coordinates);
        this.name = name;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.conqueror = conqueror;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEnergy() {
        return energy;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public Integer getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(Integer maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public Player getConqueror() {
        return conqueror;
    }

    public void setConqueror(Player conqueror) {
        this.conqueror = conqueror;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Conquers the portal.
     * If the portal is neutral, the player's will conquer it.
     * If the portal is from the player's team, the player's will not conquer it.
     * If the portal is from the player's enemy, the player's will attack it.
     *
     * @param player The player that will conquer the portal
     * @param energy The amount of energy that the player will use to conquer the portal
     */
    public void conquerPortal(Player player, Integer energy) {
        if (Objects.isNull(player)) throw new IllegalArgumentException("The player cannot be null.");
        if (Objects.isNull(energy)) throw new IllegalArgumentException("The energy cannot be null.");

        if (isTeamPortal(player)) {
            System.out.println("The player " + player.getName() + " tried to conquer the portal " + this.name + " but it's already his team's portal.");
            return;
        }

        if (player.getEnergy() < energy) {
            System.out.println("The player " + player.getName() + " tried to conquer the portal " + this.name + " with " + energy + "energy but he he only has " + Math.round(player.getEnergy() * 100.0) / 100.0 + " energy.");
            return;
        }

        int energyRequired = getEnergyRequired();

        if (energy == energyRequired) {
            resetPortal();
            player.decreaseEnergy(energy);
            player.increaseExperience(Config.PLAYER_ATTACK_PORTAL_EXPERIENCE);

            System.out.println("The player " + player.getName() + " attacked the portal " + this.name + " and made him neutral.");
        } else if (energy > energyRequired) {
            resetPortal();

            setEnergy(energy - energyRequired);
            setConqueror(player);
            setTeam(player.getTeam());

            player.decreaseEnergy(energy);
            player.increaseConqueredPortals();
            player.increaseExperience(Config.PLAYER_CONQUER_PORTAL_EXPERIENCE);

            System.out.println("The player " + player.getName() + " attacked the portal " + this.name + " and conquered him.");
        } else {
            decreaseEnergy(energy);
            player.decreaseEnergy(energy);
            player.increaseExperience(Config.PLAYER_ATTACK_PORTAL_EXPERIENCE);

            System.out.println("The player " + player.getName() + " attacked the portal " + this.name + " and weakened him, now " + this.name + " has " + this.energy + " energy.");
        }
    }

    /**
     * Gets the energy required to conquer the portal.
     *
     * @return The energy required to conquer the portal
     */
    private int getEnergyRequired() {
        return (int) (this.energy * Config.PORTAL_CONQUER_MIN_ENERGY_REQUIRED);
    }

    /**
     * Makes the portal neutral.
     */
    private void resetPortal() {
        if (Objects.nonNull(this.conqueror)) this.conqueror.decreaseConqueredPortals();
        setEnergy(0);
        setConqueror(null);
        setTeam(null);
    }

    /**
     * Charges the portal with the player's energy.
     * If the portal is neutral, the player's will not charge it.
     * If the portal is from the player's team, the player's will charge it.
     * If the portal is from the player's enemy, the player's will not charge it.
     *
     * @param energy The amount of energy that will be charged to the portal
     */
    public void chargePortal(Player player, Integer energy) {
        if (Objects.isNull(player)) throw new IllegalArgumentException("The player cannot be null.");
        if (Objects.isNull(energy)) throw new IllegalArgumentException("The energy cannot be null.");

        if (isNeutralPortal()) {
            System.out.println("The player " + player.getName() + " tried to charge the portal " + this.name + " but it's neutral.");
            return;
        }

        if (isEnemyPortal(player)) {
            System.out.println("The player " + player.getName() + " tried to charge the portal " + this.name + " but it's an enemy's portal.");
            return;
        }

        if (player.getEnergy() < energy) {
            System.out.println("The player " + player.getName() + " tried to charge the portal " + this.name + " with " + energy + "energy but he he only has " + Math.round(player.getEnergy() * 100.0) / 100.0 + " energy.");
            return;
        }

        increaseEnergy(energy);
        player.decreaseEnergy(energy);
        player.increaseExperience(Config.PLAYER_CHARGE_PORTAL_EXPERIENCE);

        System.out.println("The player " + player.getName() + " charged the portal " + this.name + " with " + energy + " energy.");
    }

    /**
     * Checks if the portal is neutral.
     *
     * @return True if the portal is neutral, false otherwise
     */
    private boolean isNeutralPortal() {
        return Objects.isNull(this.team) && Objects.isNull(this.conqueror);
    }

    /**
     * Checks if the portal is from the player's team.
     *
     * @param player The player that will be checked
     * @return True if the portal is from the player's team, false otherwise
     */
    private boolean isTeamPortal(Player player) {
        return Objects.nonNull(this.team) && this.team.equals(player.getTeam());
    }

    /**
     * Checks if the portal is from the player's enemy.
     *
     * @param player The player that will be checked
     * @return True if the portal is from the player's enemy, false otherwise
     */
    private boolean isEnemyPortal(Player player) {
        return Objects.nonNull(this.team) && !this.team.equals(player.getTeam());
    }

    /**
     * Increases the portal's energy.
     * If the portal's energy is greater than the max energy, the portal's energy will be set to the max energy.
     */
    private void increaseEnergy(Integer energy) {
        this.energy += energy;
        if (this.energy >= this.maxEnergy) this.energy = this.maxEnergy;
    }

    /**
     * Decreases the portal's energy.
     * If the portal's energy is less than 0, the portal's energy will be set to 0.
     */
    private void decreaseEnergy(Integer energy) {
        this.energy -= energy;
        if (this.energy <= 0) this.energy = 0;
    }

    public String toString() {
        return "Portal{" +
                "name='" + name + '\'' +
                ", energy=" + energy +
                ", maxEnergy=" + maxEnergy +
                ", conqueror=" + conqueror +
                ", team=" + team +
                '}';
    }
}
