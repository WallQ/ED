package pt.ipp.estg.ed.API.JSON;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pt.ipp.estg.data.structures.Graph.NetworkADT;
import pt.ipp.estg.data.structures.List.UnorderedArrayList;
import pt.ipp.estg.data.structures.List.UnorderedListADT;
import pt.ipp.estg.data.structures.Map.HashMap;
import pt.ipp.estg.ed.API.Game.Game;
import pt.ipp.estg.ed.API.Local.Connector.Connector;
import pt.ipp.estg.ed.API.Local.Coordinates;
import pt.ipp.estg.ed.API.Local.Local;
import pt.ipp.estg.ed.API.Local.Portal.Portal;
import pt.ipp.estg.ed.API.Player.IPlayerManagement;
import pt.ipp.estg.ed.API.Player.Player;
import pt.ipp.estg.ed.API.Route.Route;
import pt.ipp.estg.ed.API.Team.ITeamManagement;
import pt.ipp.estg.ed.API.Team.Team;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

import static pt.ipp.estg.ed.API.Utils.Calcs.calculateDistance;
import static pt.ipp.estg.ed.API.Utils.File.getFileFromResources;

/**
 * The `JSONImporter` class represents the JSON importer.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 * @implements IJSONImporter
 */
public class JSONImporter implements IJSONImporter {
    private final NetworkADT<Local> map;
    private final ITeamManagement teams;
    private final IPlayerManagement players;
    private final UnorderedListADT<Local> locals;
    private final UnorderedListADT<Route> routes;

    public JSONImporter(Game game) {
        this.map = game.getMap();
        this.teams = game.getTeams();
        this.players = game.getPlayers();
        this.locals = new UnorderedArrayList<>();
        this.routes = new UnorderedArrayList<>();
    }

    /**
     * Imports the game data from JSON file.
     *
     * @throws IOException if an I/O error occurs
     */
    public void importJSON() throws IOException {
        importTeams();
        importPlayers();
        importLocals();
        importRoutes();
        loadMap();
        loadPlayersEntryPoint();
    }

    /**
     * Imports the teams from JSON file.
     *
     * @throws IOException if an I/O error occurs
     */
    private void importTeams() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File file = getFileFromResources("teamsImport.json");

        JsonNode root = mapper.readTree(file);

        extractTeams(root);
    }

    private void extractTeams(JsonNode node) {
        JsonNode teamsJN = node.get("teams");

        if (Objects.isNull(teamsJN)) throw new NullPointerException("Teams not found!");

        for (JsonNode team : teamsJN) {
            if (Objects.isNull(team)) continue;

            String id = team.get("id").asText();
            String name = team.get("name").asText();

            this.teams.addTeam(new Team(id, name));
        }
    }

    /**
     * Imports the players from JSON file.
     *
     * @throws IOException if an I/O error occurs
     */
    private void importPlayers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File file = getFileFromResources("playersImport.json");

        JsonNode root = mapper.readTree(file);

        extractPlayers(root);
    }

    private void extractPlayers(JsonNode node) {
        JsonNode playersJN = node.get("players");

        if (Objects.isNull(playersJN)) throw new NullPointerException("Players not found!");

        for (JsonNode player : playersJN) {
            if (Objects.isNull(player)) continue;

            String id = player.get("id").asText();
            String name = player.get("name").asText();
            String teamId = player.get("team").asText();
            Integer level = player.get("level").asInt();
            Integer currentExperience = player.get("currentExperience").asInt();
            Integer totalExperience = player.get("totalExperience").asInt();
            Integer energy = player.get("energy").asInt();
            Integer maxEnergy = player.get("maxEnergy").asInt();
            Integer conqueredPortals = player.get("conqueredPortals").asInt();
            String currentLocalId = player.get("currentLocal").asText();

            Team tempTeam = this.teams.getTeamById(teamId);
            if (Objects.isNull(tempTeam)) {
                continue;
            }

            Local tempLocal = null;
            for (Local local : this.locals) {
                if (local.getId().equals(currentLocalId)) tempLocal = local;
            }

            this.players.addPlayer(new Player(id, name, tempTeam, level, currentExperience, totalExperience, energy, maxEnergy, conqueredPortals, tempLocal));
        }
    }

    /**
     * Imports the locals from JSON file.
     *
     * @throws IOException if an I/O error occurs
     */
    private void importLocals() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File file = getFileFromResources("localsImport.json");

        JsonNode root = mapper.readTree(file);

        extractLocals(root);
    }

    private void extractLocals(JsonNode node) {
        JsonNode localsJN = node.get("locals");

        if (Objects.isNull(localsJN)) throw new NullPointerException("Locals not found!");

        for (JsonNode local : localsJN) {
            if (Objects.isNull(local)) continue;

            String type = local.get("type").asText();

            if (type.equalsIgnoreCase("Portal")) {
                extractPortal(local);
            } else if (type.equalsIgnoreCase("Connector")) {
                extractConnector(local);
            }
        }
    }

    private void extractPortal(JsonNode portal) {
        String id = portal.get("id").asText();
        JsonNode coordinates = portal.get("coordinates");
        Double latitude = coordinates.get("latitude").asDouble();
        Double longitude = coordinates.get("longitude").asDouble();
        String name = portal.get("name").asText();
        Integer energy = portal.get("energy").asInt();
        Integer maxEnergy = portal.get("maxEnergy").asInt();
        String conquerorId = portal.get("conqueror").asText();
        String teamId = portal.get("team").asText();

        Player tempConqueror = this.players.getPlayerById(conquerorId);
        Team tempTeam = this.teams.getTeamById(teamId);

        this.locals.addToRear(new Portal(id, new Coordinates(latitude, longitude), name, energy, maxEnergy, tempConqueror, tempTeam));
    }

    private void extractConnector(JsonNode connector) {
        String id = connector.get("id").asText();
        JsonNode coordinates = connector.get("coordinates");
        Double latitude = coordinates.get("latitude").asDouble();
        Double longitude = coordinates.get("longitude").asDouble();
        Integer energy = connector.get("energy").asInt();
        JsonNode cooldown = connector.get("cooldown");
        Boolean trap = connector.get("trap").asBoolean();

        this.locals.addToRear(new Connector(id, new Coordinates(latitude, longitude), energy, new HashMap<>(), trap));
    }

    /**
     * Imports the routes from JSON file.
     *
     * @throws IOException if an I/O error occurs
     */
    private void importRoutes() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File file = getFileFromResources("routesImport.json");

        JsonNode root = mapper.readTree(file);

        extractRoutes(root);
    }

    private void extractRoutes(JsonNode node) {
        JsonNode routesJN = node.get("routes");

        if (Objects.isNull(routesJN)) throw new NullPointerException("Routes not found!");

        for (JsonNode route : routesJN) {
            if (Objects.isNull(route)) continue;

            String fromId = route.get("from").asText();
            String toId = route.get("to").asText();

            for (Local from : this.locals) {
                if (from.getId().equals(fromId)) {
                    for (Local to : this.locals) {
                        if (to.getId().equals(toId)) {
                            this.routes.addToRear(new Route(from, to, calculateDistance(from.getCoordinates(), to.getCoordinates())));
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds the locals and routes to the map.
     */
    private void loadMap() {
        for (Local local : this.locals) {
            this.map.addVertex(local);
        }

        for (Route route : this.routes) {
            this.map.addEdge(route.getFrom(), route.getTo(), route.getDistance());
        }
    }

    /**
     * Adds the players current local from the map.
     */
    private void loadPlayersEntryPoint() {
        Iterator<Local> iteratorBFS = this.map.iteratorBFS(null);
        Iterator<Player> playersIterator = this.players.getAllPlayers();

        while (playersIterator.hasNext()) {
            Player player = playersIterator.next();
            if (Objects.isNull(player.getCurrentLocal())) {
                while (iteratorBFS.hasNext()) {
                    Local local = iteratorBFS.next();
                    if (local instanceof Connector) {
                        player.setCurrentLocal(local);
                        break;
                    }
                }
            }
        }
    }

    public String toString() {
        return "JSONImporter{" +
                "map=" + map +
                ", teams=" + teams +
                ", players=" + players +
                ", locals=" + locals +
                ", routes=" + routes +
                '}';
    }
}
