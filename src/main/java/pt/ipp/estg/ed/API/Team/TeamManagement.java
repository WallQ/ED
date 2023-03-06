package pt.ipp.estg.ed.API.Team;

import pt.ipp.estg.algorithms.Sort.MergeSort;
import pt.ipp.estg.data.structures.List.UnorderedLinkedList;
import pt.ipp.estg.data.structures.List.UnorderedListADT;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

/**
 * The `TeamManagement` class represents a set of methods for managing teams.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 * @implements ITeamManagement
 */
public class TeamManagement implements ITeamManagement {
    private final UnorderedListADT<Team> teams;

    public TeamManagement() {
        this.teams = new UnorderedLinkedList<>();
    }

    public UnorderedListADT<Team> getTeams() {
        return teams;
    }

    public Iterator<Team> getAllTeams() {
        return this.teams.iterator();
    }

    public Team getTeamById(String id) {
        for (Team team : this.teams) {
            if (team.getId().equals(id)) {
                return team;
            }
        }

        return null;
    }

    public Team getTeamByName(String name) {
        for (Team team : this.teams) {
            if (team.getName().equals(name)) {
                return team;
            }
        }

        return null;
    }

    public void addTeam(Team team) {
        if (Objects.isNull(team)) throw new IllegalArgumentException("Team cannot be null!");
        this.teams.addToRear(team);
    }

    public void removeTeamById(String id) {
        if (Objects.isNull(getTeamById(id))) throw new IllegalArgumentException("Team id cannot be null!");
        this.teams.remove(getTeamById(id));
    }

    public void removeTeamByName(String name) {
        if (Objects.isNull(getTeamById(name))) throw new IllegalArgumentException("Team name cannot be null!");
        this.teams.remove(getTeamByName(name));
    }

    public void sortById() {
        Team[] teamsArray = getTeamsArray();

        MergeSort.sort(teamsArray, Comparator.comparing(Team::getId));
    }

    public void sortByName() {
        Team[] teamsArray = getTeamsArray();

        MergeSort.sort(teamsArray, Comparator.comparing(Team::getName));
    }

    private Team[] getTeamsArray() {
        Team[] teamsArray = new Team[this.teams.size()];

        Iterator<Team> iterator = this.teams.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            teamsArray[i] = iterator.next();
            i++;
        }

        return teamsArray;
    }

    public boolean isEmpty() {
        return this.teams.isEmpty();
    }

    public int size() {
        return this.teams.size();
    }

    public String toString() {
        return "TeamManagement{" +
                "teams=" + teams +
                '}';
    }
}
