package jyrs.dev.vivesbank.products.creditCards.generator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class ExpDateGenerator {

    public static String generator() {
        // Fecha actual
        LocalDate hoy = LocalDate.now();

        // Rango de caducidad: entre 1 y 5 años
        int anosCaducidad = ThreadLocalRandom.current().nextInt(1, 6); // Generar un año entre 1 y 5
        int mesCaducidad = ThreadLocalRandom.current().nextInt(1, 13); // Generar un mes entre 1 y 12

        // Generar fecha de caducidad
        LocalDate fechaCaducidad = hoy.plusYears(anosCaducidad).withMonth(mesCaducidad);

        // Formatear la fecha al formato MM/YY
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        return fechaCaducidad.format(formatter);
    }
}
