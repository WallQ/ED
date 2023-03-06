package pt.ipp.estg.ed.API.Player;

import pt.ipp.estg.ed.API.Local.Local;

/**
 * The `IPlayer` interface defines a set of methods for a player.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 */
public interface IPlayer {
    void increaseExperience(Integer experience);

    void increaseLevel();

    void increaseEnergy(Integer energy);

    void decreaseEnergy(Integer energy);

    void increaseConqueredPortals();

    void decreaseConqueredPortals();

    void changeLocal(Local local);
}
