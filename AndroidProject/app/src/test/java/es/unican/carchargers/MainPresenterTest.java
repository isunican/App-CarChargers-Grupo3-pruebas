package es.unican.carchargers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import es.unican.carchargers.activities.main.IMainContract;
import es.unican.carchargers.activities.main.MainPresenter;
import es.unican.carchargers.common.ChargersProcessor;
import es.unican.carchargers.model.Charger;
import es.unican.carchargers.repository.IRepository;
import es.unican.carchargers.repository.Repositories;

public class MainPresenterTest {
    @Mock
    IMainContract.View mainView;
    @Mock
    IMainContract.Presenter presenter;
    @Mock
    ChargersProcessor processorMock;
    ArgumentCaptor<List<Charger>> captor; //Para captar los argumnetos pasados al mock
    List<Charger>capturados = new ArrayList<>();
    Charger c1,c2,c3;

    @Before
    public void ini() {
        MockitoAnnotations.openMocks(this);
        presenter = new MainPresenter();
        processorMock = new ChargersProcessor();
        captor = ArgumentCaptor.forClass((List.class));


    }
    @Test
    //CASO 1: con valor de entrada true
    public void onIgnoreOutDatedChargers1Test() {
        //Creo los puntos de carga y le asigno una fecha de ultima verificacion
        c1 = new Charger();
        c1.dateLastVerified = "2023-09-13T12:34:56Z" ;
        c2 = new Charger();
        c2.dateLastVerified =  "2023-01-13T12:34:56Z";
        c3 = new Charger();
        c3.dateLastVerified = "2023-01-13T12:34:57Z";
        List<Charger> chargers = new ArrayList<>();
        //los añado a la lista
        chargers.add(c1);
        chargers.add(c2);
        chargers.add(c3);

        //creo el repositorio falso y establezco el comportamiento del mock
        IRepository repo = Repositories.getSyncFake(chargers);
        when(mainView.getRepository()).thenReturn(repo);
        presenter.init(mainView);
        //llamo al metodo y verifico que se ha llamado
        presenter.onIgnoreOutdatedChargers(true);
        verify(mainView, atLeast(1)).showChargers(captor.capture());
        capturados = captor.getValue();
        //compruebo que me devuelve el cargador
        assertEquals(capturados.get(0), c1);
        //compruebo el tamaño de la lista
        assertEquals(capturados.size(),1);


    }

    @Test
    //CASO 2: con valor de entrada false
    public void onIgnoreOutDatedChargers2Test() {
        //Creo los puntos de carga y le asigno una fecha de ultima verificacion
        c1 = new Charger();
        c1.dateLastVerified = "2023-09-13T12:34:56Z" ;
        c2 = new Charger();
        c2.dateLastVerified =  "2023-01-13T12:34:56Z";
        c3 = new Charger();
        c3.dateLastVerified = "2023-01-13T12:34:57Z";
        List<Charger> chargers = new ArrayList<>();
        //los añado a la lista
        chargers.add(c1);
        chargers.add(c2);
        chargers.add(c3);

        //creo el repositorio falso y establezco el comportamiento del mock
        IRepository repo = Repositories.getSyncFake(chargers);
        when(mainView.getRepository()).thenReturn(repo);
        presenter.init(mainView);
        //llamo al metodo y verifico que se ha llamado
        presenter.onIgnoreOutdatedChargers(false);
        verify(mainView, atLeast(1)).showChargers(captor.capture());
        capturados = captor.getValue();
        //compruebo que me devuelve los cargadores
        assertEquals(capturados.get(0), c1);
        assertEquals(capturados.get(1), c2);
        assertEquals(capturados.get(2), c3);
        //compruebo el tamaño de la lista
        assertEquals(capturados.size(),3);


    }
}
