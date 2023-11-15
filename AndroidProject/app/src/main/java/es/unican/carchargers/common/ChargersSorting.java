package es.unican.carchargers.common;

import android.location.Location;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.unican.carchargers.model.Charger;
import es.unican.carchargers.model.Connection;

/**
 * Methods to sort chargers
 */
public class ChargersSorting {

    public static final double EARTH_RADIUS = 6371d;

    /**
     * Sort the chargers according to the distance to the given origin coordinates, in ascending
     * order
     * @param input
     * @param originLat
     * @param originLon
     * @return
     */
    public static List<Charger> sortByDistance(List<Charger> input, double originLat, double originLon) {
        DistanceComparator comparator = new DistanceComparator(originLat, originLon);
        List<Charger> output = new ArrayList<>(input);
        output.sort(comparator);
        return output;
    }

    /**
     * Sort the chargers according to their maximum cost per kwh, in ascending order
     * @param input
     * @return
     */
    public static List<Charger> sortByCost(List<Charger> input) {
        CostComparator comparator = new CostComparator();
        List<Charger> output = new ArrayList<>(input);
        output.sort(comparator);
        return output;
    }

    /**
     * Sort the chargers according to their maximum charging power they provide, in descending order
     * @param input
     * @return
     */
    public static List<Charger> sortByPower(List<Charger> input) {
        PowerComparator comparator = new PowerComparator();
        List<Charger> output = new ArrayList<>(input);
        output.sort(comparator);
        return output;
    }

    /**
     * Comparator to sort chargers according to their distance to an origin location
     */
    private static class DistanceComparator implements Comparator<Charger> {

        private final double originLon;
        private final double originLat;

        public DistanceComparator(double originLat, double originLon) {
            this.originLat = originLat;
            this.originLon = originLon;
        }

        @Override
        public int compare(Charger o1, Charger o2) {
            double lat1 = Double.parseDouble(o1.address.latitude);
            double lon1 = Double.parseDouble(o1.address.longitude);
            double dist1 = calculateDistance(originLat, originLon, lat1, lon1);

            double lat2 = Double.parseDouble(o2.address.latitude);
            double lon2 = Double.parseDouble(o2.address.longitude);
            double dist2 = calculateDistance(originLat, originLon, lat2, lon2);

            return (int) (dist1 - dist2);
        }
    }

    /**
     * Comparator to sort chargers according to their maximum cost per kwh
     */
    private static class CostComparator implements Comparator<Charger> {

        @Override
        public int compare(Charger o1, Charger o2) {
            double cost1 = getChargerCost(o1);
            double cost2 = getChargerCost(o2);
            double diff = cost1 - cost2;
            return (int) (diff*1000);
        }
    }

    /**
     * Comparator to sort chargers according to the maximum charging power they provide
     */
    public static class PowerComparator implements Comparator<Charger> {

        @Override
        public int compare(Charger o1, Charger o2) {
            double power1 = getChargerPower(o1);
            double power2 = getChargerPower(o2);
            return (int) (power2 - power1);
        }
    }

    /**
     * Returns the distance between 2 locations, provided as two pair of latitudes and longitudes
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] distance = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, distance);
        return distance[0];
    }

    /**
     * Returns the maximum cost per kw/h of this charger
     * Returns Double.MAX_VALUE if the value is not defined
     * @param charger
     * @return
     */
    public static double getChargerCost(Charger charger) {
        if (charger.usageCost == null) {
            return Double.MAX_VALUE ;
        }

        final Pattern pattern = Pattern.compile("(\\d+(?:,\\d+)?)€");
        final Matcher matcher = pattern.matcher(charger.usageCost);

        final List<String> values = new ArrayList<>();
        while(matcher.find()) {
            String str = matcher.group().replace("€", "").replace(",", ".");
            values.add(str);
        }

        if (values.isEmpty()) {
            return Double.MAX_VALUE;
        }

        double maxCost = 0d;
        for (String value : values) {
            double cost = Double.valueOf(value);
            if (cost > maxCost) {
                maxCost = cost;
            }
        }

        return maxCost;
    }

    /**
     * Returns the maximum power provided by this charger, in kw
     * Returns 0 if the value is not provided
     * @param charger
     * @return
     */
    public static double getChargerPower(Charger charger) {
        if (charger.connections == null) {
            return 0d;
        }

        double maxPower = 0d;
        for (Connection connection : charger.connections) {
            if (connection.powerKw == null) {
                continue;
            }
            double power = Double.valueOf(connection.powerKw);
            if (power > maxPower) {
                maxPower = power;
            }
        }
        return maxPower;
    }

}
