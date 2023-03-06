package pt.ipp.estg.ed.API.Team;

import java.util.UUID;

/**
 * The `Team` class represents a team.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 * @implements ITeam
 */
public class Team implements ITeam {
    private String id;
    private String name;

    public Team() {
    }

    public Team(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Team{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
