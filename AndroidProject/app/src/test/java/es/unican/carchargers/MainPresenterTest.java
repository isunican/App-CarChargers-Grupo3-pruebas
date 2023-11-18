package es.unican.carchargers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import es.unican.carchargers.activities.main.IMainContract;
import es.unican.carchargers.activities.main.MainPresenter;
import es.unican.carchargers.common.ChargersProcessor;
import es.unican.carchargers.constants.ESorting;
import es.unican.carchargers.model.Charger;
import es.unican.carchargers.repository.IRepository;
import es.unican.carchargers.repository.Repositories;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class MainPresenterTest {
    //Aqui se prueba el metodo MainPresenter.onDistanceSortingClicked con mocks
    @Mock
    IMainContract.View view;
    @Mock
    ChargersProcessor processor;
    IMainContract.Presenter sut;
    IRepository repository;
    List<Charger> listaCargadores = new ArrayList<>();
    //Para obtener los argumnetos pasados al mock
    ArgumentCaptor<List<Charger>> captor;
    List<Charger> capturados = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        //Inicializa repositorio de cargadores para el test
        repository = Repositories.getSyncFake(listaCargadores);
        captor = ArgumentCaptor.forClass((List.class));
        sut = new MainPresenter();
        // Crear un mock de ChargersProcessor
        processor = Mockito.mock(ChargersProcessor.class);
    }

    //CASO1: Ordenar con lista ya ordenada
    @Test
    public void onDistanceSortingClickedCasoUnoTest() {
        //Creo puntos de carga y les asigno unas coordenadas
        Charger c1 = new Charger();
        c1.address.latitude = "52.343197";
        c1.address.longitude = "-0.170632";
        Charger c2 = new Charger();
        c2.address.latitude = "-29.6866";
        c2.address.longitude = "1.170632";
        Charger c3 = new Charger();
        c3.address.latitude = "-30.6866";
        c3.address.longitude = "1.170632";

        //Anhado los cargadores a la lista
        listaCargadores.add(c1);
        listaCargadores.add(c2);
        listaCargadores.add(c3);

        //Establezco el comportamiento del mock
        when(view.getRepository()).thenReturn(repository);
        sut.init(view);

        //Llamada al método testeado y verificar llamada
        sut.onDistanceSortingClicked();

        verify(view, atLeast(1)).showChargers(captor.capture());

        //Verificar ordenamiento de la lista
        capturados = captor.getValue();
        assertEquals(capturados.get(0), c1);
        assertEquals(capturados.get(1), c2);
        assertEquals(capturados.get(2), c3);
        //Verificar tamanho de la lista
        assertEquals(capturados.size(), 3);
    }

    //CASO 2: Ordenar con lista desordenada
    @Test
    public void onDistanceSortingClickedCasoDosTest() {
        //Creo puntos de carga y les asigno unas coordenadas
        Charger c1 = new Charger();
        c1.address.latitude = "52.343197";
        c1.address.longitude = "-0.170632";
        Charger c2 = new Charger();
        c2.address.latitude = "53.343197";
        c2.address.longitude = "1.170632";
        Charger c3 = new Charger();
        c3.address.latitude = "-30.6866";
        c3.address.longitude = "1.170632";

        //Anhado los cargadores a la lista de manera desordenada
        listaCargadores.add(c1);
        listaCargadores.add(c3);
        listaCargadores.add(c2);

        //Establezco el comportamiento del mock
        when(view.getRepository()).thenReturn(repository);
        sut.init(view);


        //Llamada al método testeado y verificar llamada
        sut.onDistanceSortingClicked();

        verify(view, atLeast(1)).showChargers(captor.capture());

        //Verificar ordenamiento de la lista
        capturados = captor.getValue();
        assertEquals(capturados.get(0), c1);
        assertEquals(capturados.get(1), c2);
        assertEquals(capturados.get(2), c3);
        //Verificar tamanho de la lista
        assertEquals(capturados.size(), 3);
    }

    //CASO 3: Ordenar con 2 cargadores con misma ubicacion
    @Test
    public void onDistanceSortingClickedCasoTresTest() {
        //Creo puntos de carga y les asigno unas coordenadas
        Charger c1 = new Charger();
        c1.address.latitude = "52.343197";
        c1.address.longitude = "-0.170632";
        Charger c2 = new Charger();
        c2.address.latitude = "-30.6866";
        c2.address.longitude = "1.170632";
        Charger c3 = new Charger();
        c3.address.latitude = "52.343197";
        c3.address.longitude = "-0.170632";

        //Anhado los cargadores a la lista
        listaCargadores.add(c1);
        listaCargadores.add(c3);
        listaCargadores.add(c2);

        //Establezco el comportamiento del mock
        when(view.getRepository()).thenReturn(repository);
        sut.init(view);


        //Llamada al método testeado y verificar llamada
        sut.onDistanceSortingClicked();

        verify(view, atLeast(1)).showChargers(captor.capture());

        //Verificar ordenamiento de la lista (los que son iguales aparecen primero)
        capturados = captor.getValue();
        assertEquals(capturados.get(0), c1);
        assertEquals(capturados.get(1), c3);
        assertEquals(capturados.get(2), c2);
        //Verificar tamanho de la lista
        assertEquals(capturados.size(), 3);
    }

    //CASO 4: Ordenar con ubicacion no disponible
    //No es aplicable ya que con valores null no es posible invocar sortByDistance
    /*
    @Test
    public void onDistanceSortingClickedCasoCuatroTest() {
        //Creo puntos de carga y les asigno unas coordenadas
        Charger c1 = new Charger();
        c1.address.latitude = "52.343197";
        c1.address.longitude = "-0.170632";
        Charger c2 = new Charger();
        c2.address.latitude = "-29.6866";
        c2.address.longitude = "1.170632";
        Charger c3 = new Charger();
        c3.address.latitude = "-30.6866";
        c3.address.longitude = "1.170632";
        Charger c4 = new Charger();
        c4.address.latitude = null;
        c4.address.longitude = null;

        //Anhado los cargadores a la lista
        listaCargadores.add(c1);
        listaCargadores.add(c2);
        listaCargadores.add(c3);
        listaCargadores.add(c4);

        //Establezco el comportamiento del mock
        when(view.getRepository()).thenReturn(repository);
        sut.init(view);


        //Llamada al método testeado y verificar llamada
        sut.onDistanceSortingClicked();

        verify(view, atLeast(1)).showChargers(captor.capture());

        //Verificar ordenamiento de la lista
        capturados = captor.getValue();
        assertEquals(capturados.get(0), c1);
        assertEquals(capturados.get(1), c2);
        assertEquals(capturados.get(2), c3);
        assertEquals(capturados.get(3), c4);
        //Verificar tamanho de la lista
        assertEquals(capturados.size(), 4);
    }
    */
}
