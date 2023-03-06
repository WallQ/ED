package pt.ipp.estg.ed.API.Game;

import pt.ipp.estg.data.structures.Graph.Network;
import pt.ipp.estg.data.structures.Graph.NetworkADT;
import pt.ipp.estg.ed.API.Config.Config;
import pt.ipp.estg.ed.API.Local.Connector.Connector;
import pt.ipp.estg.ed.API.Local.Local;
import pt.ipp.estg.ed.API.Local.Portal.Portal;
import pt.ipp.estg.ed.API.Player.IPlayerManagement;
import pt.ipp.estg.ed.API.Player.Player;
import pt.ipp.estg.ed.API.Player.PlayerManagement;
import pt.ipp.estg.ed.API.Team.ITeamManagement;
import pt.ipp.estg.ed.API.Team.Team;
import pt.ipp.estg.ed.API.Team.TeamManagement;

import java.util.Iterator;
import java.util.Objects;

import static pt.ipp.estg.ed.API.Utils.Input.readInteger;
import static pt.ipp.estg.ed.API.Utils.Input.readString;
import static pt.ipp.estg.ed.API.Utils.Menu.*;

/**
 * The `Game` class represents a game.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 * @implements IGame
 */
public class Game implements IGame {
    protected NetworkADT<Local> map;
    protected ITeamManagement teams;
    protected IPlayerManagement players;
    protected Player currentPlayer;

    public Game() {
        this.map = new Network<>();
        this.teams = new TeamManagement();
        this.players = new PlayerManagement();
        this.currentPlayer = null;
    }

    public NetworkADT<Local> getMap() {
        return map;
    }

    public ITeamManagement getTeams() {
        return teams;
    }

    public IPlayerManagement getPlayers() {
        return players;
    }

    /**
     * Game initialization.
     */
    public void play() {
        verifyNecessaryData();
        handleCurrentPlayer();
        addConnectorTraps();

        if (Objects.isNull(this.currentPlayer)) return;

        System.out.println("Welcome " + this.currentPlayer.getName() + "!");
        Integer option = Integer.MAX_VALUE;
        while (option != 0) {
            Local currentLocal = this.currentPlayer.getCurrentLocal();
            if (currentLocal instanceof Portal) {
                System.out.println(portalMenu());
                option = readInteger("Enter you option -> ", 0, 3);

                switch (option) {
                    case 0 -> System.out.println("Exiting game...");
                    case 1 -> {
                        System.out.println("Current energy: " + this.currentPlayer.getEnergy());
                        Integer energy = readInteger("Energy to Conquer -> ", 0, Integer.MAX_VALUE);
                        ((Portal) currentLocal).conquerPortal(this.currentPlayer, energy);
                    }
                    case 2 -> {
                        System.out.println("Current energy: " + this.currentPlayer.getEnergy());
                        Integer energy = readInteger("Energy to Charge -> ", 0, Integer.MAX_VALUE);
                        ((Portal) currentLocal).chargePortal(this.currentPlayer, energy);
                    }
                    case 3 -> handleMovePlayerTo();
                    default ->
                            System.out.println("An unknown error has occurred. Please restart Conquer the World Application, and try again.");
                }
            } else if (currentLocal instanceof Connector) {
                System.out.println(connectorMenu());
                option = readInteger("Enter you option -> ", 0, 2);

                switch (option) {
                    case 0 -> System.out.println("Thank you for playing.");
                    case 1 -> ((Connector) currentLocal).rechargePlayerEnergy(this.currentPlayer);
                    case 2 -> handleMovePlayerTo();
                    default ->
                            System.out.println("An unknown error has occurred. Please restart Conquer the World Application, and try again.");
                }
            } else {
                System.out.println("An unknown error has occurred. Please restart Conquer the World Application, and try again.");
                return;
            }
        }
    }

    /**
     * Verify if the necessary data is present to start the game.
     */
    private void verifyNecessaryData() {
        if (this.map.isEmpty()) throw new IllegalStateException("The map is empty!");
        if (this.teams.isEmpty()) throw new IllegalStateException("There are no teams!");
        if (this.players.isEmpty()) throw new IllegalStateException("There are no players!");
    }

    /**
     * Add traps to the connectors.
     */
    private void addConnectorTraps() {
        Iterator<Local> iterator = this.map.iteratorBFS(null);
        Integer connectorsCount = 0;

        while (iterator.hasNext()) {
            Local local = iterator.next();
            if (local instanceof Connector) connectorsCount++;
        }

        int traps = (int) Math.ceil(connectorsCount * Config.CONNECTOR_AMOUNT_TRAPS);

        iterator = this.map.iteratorBFS(null);
        while (iterator.hasNext() && traps > 0) {
            Local local = iterator.next();
            if (local instanceof Connector && !((Connector) local).getTrap()) {
                ((Connector) local).setTrap(true);
                traps--;
            }
        }
    }

    /**
     * Handle the current player.
     */
    private void handleCurrentPlayer() {
        System.out.println(playerMenu());
        Integer option = readInteger("Enter you option -> ", 0, 3);

        switch (option) {
            case 0 -> System.out.println("Thank you for playing.");
            case 1 -> {
                String name = readString("Name -> ");

                System.out.println("Available teams:");
                this.teams.getAllTeams().forEachRemaining(team -> System.out.println(team.getName()));
                String teamName = readString("Team -> ");

                Team team = this.teams.getTeamByName(teamName);

                if (Objects.isNull(team)) throw new IllegalStateException("The team is null!");

                Player player = new Player(name);
                player.setTeam(team);

                Iterator<Local> iterator = this.map.iteratorBFS(null);
                while (iterator.hasNext()) {
                    Local local = iterator.next();
                    if (local instanceof Connector) {
                        player.setCurrentLocal(local);
                        break;
                    }
                }

                this.players.addPlayer(player);

                this.currentPlayer = player;
            }
            case 2 -> {
                System.out.println("Available players:");
                this.players.getAllPlayers().forEachRemaining(player -> System.out.println(player.getId()));

                String id = readString("Id -> ");

                this.currentPlayer = this.players.getPlayerById(id);

                if (Objects.isNull(this.currentPlayer)) throw new IllegalStateException("The current player is null!");
            }
            case 3 -> {
                System.out.println("Available players:");
                this.players.getAllPlayers().forEachRemaining(player -> System.out.println(player.getName()));

                String name = readString("Name -> ");

                this.currentPlayer = this.players.getPlayerByName(name);

                if (Objects.isNull(this.currentPlayer)) throw new IllegalStateException("The current player is null!");
            }
            default ->
                    System.out.println("An unknown error has occurred. Please restart Conquer the World Application, and try again.");
        }
    }

    /**
     * Handle the move player to.
     */
    private void handleMovePlayerTo() {
        Local localToMove = findMoveLocal();

        if (Objects.isNull(localToMove)) {
            System.out.println("Invalid local to move!");
            return;
        }

        if (Objects.equals(localToMove, this.currentPlayer.getCurrentLocal())) {
            System.out.println("The player " + this.currentPlayer.getName() + " is already in " + localToMove.getId() + "!");
            return;
        }

        Iterator<Player> iterator = this.players.getAllPlayers();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (!Objects.equals(player.getTeam(), this.currentPlayer.getTeam()) && Objects.equals(player.getCurrentLocal(), localToMove) && player.getEnergy() > this.currentPlayer.getEnergy()) {
                System.out.println("The player " + this.currentPlayer.getName() + " tried to move to " + localToMove.getId() + " but it's already occupied by opponent " + player.getName() + "!");
                return;
            }
        }

        double energyCost = this.map.shortestPathWeight(this.currentPlayer.getCurrentLocal(), localToMove);
        if (energyCost > this.currentPlayer.getEnergy()) {
            System.out.println("The player " + this.currentPlayer.getName() + " tried to move to " + localToMove.getId() + " but doesn't have enough energy!");
            System.out.println("It costs " + energyCost + " energy to move to " + localToMove.getId() + ", but the player only has " + this.currentPlayer.getEnergy() + " energy!");
            return;
        }

        this.currentPlayer.decreaseEnergy((int) energyCost);
        this.currentPlayer.changeLocal(localToMove);
        this.currentPlayer.increaseExperience(Config.PLAYER_TRAVEL_EXPERIENCE);

        System.out.println("The player " + this.currentPlayer.getName() + " moved to " + localToMove.getId() + "!");
        System.out.println("It costed " + energyCost + " energy to move to " + localToMove.getId() + ", now the player has " + this.currentPlayer.getEnergy() + " energy!");

        if (localToMove instanceof Connector) {
            ((Connector) localToMove).ambushPlayer(this.currentPlayer);
        }
    }

    /**
     * Find the local to move.
     *
     * @return The local to move
     */
    private Local findMoveLocal() {
        System.out.println("Possible locals to move: ");
        this.map.iteratorBFS(null).forEachRemaining(local -> System.out.println(local.getId()));

        String localId = readString("Enter local id to move -> ");

        Iterator<Local> iterator = this.map.iteratorBFS(null);
        while (iterator.hasNext()) {
            Local local = iterator.next();
            if (Objects.equals(local.getId(), localId)) return local;
        }

        return null;
    }

    public String toString() {
        return "Game{" +
                "map=" + map +
                ", teams=" + teams +
                ", players=" + players +
                ", currentPlayer=" + currentPlayer +
                '}';
    }
}
