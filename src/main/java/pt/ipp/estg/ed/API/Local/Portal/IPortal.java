package pt.ipp.estg.ed.API.Local.Portal;

import pt.ipp.estg.ed.API.Player.Player;

/**
 * The `IConnector` interface defines a set of methods for a portal.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 */
public interface IPortal {
    void conquerPortal(Player player, Integer energy);

    void chargePortal(Player player, Integer energy);
}
