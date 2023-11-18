package es.unican.carchargers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.checkerframework.checker.units.qual.C;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

import es.unican.carchargers.activities.main.IMainContract;
import es.unican.carchargers.activities.main.MainPresenter;
import es.unican.carchargers.common.ChargersProcessor;
import es.unican.carchargers.common.ChargersSorting;
import es.unican.carchargers.constants.ESorting;
import es.unican.carchargers.model.Charger;
import es.unican.carchargers.repository.IRepository;
import es.unican.carchargers.repository.Repositories;

public class MainPresenterTest {
    //Aqui se prueba el metodo MainPresenter.onDistanceSortingClicked con mocks

    @Mock
    IMainContract.View view;
    @Mock
    ChargersProcessor processor;
    @Mock
    ChargersSorting sort;

    IMainContract.Presenter sut;
    IRepository repository;
    List<Charger> listaCargadores = new ArrayList<>();
    //Para captar los argumnetos pasados al mock
    ArgumentCaptor<List<Charger>> captor;
    List<Charger>capturados = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        //Inicializa repositorio falso
        repository = Repositories.getSyncFake(listaCargadores);
        captor = ArgumentCaptor.forClass((List.class));
        sut = new MainPresenter();
        // Crear un mock de ChargersProcessor
        processor = Mockito.mock(ChargersProcessor.class);
        sort = Mockito.mock(ChargersSorting.class);
    }

    //CASO1: Filtrar por ubicación de mas cercano a lejano.
    @Test
    public void onDistanceSortingClickedTest () {
        //Ordenamiento por distancia
        ESorting sorting = ESorting.DISTANCE;

        //Creo puntos de carga y les asigno unas coordenadas
        Charger c1 = new Charger();
        c1.address.latitude = ;
        c1.address.longitude = ;

        //Anhado los cargadores a la lista
        listaCargadores.add(c1);

        //Establezco el comportamiento del mock
        when(view.getRepository()).thenReturn(repository);


        //Llamada al método testeado y verificar llamada
        sut.onDistanceSortingClicked();

        verify(view, atLeast(1)).showChargers(captor.capture());

        //Verificar ordenamiento de la lista
        capturados = captor.getValue();
        assertEquals(capturados.get(0), c1);

        //Verificar tamaño de la lista
        assertEquals(capturados.size(),2);


    }
}
