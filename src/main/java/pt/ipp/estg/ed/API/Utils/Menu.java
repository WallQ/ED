package pt.ipp.estg.ed.API.Utils;

/**
 * The `Menu` class represents the menus used in the application.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 */
public class Menu {
    /**
     * The player menu.
     *
     * @return The player menu
     */
    public static String playerMenu() {
        ConsoleBuilder builder = new ConsoleBuilder();
        builder.setTitle("Player Menu")
                .addOption("Create Player")
                .addOption("Choose Player By Id")
                .addOption("Choose Player By Name");
        return builder.build();
    }

    /**
     * The portal menu.
     *
     * @return The portal menu
     */
    public static String portalMenu() {
        ConsoleBuilder builder = new ConsoleBuilder();
        builder.setTitle("Player Menu")
                .addOption("Conquer")
                .addOption("Charge Energy")
                .addOption("Move");
        return builder.build();
    }

    /**
     * The connector menu.
     *
     * @return The connector menu
     */
    public static String connectorMenu() {
        ConsoleBuilder builder = new ConsoleBuilder();
        builder.setTitle("Connector Menu")
                .addOption("Recharge Energy")
                .addOption("Move");
        return builder.build();
    }

    /**
     * The game menu.
     *
     * @return The game menu
     */
    public static String gameManagementMenu() {
        ConsoleBuilder builder = new ConsoleBuilder();
        builder.setTitle("Local Management Menu")
                .addOption("View Map By Breadth-First Search")
                .addOption("View Map By Depth-First Search")
                .addOption("View Map By Shortest Path")
                .addOption("View Map By Shortest Path Weight")
                .addOption("View Map By Shortest Path Between Locals");
        return builder.build();
    }

    /**
     * The local management menu.
     *
     * @return The local management menu
     */
    public static String localManagementMenu() {
        ConsoleBuilder builder = new ConsoleBuilder();
        builder.setTitle("Local Management Menu")
                .addOption("Portal Management")
                .addOption("Connector Management");
        return builder.build();
    }

    /**
     * The portal management menu.
     *
     * @return The portal management menu
     */
    public static String portalManagementMenu() {
        ConsoleBuilder builder = new ConsoleBuilder();
        builder.setTitle("Portal Management Menu")
                .addOption("Get All Portals")
                .addOption("Get Portals By Id")
                .addOption("Get Portals By Name")
                .addOption("Get Portals By Energy")
                .addOption("Get Portal")
                .addOption("Add Portal")
                .addOption("Remove Portal")
                .addOption("View Map");
        return builder.build();
    }

    /**
     * The connector management menu.
     *
     * @return The connector management menu
     */
    public static String connectorManagementMenu() {
        ConsoleBuilder builder = new ConsoleBuilder();
        builder.setTitle("Connector Management Menu")
                .addOption("Get All Connectors")
                .addOption("Get Connector By Id")
                .addOption("Get Connector By Energy")
                .addOption("Get Connector")
                .addOption("Add Connector")
                .addOption("Remove Connector")
                .addOption("View Map");
        return builder.build();
    }

    /**
     * The routes management menu.
     *
     * @return The routes management menu
     */
    public static String routesManagementMenu() {
        ConsoleBuilder builder = new ConsoleBuilder();
        builder.setTitle("Routes Management Menu")
                .addOption("Add Route")
                .addOption("Remove Route");
        return builder.build();
    }

    /**
     * The player management menu.
     *
     * @return The player management menu
     */
    public static String playerManagementMenu() {
        ConsoleBuilder builder = new ConsoleBuilder();
        builder.setTitle("Routes Management Menu")
                .addOption("Get All Players")
                .addOption("Get Players By Id")
                .addOption("Get Players By Name")
                .addOption("Get Players By Level")
                .addOption("Get Players By Conquered Portals")
                .addOption("Get Player By Id")
                .addOption("Get Player By Name")
                .addOption("Add Player")
                .addOption("Remove Player");
        return builder.build();
    }

    /**
     * The application menu.
     *
     * @return The application menu
     */
    public static String applicationMenu() {
        ConsoleBuilder builder = new ConsoleBuilder();
        builder.setTitle("Conquer the World")
                .addOption("Play")
                .addOption("Game Management")
                .addOption("Local Management")
                .addOption("Route Management")
                .addOption("Player Management")
                .addOption("Team Management");
        return builder.build();
    }
}
