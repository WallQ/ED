package pt.ipp.estg.ed.API.Route;

import pt.ipp.estg.ed.API.Local.Local;

/**
 * The `Route` class represents a route between two locals in the game.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 * @implements IRoute
 */
public class Route implements IRoute {
    private Local from;
    private Local to;
    private Double distance;

    public Route() {
    }

    public Route(Local from, Local to, Double distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Local getFrom() {
        return from;
    }

    public void setFrom(Local from) {
        this.from = from;
    }

    public Local getTo() {
        return to;
    }

    public void setTo(Local to) {
        this.to = to;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String toString() {
        return "Route{" +
                "from=" + from +
                ", to=" + to +
                ", distance=" + distance +
                '}';
    }
}
