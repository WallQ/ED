package pt.ipp.estg.ed.API.Team;

import java.util.Iterator;

/**
 * The `ITeamManagement` interface defines a set of methods for managing teams.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 */
public interface ITeamManagement {
    Iterator<Team> getAllTeams();

    Team getTeamById(String id);

    Team getTeamByName(String name);

    void addTeam(Team team);

    void removeTeamById(String id);

    void removeTeamByName(String name);

    void sortById();

    void sortByName();

    boolean isEmpty();

    int size();
}
