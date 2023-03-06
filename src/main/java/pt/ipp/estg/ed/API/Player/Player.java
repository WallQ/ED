package pt.ipp.estg.ed.API.Player;

import pt.ipp.estg.ed.API.Config.Config;
import pt.ipp.estg.ed.API.Local.Local;
import pt.ipp.estg.ed.API.Team.Team;

import java.util.Objects;
import java.util.UUID;

import static pt.ipp.estg.ed.API.Utils.Calcs.calculateExperience;

/**
 * The `Team` class represents a player.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 * @implements IPlayer
 */
public class Player implements IPlayer {
    private String id;
    private String name;
    private Team team;
    private Integer level;
    private Integer currentExperience;
    private Integer totalExperience;
    private Integer energy;
    private Integer maxEnergy;
    private Integer conqueredPortals;
    private Local currentLocal;

    public Player() {
    }

    public Player(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.team = null;
        this.level = Config.PLAYER_INITIAL_LEVEL;
        this.currentExperience = Config.PLAYER_INITIAL_EXPERIENCE;
        this.totalExperience = Config.PLAYER_INITIAL_EXPERIENCE;
        this.energy = Config.PLAYER_INITIAL_ENERGY;
        this.maxEnergy = Config.PLAYER_INITIAL_MAX_ENERGY;
        this.conqueredPortals = 0;
        this.currentLocal = null;
    }

    public Player(String id, String name, Team team, Integer level, Integer currentExperience, Integer totalExperience, Integer energy, Integer maxEnergy, Integer conqueredPortals, Local currentLocal) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.level = level;
        this.currentExperience = currentExperience;
        this.totalExperience = totalExperience;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.conqueredPortals = conqueredPortals;
        this.currentLocal = currentLocal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCurrentExperience() {
        return currentExperience;
    }

    public void setCurrentExperience(Integer currentExperience) {
        this.currentExperience = currentExperience;
    }

    public Integer getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(Integer totalExperience) {
        this.totalExperience = totalExperience;
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

    public Integer getConqueredPortals() {
        return conqueredPortals;
    }

    public void setConqueredPortals(Integer conqueredPortals) {
        this.conqueredPortals = conqueredPortals;
    }

    public Local getCurrentLocal() {
        return currentLocal;
    }

    public void setCurrentLocal(Local currentLocal) {
        this.currentLocal = currentLocal;
    }

    /**
     * Increases the player's experience by the given amount.
     * If the player's experience is greater than the experience needed to level up, the player's level is increased.
     *
     * @param experience The experience to increase
     */
    public void increaseExperience(Integer experience) {
        if (Objects.isNull(experience)) throw new IllegalArgumentException("The experience cannot be null.");

        this.currentExperience += experience;
        this.totalExperience += experience;

        if (this.currentExperience >= calculateExperience(this.level)) {
            increaseLevel();
        }
    }

    /**
     * Increases the player's level by 1.
     * If the player's level is already the maximum level, nothing happens.
     */
    public void increaseLevel() {
        if (Objects.equals(this.level, Config.PLAYER_MAX_LEVEL)) return;
        this.level++;
        this.maxEnergy += Config.PLAYER_MAX_ENERGY_PER_LEVEL;
    }

    /**
     * Increases the player's energy by the given amount.
     * If the player's energy is greater than the maximum energy, the player's energy is set to the maximum energy.
     */
    public void increaseEnergy(Integer energy) {
        if (Objects.isNull(energy)) throw new IllegalArgumentException("The energy cannot be null.");

        this.energy += energy;
        if (this.energy >= this.maxEnergy) this.energy = this.maxEnergy;
    }

    /**
     * Decreases the player's energy by the given amount.
     * If the player's energy is less than 0, the player's energy is set to 0.
     */
    public void decreaseEnergy(Integer energy) {
        if (Objects.isNull(energy)) throw new IllegalArgumentException("The energy cannot be null.");

        this.energy -= energy;
        if (this.energy <= 0) this.energy = 0;
    }

    /**
     * Increases the player's conquered portals by 1.
     */
    public void increaseConqueredPortals() {
        this.conqueredPortals++;
    }

    /**
     * Decreases the player's conquered portals by 1.
     * If the player's conquered portals is less than 0, the player's conquered portals is set to 0.
     */
    public void decreaseConqueredPortals() {
        this.conqueredPortals--;
        if (this.conqueredPortals < 0) this.conqueredPortals = 0;
    }

    /**
     * Changes the player's current local.
     */
    public void changeLocal(Local local) {
        if (Objects.isNull(local)) throw new IllegalArgumentException("Local cannot be null");

        this.currentLocal = local;
    }

    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", team=" + team +
                ", level=" + level +
                ", currentExperience=" + currentExperience +
                ", totalExperience=" + totalExperience +
                ", energy=" + energy +
                ", maxEnergy=" + maxEnergy +
                ", conqueredPortals=" + conqueredPortals +
                ", currentLocal=" + currentLocal +
                '}';
    }
}
