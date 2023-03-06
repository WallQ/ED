package pt.ipp.estg.ed.API.Management;

import pt.ipp.estg.data.structures.Graph.NetworkADT;
import pt.ipp.estg.ed.API.Game.Game;
import pt.ipp.estg.ed.API.Local.Connector.Connector;
import pt.ipp.estg.ed.API.Local.Local;
import pt.ipp.estg.ed.API.Player.Player;
import pt.ipp.estg.ed.API.Team.ITeamManagement;
import pt.ipp.estg.ed.API.Team.Team;

import java.util.Iterator;
import java.util.Objects;

import static pt.ipp.estg.ed.API.Utils.Input.readInteger;
import static pt.ipp.estg.ed.API.Utils.Input.readString;
import static pt.ipp.estg.ed.API.Utils.Menu.playerManagementMenu;

/**
 * The `PlayerManagement` class represents a set of methods for managing the players in the game.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 * @implements IPlayerManagement
 */
public class PlayerManagement implements IPlayerManagement {
    private final NetworkADT<Local> map;
    private final ITeamManagement teams;
    private final pt.ipp.estg.ed.API.Player.IPlayerManagement players;

    public PlayerManagement(Game game) {
        this.map = game.getMap();
        this.teams = game.getTeams();
        this.players = game.getPlayers();
    }

    /*
     * Player Management initialization.
     */
    public void manage() {
        Integer option = Integer.MAX_VALUE;
        while (option != 0) {
            System.out.println(playerManagementMenu());
            option = readInteger("Enter you option -> ", 0, 9);

            switch (option) {
                case 0 -> System.out.println("Exiting Local Management...");
                case 1 -> {
                    Iterator<Player> playerIterator = this.players.getAllPlayers();
                    while (playerIterator.hasNext()) {
                        Player player = playerIterator.next();
                        System.out.println(player);
                    }
                }
                case 2 -> {
                    this.players.sortById();
                    Iterator<Player> playerIterator = this.players.getAllPlayers();
                    while (playerIterator.hasNext()) {
                        Player player = playerIterator.next();
                        System.out.println(player);
                    }
                }
                case 3 -> {
                    this.players.sortByName();
                    Iterator<Player> playerIterator = this.players.getAllPlayers();
                    while (playerIterator.hasNext()) {
                        Player player = playerIterator.next();
                        System.out.println(player);
                    }
                }
                case 4 -> {
                    this.players.sortByLevel();
                    Iterator<Player> playerIterator = this.players.getAllPlayers();
                    while (playerIterator.hasNext()) {
                        Player player = playerIterator.next();
                        System.out.println(player);
                    }
                }
                case 5 -> {
                    this.players.sortByConqueredPortals();
                    Iterator<Player> playerIterator = this.players.getAllPlayers();
                    while (playerIterator.hasNext()) {
                        Player player = playerIterator.next();
                        System.out.println(player);
                    }
                }
                case 6 -> {
                    String playerId = readString("Enter the id of the player -> ");
                    Player player = this.players.getPlayerById(playerId);
                    if (Objects.nonNull(player)) System.out.println(player);
                    else System.out.println("Player not found!");
                }
                case 7 -> {
                    String playerName = readString("Enter the name of the player -> ");
                    Player player = this.players.getPlayerByName(playerName);
                    if (Objects.nonNull(player)) System.out.println(player);
                    else System.out.println("Player not found!");
                }
                case 8 -> {
                    String playerName = readString("Enter the name of the player -> ");

                    System.out.println("Available Teams:");
                    this.teams.getAllTeams().forEachRemaining(System.out::println);

                    String teamId = readString("Enter the id of the team -> ");

                    Team team = this.teams.getTeamById(teamId);

                    if (Objects.isNull(team)) {
                        System.out.println("Team not found!");
                        break;
                    }

                    Player newPlayer = new Player(playerName);
                    newPlayer.setTeam(team);

                    Iterator<Local> localIterator = this.map.iteratorBFS(null);
                    while (localIterator.hasNext()) {
                        Local local = localIterator.next();
                        if (local instanceof Connector) {
                            newPlayer.setCurrentLocal(local);
                            break;
                        }
                    }

                    this.players.addPlayer(newPlayer);
                }
                case 9 -> {
                    String playerId = readString("Enter the id of the player -> ");
                    this.players.removePlayerById(playerId);
                }
            }
        }
    }

    public String toString() {
        return "PlayerManagement{" +
                "map=" + map +
                ", teams=" + teams +
                ", players=" + players +
                '}';
    }
}
