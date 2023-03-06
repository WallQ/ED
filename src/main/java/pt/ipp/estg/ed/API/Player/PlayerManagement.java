package pt.ipp.estg.ed.API.Player;

import pt.ipp.estg.algorithms.Sort.MergeSort;
import pt.ipp.estg.data.structures.List.UnorderedLinkedList;
import pt.ipp.estg.data.structures.List.UnorderedListADT;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

/**
 * The `PlayerManagement` class represents a set of methods for managing players.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 * @implements IPlayerManagement
 */
public class PlayerManagement implements IPlayerManagement {
    private final UnorderedListADT<Player> players;

    public PlayerManagement() {
        this.players = new UnorderedLinkedList<>();
    }

    public UnorderedListADT<Player> getPlayers() {
        return players;
    }

    public Iterator<Player> getAllPlayers() {
        return this.players.iterator();
    }

    public Player getPlayerById(String id) {
        for (Player player : this.players) {
            if (player.getId().equals(id)) {
                return player;
            }
        }

        return null;
    }

    public Player getPlayerByName(String name) {
        for (Player player : this.players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }

        return null;
    }

    public void addPlayer(Player player) {
        if (Objects.isNull(player)) throw new IllegalArgumentException("Player cannot be null!");
        this.players.addToRear(player);
    }

    public void removePlayerById(String id) {
        if (Objects.isNull(id)) throw new IllegalArgumentException("Player id cannot be null!");
        this.players.remove(getPlayerById(id));
    }

    public void removePlayerByName(String name) {
        if (Objects.isNull(name)) throw new IllegalArgumentException("Player name cannot be null!");
        this.players.remove(getPlayerByName(name));
    }

    public void sortById() {
        Player[] playersArray = getPlayersArray();

        MergeSort.sort(playersArray, Comparator.comparing(Player::getId));
    }

    public void sortByName() {
        Player[] playersArray = getPlayersArray();

        MergeSort.sort(playersArray, Comparator.comparing(Player::getName));
    }

    public void sortByLevel() {
        Player[] playersArray = getPlayersArray();

        MergeSort.sort(playersArray, Comparator.comparing(Player::getLevel));
    }

    public void sortByConqueredPortals() {
        Player[] playersArray = getPlayersArray();

        MergeSort.sort(playersArray, Comparator.comparing(Player::getConqueredPortals));
    }

    private Player[] getPlayersArray() {
        Player[] playersArray = new Player[this.players.size()];

        Iterator<Player> iterator = this.players.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            playersArray[i] = iterator.next();
            i++;
        }

        return playersArray;
    }

    public boolean isEmpty() {
        return this.players.isEmpty();
    }

    public int size() {
        return this.players.size();
    }

    public String toString() {
        return "PlayerManagement{" +
                "players=" + players +
                '}';
    }
}
