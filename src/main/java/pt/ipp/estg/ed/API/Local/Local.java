package pt.ipp.estg.ed.API.Local;

import java.util.UUID;

/**
 * The `Local` abstract class represents a local in the game.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 */
public abstract class Local {
    private String id;
    private Coordinates coordinates;

    public Local() {
    }

    public Local(Coordinates coordinates) {
        this.id = UUID.randomUUID().toString();
        this.coordinates = coordinates;
    }

    public Local(String id, Coordinates coordinates) {
        this.id = id;
        this.coordinates = coordinates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String toString() {
        return "Local{" +
                "id='" + id + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
