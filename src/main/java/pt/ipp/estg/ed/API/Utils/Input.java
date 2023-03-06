package pt.ipp.estg.ed.API.Utils;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The `Input` class represents a set of methods that allow the user to enter data from the console.
 *
 * @author Sérgio Félix & Carlos Leite
 * @version 1.0
 */
public class Input {
    /**
     * Reads a string from the console.
     *
     * @param message The message to be displayed to the user
     * @return The string entered by the user
     */
    public static String readString(String message) {
        String str;
        Scanner scanner = new Scanner(System.in);

        System.out.println(message);
        str = scanner.nextLine();

        return str;
    }

    /**
     * Reads an integer from the console.
     *
     * @param message    The message to be displayed to the user
     * @param limitStart The minimum value that the user can enter
     * @param limitEnd   The maximum value that the user can enter
     * @return The integer entered by the user
     */
    public static Integer readInteger(String message, int limitStart, int limitEnd) {
        Scanner scanner = new Scanner(System.in);
        int number;

        while (true) {
            try {
                System.out.println(message);
                number = scanner.nextInt();
                break;
            } catch (InputMismatchException exception) {
                System.out.println("Please enter a valid option (" + limitStart + "-" + limitEnd + ").\n");
                scanner.nextLine();
            }
        }

        while (number < limitStart || number > limitEnd) {
            System.out.println("Please enter a valid option (" + limitStart + "-" + limitEnd + ").\n");
            System.out.println(message);
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid option (" + limitStart + "-" + limitEnd + ").\n");
                System.out.println(message);
                scanner.next();
            }
            number = scanner.nextInt();
        }

        return number;
    }

    /**
     * Reads a double from the console.
     *
     * @param message    The message to be displayed to the user
     * @param limitStart The minimum value that the user can enter
     * @param limitEnd   The maximum value that the user can enter
     * @return The double entered by the user
     */
    public static Double readDouble(String message, double limitStart, double limitEnd) {
        Scanner scanner = new Scanner(System.in);
        double number;

        while (true) {
            try {
                System.out.println(message);
                number = scanner.nextDouble();
                break;
            } catch (InputMismatchException exception) {
                System.out.println("Please enter a valid option (" + limitStart + "-" + limitEnd + ").\n");
                scanner.nextLine();
            }
        }

        while (number < limitStart || number > limitEnd) {
            System.out.println("Please enter a valid option (" + limitStart + "-" + limitEnd + ").\n");
            System.out.println(message);
            while (!scanner.hasNextDouble()) {
                System.out.println("Please enter a valid option (" + limitStart + "-" + limitEnd + ").\n");
                System.out.println(message);
                scanner.next();
            }
            number = scanner.nextDouble();
        }

        return number;
    }
}
