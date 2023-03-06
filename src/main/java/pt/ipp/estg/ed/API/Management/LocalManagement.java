package pt.ipp.estg.ed.API.Management;

import pt.ipp.estg.algorithms.Sort.MergeSort;
import pt.ipp.estg.data.structures.Graph.NetworkADT;
import pt.ipp.estg.data.structures.List.UnorderedArrayList;
import pt.ipp.estg.data.structures.List.UnorderedListADT;
import pt.ipp.estg.ed.API.Game.Game;
import pt.ipp.estg.ed.API.Local.Connector.Connector;
import pt.ipp.estg.ed.API.Local.Coordinates;
import pt.ipp.estg.ed.API.Local.Local;
import pt.ipp.estg.ed.API.Local.Portal.Portal;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

import static pt.ipp.estg.ed.API.Utils.Input.*;
import static pt.ipp.estg.ed.API.Utils.Menu.*;

/**
 * The `LocalManagement` class represents a set of methods for managing the locals in the game.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 * @implements ILocalManagement
 */
public class LocalManagement implements ILocalManagement {
    private final NetworkADT<Local> map;

    public LocalManagement(Game game) {
        this.map = game.getMap();
    }

    /*
     * Local Management initialization.
     */
    public void manage() {
        Integer option = Integer.MAX_VALUE;
        while (option != 0) {
            System.out.println(localManagementMenu());
            option = readInteger("Enter you option -> ", 0, 2);

            switch (option) {
                case 0 -> System.out.println("Exiting Local Management...");
                case 1 -> {
                    while (option != 0) {
                        System.out.println(portalManagementMenu());
                        option = readInteger("Enter you option -> ", 0, 8);

                        switch (option) {
                            case 0 -> System.out.println("Exiting Portal Management...");
                            case 1 -> {
                                Iterator<Portal> portalIterator = this.getAllPortals();
                                while (portalIterator.hasNext()) {
                                    Portal portal = portalIterator.next();
                                    System.out.println(portal);
                                }
                            }
                            case 2 -> {
                                Portal[] portals = this.getPortalsBy(Comparator.comparing(Portal::getId));
                                for (Portal portal : portals) {
                                    System.out.println(portal);
                                }
                            }
                            case 3 -> {
                                Portal[] portals = this.getPortalsBy(Comparator.comparing(Portal::getName));
                                for (Portal portal : portals) {
                                    System.out.println(portal);
                                }
                            }
                            case 4 -> {
                                Portal[] portals = this.getPortalsBy(Comparator.comparing(Portal::getEnergy));
                                for (Portal portal : portals) {
                                    System.out.println(portal);
                                }
                            }
                            case 5 -> {
                                String portalId = readString("Enter the id of the portal -> ");
                                Portal portal = this.getPortal(portalId);
                                if (Objects.isNull(portal)) System.out.println("Portal not found.");
                                else System.out.println(portal);
                            }
                            case 6 -> {
                                String portalName = readString("Enter the name of the portal -> ");
                                Double latitude = readDouble("Enter the latitude coordinate of the portal -> ", 0, Double.POSITIVE_INFINITY);
                                Double longitude = readDouble("Enter the longitude coordinate of the portal -> ", 0, Double.POSITIVE_INFINITY);
                                this.addPortal(portalName, latitude, longitude);
                            }
                            case 7 -> {
                                String portalId = readString("Enter the id of the portal -> ");
                                this.removePortal(portalId);
                            }
                            case 8 -> {
                                if (this.map.isEmpty()) System.out.println("There are no portals in the map.");
                                else System.out.println(this.map);
                            }
                            default ->
                                    System.out.println("An unknown error has occurred. Please restart Conquer the World Application, and try again.");
                        }
                    }
                }
                case 2 -> {
                    while (option != 0) {
                        System.out.println(connectorManagementMenu());
                        option = readInteger("Enter you option -> ", 0, 7);

                        switch (option) {
                            case 0 -> System.out.println("Exiting Portal Management...");
                            case 1 -> {
                                Iterator<Connector> connectorIterator = this.getAllConnectors();
                                while (connectorIterator.hasNext()) {
                                    Connector portal = connectorIterator.next();
                                    System.out.println(portal);
                                }
                            }
                            case 2 -> {
                                Connector[] connectors = this.getConnectorsBy(Comparator.comparing(Connector::getId));
                                for (Connector connector : connectors) {
                                    System.out.println(connector);
                                }
                            }
                            case 3 -> {
                                Connector[] connectors = this.getConnectorsBy(Comparator.comparing(Connector::getEnergy));
                                for (Connector connector : connectors) {
                                    System.out.println(connector);
                                }
                            }
                            case 4 -> {
                                String connectorId = readString("Enter the id of the connector -> ");
                                Connector connector = this.getConnector(connectorId);
                                if (Objects.isNull(connector)) System.out.println("Connector not found.");
                                else System.out.println(connector);
                            }
                            case 5 -> {
                                Double latitude = readDouble("Enter the latitude coordinate of the connector -> ", 0, Double.POSITIVE_INFINITY);
                                Double longitude = readDouble("Enter the longitude coordinate of the connector -> ", 0, Double.POSITIVE_INFINITY);
                                this.addConnector(latitude, longitude);
                            }
                            case 6 -> {
                                String connectorId = readString("Enter the id of the connector -> ");
                                this.removeConnector(connectorId);
                            }
                            case 7 -> {
                                if (this.map.isEmpty()) System.out.println("There are no connectors in the map.");
                                else System.out.println(this.map);
                            }
                            default ->
                                    System.out.println("An unknown error has occurred. Please restart Conquer the World Application, and try again.");
                        }
                    }
                }
                default ->
                        System.out.println("An unknown error has occurred. Please restart Conquer the World Application, and try again.");
            }
        }
    }

    /*
     * Gets all portals in the map.
     */
    private UnorderedListADT<Portal> getPortalsList() {
        UnorderedListADT<Portal> portals = new UnorderedArrayList<>();
        Iterator<Local> localIterator = this.map.getVertices();

        while (localIterator.hasNext()) {
            Local local = localIterator.next();
            if (local instanceof Portal portal) portals.addToRear(portal);
        }

        return portals;
    }

    /*
     * Gets all portals.
     *
     * @return an iterator of portals.
     */
    private Iterator<Portal> getAllPortals() {
        return this.getPortalsList().iterator();
    }

    /*
     * Gets all portals sorted by a comparator.
     *
     * @return an array of portals sorted by the comparator.
     */
    private Portal[] getPortalsBy(Comparator<Portal> comparator) {
        UnorderedListADT<Portal> portals = this.getPortalsList();

        Portal[] portalsArray = new Portal[portals.size()];
        Iterator<Portal> portalsIterator = portals.iterator();
        int i = 0;
        while (portalsIterator.hasNext()) {
            portalsArray[i++] = portalsIterator.next();
        }

        MergeSort.sort(portalsArray, comparator);

        return portalsArray;
    }

    /*
     * Gets a portal by its id.
     */
    private Portal getPortal(String id) {
        if (Objects.isNull(id)) throw new IllegalArgumentException("Id cannot be null!");

        for (Portal portal : this.getPortalsList()) {
            if (portal.getId().equals(id)) return portal;
        }

        return null;
    }

    /*
     * Adds a portal to the map.
     */
    private void addPortal(String name, Double latitude, Double longitude) {
        if (Objects.isNull(name)) throw new IllegalArgumentException("Name cannot be null!");
        if (Objects.isNull(latitude)) throw new IllegalArgumentException("Latitude cannot be null!");
        if (Objects.isNull(longitude)) throw new IllegalArgumentException("Longitude cannot be null!");

        Coordinates coordinates = new Coordinates(latitude, longitude);

        for (Portal portal : this.getPortalsList()) {
            if (portal.getName().equals(name)) {
                throw new IllegalArgumentException("Portal name already exists");
            }

            if (portal.getCoordinates().equals(coordinates)) {
                throw new IllegalArgumentException("Portal coordinates already exists");
            }
        }

        this.map.addVertex(new Portal(name, coordinates));
    }

    /*
     * Removes a portal from the map.
     */
    private void removePortal(String id) {
        if (Objects.isNull(id)) throw new IllegalArgumentException("Id cannot be null!");

        for (Portal portal : this.getPortalsList()) {
            if (portal.getId().equals(id)) {
                this.map.removeVertex(portal);
                return;
            }
        }
    }

    /*
     * Gets all connectors in the map.
     */
    private UnorderedListADT<Connector> getConnectorsList() {
        UnorderedListADT<Connector> connectors = new UnorderedArrayList<>();
        Iterator<Local> localIterator = this.map.getVertices();

        while (localIterator.hasNext()) {
            Local local = localIterator.next();
            if (local instanceof Connector connector) connectors.addToRear(connector);
        }

        return connectors;
    }

    /*
     * Gets all connectors.
     *
     * @return an iterator of connectors.
     */
    private Iterator<Connector> getAllConnectors() {
        return this.getConnectorsList().iterator();
    }

    /*
     * Gets all connectors sorted by a comparator.
     *
     * @return an array of connectors sorted by the comparator.
     */
    private Connector[] getConnectorsBy(Comparator<Connector> comparator) {
        UnorderedListADT<Connector> connectors = this.getConnectorsList();

        Connector[] connectorsArray = new Connector[connectors.size()];
        Iterator<Connector> connectorsIterator = connectors.iterator();
        int i = 0;
        while (connectorsIterator.hasNext()) {
            connectorsArray[i++] = connectorsIterator.next();
        }

        MergeSort.sort(connectorsArray, comparator);

        return connectorsArray;
    }

    /*
     * Gets a connector by its id.
     *
     * @return the connector if it exists, null otherwise.
     */
    private Connector getConnector(String id) {
        if (Objects.isNull(id)) throw new IllegalArgumentException("Id cannot be null!");

        for (Connector connector : this.getConnectorsList()) {
            if (connector.getId().equals(id)) return connector;
        }

        return null;
    }

    /*
     * Adds a connector to the map.
     */
    private void addConnector(Double latitude, Double longitude) {
        if (Objects.isNull(latitude)) throw new IllegalArgumentException("Latitude cannot be null!");
        if (Objects.isNull(longitude)) throw new IllegalArgumentException("Longitude cannot be null!");

        Coordinates coordinates = new Coordinates(latitude, longitude);

        for (Connector connector : this.getConnectorsList()) {
            if (connector.getCoordinates().equals(coordinates)) {
                throw new IllegalArgumentException("Connector coordinates already exists");
            }
        }

        this.map.addVertex(new Connector(coordinates));
    }

    /*
     * Removes a connector from the map.
     */
    private void removeConnector(String id) {
        if (Objects.isNull(id)) throw new IllegalArgumentException("Id cannot be null!");

        for (Connector connector : this.getConnectorsList()) {
            if (connector.getId().equals(id)) {
                this.map.removeVertex(connector);
                return;
            }
        }
    }

    public String toString() {
        return "LocalManagement{" +
                "map=" + map +
                '}';
    }
}
