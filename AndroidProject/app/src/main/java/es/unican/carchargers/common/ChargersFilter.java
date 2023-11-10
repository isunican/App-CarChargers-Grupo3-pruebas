package es.unican.carchargers.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import es.unican.carchargers.constants.EOperator;
import es.unican.carchargers.model.Charger;

/**
 * Methods to perform filtering of chargers
 */
public class ChargersFilter {

    /**
     * Selects the chargers that are owned by the given operator.
     * If the operator is null, every charger is selected
     * @param input
     * @param operator
     * @return
     */
    public static List<Charger> filterByOperator(List<Charger> input, EOperator operator) {
        if (operator == null) {
            return input;
        }

        List<Charger> output = new ArrayList<>();
        for (Charger charger : input) {
            EOperator chargerOperator = EOperator.fromId(charger.operator.id);
            if (operator == chargerOperator) {
                output.add(charger);
            }
        }
        return output;
    }

    /**
     * Selects the chargers that were verified less than 6 months ago
     * @param input
     * @return
     */
    public static List<Charger> ignoreOutdated(List<Charger> input) {
        LocalDateTime limit = LocalDateTime.now().minus(6, ChronoUnit.MONTHS);

        List<Charger> output = new ArrayList<>();
        for (Charger charger : input) {
            LocalDateTime lastVerified = getChargerLastVerified(charger);
            if (lastVerified.isAfter(limit)) {
                output.add(charger);
            }
        }

        return input;
    }

    /**
     * Returns the DateTime at which the charger was last verified
     * @param charger
     * @return
     */
    private static LocalDateTime getChargerLastVerified(Charger charger) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        Instant instant = Instant.from(formatter.parse(charger.dateLastVerified));
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return dateTime;
    }

}
