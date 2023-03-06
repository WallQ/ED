package pt.ipp.estg.ed.API.Management;

import pt.ipp.estg.data.structures.Graph.NetworkADT;
import pt.ipp.estg.data.structures.List.UnorderedArrayList;
import pt.ipp.estg.data.structures.List.UnorderedListADT;
import pt.ipp.estg.ed.API.Game.Game;
import pt.ipp.estg.ed.API.Local.Local;

import java.util.Iterator;
import java.util.Objects;

import static pt.ipp.estg.ed.API.Utils.Input.readInteger;
import static pt.ipp.estg.ed.API.Utils.Input.readString;
import static pt.ipp.estg.ed.API.Utils.Menu.gameManagementMenu;

/**
 * The `GameManagement` class represents a set of methods for managing the game.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 * @implements IGameManagement
 */
public class GameManagement implements IGameManagement {
    private final NetworkADT<Local> map;

    public GameManagement(Game game) {
        this.map = game.getMap();
    }

    /*
     * Game Management initialization.
     */
    public void manage() {
        Integer option = Integer.MAX_VALUE;
        while (option != 0) {
            System.out.println(gameManagementMenu());
            option = readInteger("Enter you option -> ", 0, 5);

            switch (option) {
                case 0 -> System.out.println("Exiting Local Management...");
                case 1 -> {
                    Iterator<Local> iterator = this.map.iteratorBFS(null);
                    while (iterator.hasNext()) {
                        Local local = iterator.next();
                        System.out.println(local.getId());
                    }
                }
                case 2 -> {
                    Iterator<Local> iterator = this.map.iteratorDFS(null);
                    while (iterator.hasNext()) {
                        Local local = iterator.next();
                        System.out.println(local.getId());
                    }
                }
                case 3 -> {
                    listLocals();

                    String fromId = readString("Enter from local id -> ");
                    String toId = readString("Enter to local id -> ");

                    Local from = this.getLocal(fromId);
                    Local to = this.getLocal(toId);

                    Iterator<Local> iterator = this.map.iteratorShortestPath(from, to);
                    while (iterator.hasNext()) {
                        Local local = iterator.next();
                        System.out.println(local.getId());
                    }
                }
                case 4 -> {
                    listLocals();

                    String fromId = readString("Enter from local id -> ");
                    String toId = readString("Enter to local id -> ");

                    Local from = this.getLocal(fromId);
                    Local to = this.getLocal(toId);

                    double pathWeight = this.map.shortestPathWeight(from, to);
                    System.out.println("The shortest path weight is: " + pathWeight);
                }
                case 5 -> {
                    listLocals();

                    String fromId = readString("Enter from local id -> ");
                    String toId = readString("Enter to local id -> ");

                    Local from = this.getLocal(fromId);
                    Local to = this.getLocal(toId);

                    UnorderedListADT<String> localsId = new UnorderedArrayList<>();
                    String continueAdding;
                    do {
                        String localId = readString("Enter local id to pass through -> ");
                        localsId.addToRear(localId);
                        continueAdding = readString("Do you want to add another local to the path? (Y/N) -> ");
                    } while (continueAdding.equalsIgnoreCase("Y"));

                    UnorderedListADT<Local> locals = new UnorderedArrayList<>();
                    for (String id : localsId) {
                        locals.addToRear(this.getLocal(id));
                    }

                    boolean isPath = true;
                    Iterator<Local> iterator = this.map.iteratorShortestPath(from, to);

                    while (iterator.hasNext()) {
                        Local local = iterator.next();
                        if (!locals.contains(local)) {
                            isPath = false;
                            break;
                        }
                    }

                    if (isPath) {
                        iterator = this.map.iteratorShortestPath(from, to);
                        while (iterator.hasNext()) {
                            Local local = iterator.next();
                            System.out.println(local.getId());
                        }
                    } else {
                        System.out.println("The path is not valid!");
                    }
                }
                default ->
                        System.out.println("An unknown error has occurred. Please restart Conquer the World Application, and try again.");
            }
        }
    }

    /*
     * List all locals in the game.
     */
    private void listLocals() {
        System.out.println("Possible locals: ");
        Iterator<Local> iterator = this.map.iteratorBFS(null);
        while (iterator.hasNext()) {
            Local local = iterator.next();
            System.out.println(local.getId());
        }
    }

    /*
     * Find a local by its id.
     */
    private Local getLocal(String localId) {
        Iterator<Local> iterator = this.map.iteratorBFS(null);
        while (iterator.hasNext()) {
            Local local = iterator.next();
            if (Objects.equals(local.getId(), localId)) return local;
        }

        return null;
    }

    public String toString() {
        return "GameManagement{" +
                "map=" + map +
                '}';
    }
}
