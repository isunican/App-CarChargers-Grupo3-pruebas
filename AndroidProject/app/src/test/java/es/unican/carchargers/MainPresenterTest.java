package es.unican.carchargers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import es.unican.carchargers.activities.main.IMainContract;
import es.unican.carchargers.activities.main.MainPresenter;
import es.unican.carchargers.common.ChargersFilter;
import es.unican.carchargers.common.ChargersProcessor;
import es.unican.carchargers.model.Charger;
import es.unican.carchargers.repository.IRepository;
import es.unican.carchargers.repository.Repositories;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock private IMainContract.View view;
    private IMainContract.Presenter sut;
    //private ChargersFilter filtrado;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
        sut = new MainPresenter();

    }

    @Test
    public void onIgnoreOutdatedChargersTest() {

        //Creacion de cargadores
        Charger charger1 = new Charger();
        charger1.id = "1";
        Charger charger2 = new Charger();
        charger2.id = "2";
        Charger charger3 = new Charger();
        charger3.id = "3";
        Charger charger4 = new Charger();
        charger4.id = "4";
        List<Charger> chargers = new ArrayList<>();
        List<Charger> chargersUpdated = new ArrayList<>();

        //Solo cargadores 1 y 2 pasan el filtro de actualizado
        charger1.dateLastVerified = "2023-11-14T17:49:00Z";
        charger2.dateLastVerified = "2023-11-14T17:49:00Z";
        charger3.dateLastVerified = "2023-03-14T17:49:00Z";
        charger4.dateLastVerified = "2023-03-14T17:49:00Z";

        //Se añaden todos los cargadores a la lista
        chargers.add(charger1);
        chargers.add(charger2);
        chargers.add(charger3);
        chargers.add(charger4);

        //Se añaden los cargadores actualizados a una segunda lista
        chargersUpdated.add(charger1);
        chargersUpdated.add(charger2);

        IRepository repository = Repositories.getSyncFake(chargers);
        when(view.getRepository()).thenReturn(repository);
        sut.init(view);

        //Caso verdadero, devuelve solo los dos primeros
        sut.onIgnoreOutdatedChargers(true);
        verify(view, times(1)).showChargers(chargersUpdated);

        //Caso falso, devuelve la lista completa
        sut.onIgnoreOutdatedChargers(false);
        verify(view, times(1)).showChargers(chargers);

    }

}