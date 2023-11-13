package es.unican.carchargers.activities.main;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

import es.unican.carchargers.common.ChargersFilter;
import es.unican.carchargers.common.ChargersProcessor;
import es.unican.carchargers.common.ChargersSorting;
import es.unican.carchargers.constants.ESorting;
import es.unican.carchargers.repository.ICallBack;
import es.unican.carchargers.constants.ECountry;
import es.unican.carchargers.constants.ELocation;
import es.unican.carchargers.constants.EOperator;
import es.unican.carchargers.model.Charger;
import es.unican.carchargers.repository.IRepository;
import es.unican.carchargers.repository.service.APIArguments;

public class MainPresenter implements IMainContract.Presenter {

    /** the view controlled by this presenter */
    private IMainContract.View view;

    /** all chargers that have been downloaded */
    private List<Charger> allChargers;

    /** a cached list of charging stations currently shown */
    private List<Charger> shownChargers;

    /** */
    private ELocation location = ELocation.SANTANDER;

    /** processor to filter and sort chargers */
    private final ChargersProcessor processor = new ChargersProcessor();

    @Override
    public void init(IMainContract.View view) {
        this.view = view;
        this.processor.setLocation(location.lat, location.lon);
        view.init();
        load();
    }

    /**
     * This method requests a list of charging stations from the repository, and requests
     * the view to show them.
     */
    private void load() {
        IRepository repository = view.getRepository();

        // set API arguments to retrieve charging stations that match some criteria
        APIArguments args = APIArguments.builder()
                .setCountryCode(ECountry.SPAIN.code)
                .setLevelId(3)         // level 3 (over 40kw)
                .setStatusId(50)       // status 50 (Operational)
                .setMaxResults(1000);   // limit download to 1000 results

        ICallBack callback = new ICallBack() {
            @Override
            public void onSuccess(List<Charger> chargers) {
                updateChargers(chargers);
            }

            @Override
            public void onFailure(Throwable e) {
                MainPresenter.this.allChargers = Collections.emptyList();
                MainPresenter.this.shownChargers = Collections.emptyList();
                view.showLoadError();
            }
        };

        // asynchronously request chargers
        repository.requestChargers(args, callback);
    }

    /**
     *
     * @param chargers
     */
    private void updateChargers(List<Charger> chargers) {
        this.allChargers = chargers != null ? chargers : Collections.emptyList();
        this.shownChargers = this.processor.apply(this.allChargers);
        view.showChargers(shownChargers);
        view.showLoadCorrect(shownChargers.size());
    }

    @Override
    public void onChargerClicked(int index) {
        if (shownChargers != null && index < shownChargers.size()) {
            Charger charger = shownChargers.get(index);
            view.showChargerDetails(charger);
        }
    }

    @Override
    public void onMenuInfoClicked() {
        view.showInfoActivity();
    }

    @Override
    public void onDistanceSortingClicked() {
        this.processor.setSorting(ESorting.DISTANCE);
        updateChargers(allChargers);
    }

    @Override
    public void onPowerSortingClicked() {
        this.processor.setSorting(ESorting.POWER);
        updateChargers(allChargers);
    }

    @Override
    public void onCostSortingClicked() {
        this.processor.setSorting(ESorting.COST);
        updateChargers(allChargers);
    }

    @Override
    // este
    public void onIgnoreOutdatedChargers(boolean checked) {
        this.processor.setIgnoreOutdated(checked);
        updateChargers(allChargers);
    }

    @Override
    public void onOperatorFilterClicked(EOperator operator) {
        this.processor.setActiveOperator(operator);
        updateChargers(allChargers);
    }

}
