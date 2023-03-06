package pt.ipp.estg.ed.API.JSON;

import java.io.IOException;

/**
 * The `IJSONExporter` interface defines a set of methods for exporting the game data to a JSON file
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 */
public interface IJSONExporter {
    void exportJSON() throws IOException;
}
