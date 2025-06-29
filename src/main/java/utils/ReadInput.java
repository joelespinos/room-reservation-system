package utils;

import controller.ReservationSystemController;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Scanner;

public class ReadInput {

    private static final int REPEAT = -1;

    /**
     * Lee un entero desde consola, permitiendo mantener el valor anterior si se deja en blanco.
     * Valida que el número sea mayor o igual que minValue.
     * @param keyboard Scanner para leer desde consola.
     * @param oldValue Valor anterior a mantener si el usuario deja en blanco.
     * @param prompt Mensaje a mostrar al usuario.
     * @param minValue Valor mínimo permitido.
     * @return El número leído o el valor anterior.
     */
    public static int readIntMinValue(Scanner keyboard, int oldValue, String prompt, int minValue) {
        String numberStr;
        int number;
        do {
            try {
                System.out.print("Ingrese " + prompt + ": ");
                numberStr = keyboard.nextLine();
                if (numberStr.isBlank()) {
                    number = oldValue;
                } else {
                    number = Integer.parseInt(numberStr);

                    if (!InputValidator.isNumberValid(number, minValue))
                        System.out.println("ERROR! " + prompt + " debe ser mayor o igual a " + minValue);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! Debe ingresar un número entero.");
                number = REPEAT;
            }
        } while (!InputValidator.isNumberValid(number, minValue));
        return number;
    }

    /**
     * Lee un entero positivo desde consola, validando que sea mayor o igual que minValue.
     * @param keyboard Scanner para leer desde consola.
     * @param prompt Mensaje a mostrar al usuario.
     * @param minValue Valor mínimo permitido.
     * @return El número leído.
     */
    public static int readPositiveInt(Scanner keyboard, String prompt, int minValue) {
        String numberStr;
        int number;
        do {
            try {
                do {
                    System.out.print("Ingrese " + prompt + ": ");
                    numberStr = keyboard.nextLine();
                    if (numberStr.isBlank())
                        System.out.println("ERROR! " + prompt + " no puede estar en blanco o vacío");
                } while (numberStr.isBlank());

                number = Integer.parseInt(numberStr);

                if (!InputValidator.isNumberValid(number, minValue))
                    System.out.println("ERROR! " + prompt + " debe ser mayor o igual a " + minValue);

            } catch (NumberFormatException e) {
                System.out.println("Error! Debe ingresar un número entero.");
                number = REPEAT;
            }
        } while (!InputValidator.isNumberValid(number, minValue));
        return number;
    }

    /**
     * Lee una cadena desde consola, permitiendo mantener el valor anterior si se deja en blanco.
     * Valida longitud máxima y que no sea solo numérica.
     * @param keyboard Scanner para leer desde consola.
     * @param oldValue Valor anterior a mantener si el usuario deja en blanco.
     * @param prompt Mensaje a mostrar al usuario.
     * @param maxChars Número máximo de caracteres permitidos.
     * @return La cadena leída o el valor anterior.
     */
    public static String readString(Scanner keyboard, String oldValue, String prompt, int maxChars) {
        String newValue;
        do {
            System.out.print("Ingrese " + prompt + ": ");
            newValue = keyboard.nextLine();

            if (newValue.isBlank())
                newValue = oldValue;

            else if (newValue.length() > maxChars)
                System.out.println("ERROR, " + prompt + " es demasiado largo (max " + maxChars + " caracteres)");

            else if (newValue.matches("\\d+"))
                System.out.println("ERROR, " + prompt + " no puede ser solo numerico");
        } while (!InputValidator.isStringToUpdateNumValid(newValue, maxChars));

        return newValue;
    }

    /**
     * Lee una cadena desde consola, permitiendo mantener el valor anterior si se deja en blanco.
     * Valida solo la longitud máxima.
     * @param keyboard Scanner para leer desde consola.
     * @param oldValue Valor anterior a mantener si el usuario deja en blanco.
     * @param prompt Mensaje a mostrar al usuario.
     * @param maxChars Número máximo de caracteres permitidos.
     * @return La cadena leída o el valor anterior.
     */
    public static String readStringNum(Scanner keyboard, String oldValue, String prompt, int maxChars) {
        String newValue;
        do {
            System.out.print("Ingrese " + prompt + ": ");
            newValue = keyboard.nextLine();

            if (newValue.isBlank())
                newValue = oldValue;

            else if (newValue.length() > maxChars)
                System.out.println("ERROR, " + prompt + " es demasiado largo (max " + maxChars + " caracteres)");

        } while (!InputValidator.isStringToUpdateValid(newValue, maxChars));

        return newValue;
    }

    /**
     * Lee una cadena no vacía desde consola, validando longitud máxima y que no sea solo numérica.
     * @param keyboard Scanner para leer desde consola.
     * @param prompt Mensaje a mostrar al usuario.
     * @param maxChars Número máximo de caracteres permitidos.
     * @return La cadena leída.
     */
    public static String readNonEmptyString(Scanner keyboard, String prompt, int maxChars) {
        String str;
        do {
            System.out.print("Ingrese " + prompt + ": ");
            str = keyboard.nextLine();

            if (str.isBlank())
                System.out.println("ERROR! " + prompt + " no puede estar en blanco o vacío");

            else if (str.length() > maxChars)
                System.out.println("ERROR, " + prompt + " es demasiado largo (max " + maxChars + " caracteres)");

            else if (str.matches("\\d+"))
                System.out.println("ERROR, " + prompt + " no puede ser solo numerico");
        } while (!InputValidator.isStringNotNumValid(str, maxChars));

        return str;
    }

    /**
     * Lee una cadena no vacía desde consola, validando solo la longitud máxima.
     * @param keyboard Scanner para leer desde consola.
     * @param prompt Mensaje a mostrar al usuario.
     * @param maxChars Número máximo de caracteres permitidos.
     * @return La cadena leída.
     */
    public static String readNonEmptyStringNum(Scanner keyboard, String prompt, int maxChars) {
        String str;
        do {
            System.out.print("Ingrese " + prompt + ": ");
            str = keyboard.nextLine();

            if (str.isBlank())
                System.out.println("ERROR! " + prompt + " no puede estar en blanco o vacío");

            else if (str.length() > maxChars)
                System.out.println("ERROR, " + prompt + " es demasiado largo (max " + maxChars + " caracteres)");

        } while (!InputValidator.isStringValid(str, maxChars));

        return str;
    }

    /**
     * Lee una fecha desde consola en formato YYYY-MM-DD y valida que sea correcta y futura.
     * @param keyboard Scanner para leer desde consola.
     * @param prompt Mensaje a mostrar al usuario.
     * @return La fecha leída.
     */
    public static String readDate(Scanner keyboard, String prompt) {
        String date;
        do {
            System.out.print("Ingrese " + prompt + " (YYYY-MM-DD): ");
            date = keyboard.nextLine();

            if (!InputValidator.isDateValid(date)) {
                System.out.println("ERROR! Fecha no válida.");
            }
        } while (!InputValidator.isDateValid(date));
        return date;
    }

    /**
     * Lee una hora desde consola en formato HH:MM, validando según la fecha y una hora opcional mínima.
     * @param keyboard Scanner para leer desde consola.
     * @param prompt Mensaje a mostrar al usuario.
     * @param optionalTime Hora mínima opcional para validar.
     * @param reservationDate Fecha de la reserva.
     * @return La hora leída.
     */
    public static String readTimeAccordingADate(Scanner keyboard, String prompt,
                                                Optional<LocalTime> optionalTime, LocalDate reservationDate) {
        String time;
        do {
            System.out.print("Ingrese " + prompt + " (HH:MM): ");
            time = keyboard.nextLine();

            if (!InputValidator.isTimeValidAccordingADate(time, reservationDate)) {
                System.out.println("ERROR! Hora no válida.");
            }
            else if (!InputValidator.isTimeAfterOptional(time, optionalTime)) {
                System.out.println("ERROR! La hora debe ser posterior a " + optionalTime.get());
            }
        } while (!InputValidator.isTimeValidAccordingADate(time, reservationDate) || !InputValidator.isTimeAfterOptional(time, optionalTime));
        return time;
    }

    /**
     * Lee un DNI desde consola y valida que sea correcto y único en el sistema.
     * @param keyboard Scanner para leer desde consola.
     * @param prompt Mensaje a mostrar al usuario.
     * @param controller Controlador para comprobar unicidad del DNI.
     * @return El DNI leído.
     */
    public static String readDNI(Scanner keyboard, String prompt, ReservationSystemController controller) {
        String dni = "";

        try {
            do {
                System.out.print("Ingrese " + prompt + ": ");
                dni = keyboard.nextLine();

                if (dni.isBlank()) {
                    System.out.println("ERROR! " + prompt + " no puede estar en blanco o vacío");
                } else if (!InputValidator.isDNIValid(dni, controller)) {
                    System.out.println("ERROR! DNI no válido. O repetido en el sistema.");
                }
            } while (dni.isBlank() || !InputValidator.isDNIValid(dni, controller));
        } catch (SQLException e) {
            System.out.println("ERROR SQL, no se ha podido validar el DNI en el sistema");
        }

        return dni;
    }
}
