package pt.ipp.estg.ed.API.JSON;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import pt.ipp.estg.data.structures.Graph.NetworkADT;
import pt.ipp.estg.data.structures.List.UnorderedArrayList;
import pt.ipp.estg.data.structures.List.UnorderedListADT;
import pt.ipp.estg.ed.API.Game.Game;
import pt.ipp.estg.ed.API.Local.Local;
import pt.ipp.estg.ed.API.Player.IPlayerManagement;
import pt.ipp.estg.ed.API.Route.Route;
import pt.ipp.estg.ed.API.Team.ITeamManagement;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static pt.ipp.estg.ed.API.Utils.Calcs.calculateDistance;
import static pt.ipp.estg.ed.API.Utils.File.getPathFromResources;

/**
 * The `JSONExporter` class represents the JSON exporter.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 * @implements IJSONExporter
 */
public class JSONExporter implements IJSONExporter {
    private final NetworkADT<Local> map;
    private final ITeamManagement teams;
    private final IPlayerManagement players;
    private final UnorderedListADT<Local> locals;
    private final UnorderedListADT<Route> routes;

    public JSONExporter(Game game) {
        this.map = game.getMap();
        this.teams = game.getTeams();
        this.players = game.getPlayers();
        this.locals = new UnorderedArrayList<>();
        this.routes = new UnorderedArrayList<>();
    }

    /**
     * Exports the game data to JSON files.
     *
     * @throws IOException if an I/O error occurs
     */
    public void exportJSON() throws IOException {
        exportTeams();
        exportPlayers();
        exportLocals();
        exportRoutes();
    }

    /**
     * Exports the teams to a JSON file.
     *
     * @throws IOException if an I/O error occurs
     */
    private void exportTeams() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String buildDir = getPathFromResources("");
        File file = new File(buildDir, "teamsExport.json");

        JsonGenerator generator = mapper.createGenerator(file, JsonEncoding.UTF8);

        generator.writeStartObject();
        generator.writeObjectField("teams", this.teams.getAllTeams());
        generator.writeEndObject();

        generator.close();
    }

    /**
     * Exports the players to a JSON file.
     *
     * @throws IOException if an I/O error occurs
     */
    private void exportPlayers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String buildDir = getPathFromResources("");
        File file = new File(buildDir, "playersExport.json");

        JsonGenerator generator = mapper.createGenerator(file, JsonEncoding.UTF8);

        generator.writeStartObject();
        generator.writeObjectField("players", this.players.getAllPlayers());
        generator.writeEndObject();

        generator.close();
    }

    /**
     * Exports the locals to a JSON file.
     *
     * @throws IOException if an I/O error occurs
     */
    private void exportLocals() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String buildDir = getPathFromResources("");
        File file = new File(buildDir, "localsExport.json");

        JsonGenerator generator = mapper.createGenerator(file, JsonEncoding.UTF8);

        Iterator<Local> iterator = this.map.iteratorBFS(null);
        while (iterator.hasNext()) {
            Local local = iterator.next();
            this.locals.addToRear(local);
        }

        generator.writeStartObject();
        generator.writeObjectField("locals", this.locals.iterator());
        generator.writeEndObject();

        generator.close();
    }

    /**
     * Exports the routes to a JSON file.
     *
     * @throws IOException if an I/O error occurs
     */
    private void exportRoutes() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String buildDir = getPathFromResources("");
        File file = new File(buildDir, "routesExport.json");

        JsonGenerator generator = mapper.createGenerator(file, JsonEncoding.UTF8);

        for (Local local : this.locals) {
            Iterator<Local> iterator = this.map.getAdjacentVertices(local);
            while (iterator.hasNext()) {
                Local adjacent = iterator.next();
                this.routes.addToRear(new Route(local, adjacent, calculateDistance(local.getCoordinates(), adjacent.getCoordinates())));
            }
        }

        generator.writeStartObject();
        generator.writeObjectField("routes", this.routes.iterator());
        generator.writeEndObject();

        generator.close();
    }

    public String toString() {
        return "JSONExporter{" +
                "map=" + map +
                ", teams=" + teams +
                ", players=" + players +
                ", locals=" + locals +
                ", routes=" + routes +
                '}';
    }
}
