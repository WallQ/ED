package pt.ipp.estg.ed.API.Player;

import java.util.Iterator;

/**
 * The `IPlayerManagement` interface defines a set of methods for managing players.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 */
public interface IPlayerManagement {
    Iterator<Player> getAllPlayers();

    Player getPlayerById(String id);

    Player getPlayerByName(String name);

    void addPlayer(Player player);

    void removePlayerById(String id);

    void removePlayerByName(String name);

    void sortById();

    void sortByName();

    void sortByLevel();

    void sortByConqueredPortals();

    boolean isEmpty();

    int size();
}
