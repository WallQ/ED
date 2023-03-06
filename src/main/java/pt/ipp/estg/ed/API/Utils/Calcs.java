package pt.ipp.estg.ed.API.Utils;

import pt.ipp.estg.ed.API.Local.Coordinates;

/**
 * The `Calcs` class represents the calculations used in the application.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 */
public class Calcs {
    /**
     * Calculates the distance between two coordinates
     *
     * @param coordinates1 The first coordinates
     * @param coordinates2 The second coordinates
     * @return The distance between the two coordinates
     */
    public static double calculateDistance(Coordinates coordinates1, Coordinates coordinates2) {
        return Math.round(Math.sqrt(Math.pow(coordinates2.getLatitude() - coordinates1.getLatitude(), 2.0) + Math.pow(coordinates2.getLongitude() - coordinates1.getLongitude(), 2.0)) / 2 * 100.0) / 100.0;
    }

    /**
     * Calculates the experience needed to reach a certain level
     *
     * @param level The level to calculate the experience
     * @return The experience needed to reach the level
     */
    public static int calculateExperience(Integer level) {
        return (int) Math.pow((level / 0.07), 2.0);
    }
}
