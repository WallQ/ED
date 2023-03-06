package pt.ipp.estg.ed.API.Utils;

import pt.ipp.estg.data.structures.List.UnorderedArrayList;
import pt.ipp.estg.data.structures.List.UnorderedListADT;

/**
 * The `ConsoleBuilder` class represents a console menu builder.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 */
public class ConsoleBuilder {
    private final StringBuilder builder;
    private final UnorderedListADT<String> options;
    private int countOptions;
    private int frameSize;

    public ConsoleBuilder() {
        builder = new StringBuilder();
        options = new UnorderedArrayList<>();
        countOptions = 1;
        frameSize = 0;
    }

    /**
     * Sets the title of the console menu.
     *
     * @param title The title of the console menu
     * @return The console builder
     */
    public ConsoleBuilder setTitle(String title) {
        builder.append("=".repeat(title.length()));
        builder.append(" ").append(title).append(" ");
        builder.append("=".repeat(title.length()));
        builder.append("\n");

        frameSize = (title.length() * 3) + 2;

        return this;
    }

    /**
     * Adds an option to the console menu.
     *
     * @param option The option to be added
     * @return The console builder
     */
    public ConsoleBuilder addOption(String option) {
        options.addToRear(countOptions + ". " + option + "\n");
        countOptions++;
        return this;
    }

    /**
     * Builds the console menu.
     *
     * @return The console menu
     */
    public String build() {
        for (String op : options) {
            builder.append(op);
        }

        builder.append("0. Sair\n");
        builder.append("=".repeat(frameSize));
        builder.append("\n");

        return builder.toString();
    }
}
