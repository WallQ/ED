package pt.ipp.estg.ed.Application;

import pt.ipp.estg.ed.API.Game.Game;
import pt.ipp.estg.ed.API.JSON.JSONExporter;
import pt.ipp.estg.ed.API.JSON.JSONImporter;
import pt.ipp.estg.ed.API.Management.*;

import java.io.IOException;

import static pt.ipp.estg.ed.API.Utils.Input.readInteger;
import static pt.ipp.estg.ed.API.Utils.Menu.applicationMenu;

public class Main {
    public static void main(String[] args) throws IOException {
        Game game = new Game();

        JSONImporter jsonImporter = new JSONImporter(game);
        jsonImporter.importJSON();

        Integer option = Integer.MAX_VALUE;
        while (option != 0) {
            System.out.println(applicationMenu());
            option = readInteger("Enter you option -> ", 0, 5);

            switch (option) {
                case 0 -> System.out.println("Thank you for using our application.");
                case 1 -> game.play();
                case 2 -> {
                    IGameManagement gameManagement = new GameManagement(game);
                    gameManagement.manage();
                }
                case 3 -> {
                    ILocalManagement localManagement = new LocalManagement(game);
                    localManagement.manage();
                }
                case 4 -> {
                    IRouteManagement routeManagement = new RouteManagement(game);
                    routeManagement.manage();
                }
                case 5 -> {
                    IPlayerManagement playerManagement = new PlayerManagement(game);
                    playerManagement.manage();
                }
                default ->
                        System.out.println("An unknown error has occurred. Please restart Conquer the World Application, and try again.");
            }
        }

        JSONExporter jsonExporter = new JSONExporter(game);
        jsonExporter.exportJSON();
    }
}