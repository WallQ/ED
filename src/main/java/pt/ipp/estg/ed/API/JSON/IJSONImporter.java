package pt.ipp.estg.ed.API.JSON;

import java.io.IOException;

/**
 * The `IJSONImporter` interface defines a set of methods for importing the game data from a JSON file
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 */
public interface IJSONImporter {
    void importJSON() throws IOException;
}
