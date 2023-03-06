package pt.ipp.estg.ed.API.Config;

/**
 * The `Config` class defines a collection of static constants that serve as the default configuration settings for the game.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 */
public class Config {
    public static final String TEAM_INITIAL_NAME_1 = "Giants";
    public static final String TEAM_INITIAL_NAME_2 = "Sparks";
    public static final Integer PLAYER_INITIAL_LEVEL = 1;
    public static final Integer PLAYER_MAX_LEVEL = 30;
    public static final Integer PLAYER_INITIAL_EXPERIENCE = 0;
    public static final Integer PLAYER_INITIAL_ENERGY = 100;
    public static final Integer PLAYER_INITIAL_MAX_ENERGY = 100;
    public static final Integer PLAYER_MAX_ENERGY_PER_LEVEL = 25;
    public static final Integer PLAYER_TRAVEL_EXPERIENCE = 15;
    public static final Integer PLAYER_RECHARGE_ENERGY_EXPERIENCE = 30;
    public static final Integer PLAYER_CHARGE_PORTAL_EXPERIENCE = 30;
    public static final Integer PLAYER_ATTACK_PORTAL_EXPERIENCE = 50;
    public static final Integer PLAYER_CONQUER_PORTAL_EXPERIENCE = 100;
    public static final Integer PORTAL_INITIAL_ENERGY = 100;
    public static final Integer PORTAL_MAX_ENERGY = Integer.MAX_VALUE;
    public static final Double PORTAL_CONQUER_MIN_ENERGY_REQUIRED = 0.25;
    public static final Integer CONNECTOR_AMOUNT_ENERGY_RECHARGEABLE = 50;
    public static final Integer CONNECTOR_COOLDOWN_IN_SECONDS = 10;
    public static final Double CONNECTOR_AMOUNT_TRAPS = 0.25;
    public static final Double CONNECTOR_AMOUNT_PUNISH_ENERGY = 0.5;
}
