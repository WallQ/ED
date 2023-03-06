package pt.ipp.estg.ed.API.Local.Connector;

import pt.ipp.estg.ed.API.Player.Player;

/**
 * The `IConnector` interface defines a set of methods for a connector.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 */
public interface IConnector {
    void rechargePlayerEnergy(Player player);

    void ambushPlayer(Player player);
}
