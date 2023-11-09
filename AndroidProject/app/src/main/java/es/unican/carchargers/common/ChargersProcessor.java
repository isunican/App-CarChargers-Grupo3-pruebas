package es.unican.carchargers.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import es.unican.carchargers.constants.EOperator;
import es.unican.carchargers.constants.ESorting;
import es.unican.carchargers.model.Charger;

public class ChargersProcessor implements Function<List<Charger>, List<Charger>> {

    private EOperator activeOperator = null;
    private boolean ignoreOutdated = false;
    private ESorting sorting = null;

    private double latitude;
    private double longitude;

    public void setActiveOperator(EOperator operator) {
        this.activeOperator = operator;
    }

    public void setSorting(ESorting sorting) {
        this.sorting = sorting;
    }

    public void setIgnoreOutdated(boolean state) {
        this.ignoreOutdated = state;
    }

    public void setLocation(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    @Override
    public List<Charger> apply(List<Charger> chargers) {
        List<Charger> output = new ArrayList<>(chargers);

        if (ignoreOutdated) {
            output = ChargersFilter.ignoreOutdated(output);
        }

        output = ChargersFilter.filterByOperator(output, activeOperator);

        if (sorting != null) {
            switch (sorting) {
                case POWER:
                    output = ChargersSorting.sortByPower(output);
                    break;
                case COST:
                    output = ChargersSorting.sortByCost(output);
                    break;
                case DISTANCE:
                    output = ChargersSorting.sortByDistance(output, latitude, longitude);
                    break;
            }
        }

        return output;
    }
    
}
