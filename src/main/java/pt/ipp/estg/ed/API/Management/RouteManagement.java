package pt.ipp.estg.ed.API.Management;

import pt.ipp.estg.data.structures.Graph.NetworkADT;
import pt.ipp.estg.ed.API.Game.Game;
import pt.ipp.estg.ed.API.Local.Local;
import pt.ipp.estg.ed.API.Local.Portal.Portal;

import java.util.Iterator;
import java.util.Objects;

import static pt.ipp.estg.ed.API.Utils.Calcs.calculateDistance;
import static pt.ipp.estg.ed.API.Utils.Input.readInteger;
import static pt.ipp.estg.ed.API.Utils.Input.readString;
import static pt.ipp.estg.ed.API.Utils.Menu.routesManagementMenu;

/**
 * The `RouteManagement` class represents a set of methods for managing the routes in the game.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 * @implements IRouteManagement
 */
public class RouteManagement implements IRouteManagement {
    private final NetworkADT<Local> map;

    public RouteManagement(Game game) {
        this.map = game.getMap();
    }

    /*
     * Route Management initialization.
     */
    public void manage() {
        Integer option = Integer.MAX_VALUE;
        while (option != 0) {
            System.out.println(routesManagementMenu());
            option = readInteger("Enter you option -> ", 0, 2);

            switch (option) {
                case 0 -> System.out.println("Exiting Routes Management...");
                case 1 -> {
                    String fromId = readString("Enter the from id of the local -> ");
                    String toId = readString("Enter the to id of the local -> ");
                    this.addPath(fromId, toId);
                }
                case 2 -> {
                    String fromId = readString("Enter the from id of the local -> ");
                    String toId = readString("Enter the to id of the local -> ");
                    this.removePath(fromId, toId);
                }
                default ->
                        System.out.println("An unknown error has occurred. Please restart Conquer the World Application, and try again.");
            }
        }
    }

    /*
     * Add a path between two locals in the game.
     */
    public void addPath(String from, String to) {
        if (Objects.isNull(from)) throw new IllegalArgumentException("From cannot be null!");
        if (Objects.isNull(to)) throw new IllegalArgumentException("To cannot be null!");

        Local fromLocal = this.findLocalById(from);
        Local toLocal = this.findLocalById(to);

        if (Objects.isNull(fromLocal)) throw new IllegalArgumentException("From local does not exist!");
        if (Objects.isNull(toLocal)) throw new IllegalArgumentException("To local does not exist!");

        if (fromLocal instanceof Portal && toLocal instanceof Portal)
            throw new IllegalArgumentException("Cannot create a path between two portals!");

        this.map.addEdge(fromLocal, toLocal, calculateDistance(fromLocal.getCoordinates(), toLocal.getCoordinates()));
    }

    /*
     * Remove a path between two locals in the game.
     */
    public void removePath(String from, String to) {
        if (Objects.isNull(from)) throw new IllegalArgumentException("From cannot be null!");
        if (Objects.isNull(to)) throw new IllegalArgumentException("To cannot be null!");

        Local fromLocal = this.findLocalById(from);
        Local toLocal = this.findLocalById(to);

        if (Objects.isNull(fromLocal)) throw new IllegalArgumentException("From local does not exist!");
        if (Objects.isNull(toLocal)) throw new IllegalArgumentException("To local does not exist!");

        this.map.removeEdge(fromLocal, toLocal);
    }

    /*
     * Find a local by its id.
     */
    private Local findLocalById(String id) {
        Iterator<Local> locals = this.map.getVertices();
        while (locals.hasNext()) {
            Local local = locals.next();
            if (local.getId().equals(id)) {
                return local;
            }
        }

        return null;
    }

    public String toString() {
        return "RouteManagement{" +
                "map=" + map +
                '}';
    }
}
