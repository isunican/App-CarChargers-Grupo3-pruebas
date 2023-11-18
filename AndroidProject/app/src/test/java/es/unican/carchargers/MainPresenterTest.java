package es.unican.carchargers;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import static org.junit.Assert.assertEquals;
import es.unican.carchargers.activities.main.IMainContract;
import es.unican.carchargers.activities.main.MainPresenter;
import es.unican.carchargers.constants.EOperator;
import es.unican.carchargers.model.Charger;
import es.unican.carchargers.model.Operator;
import es.unican.carchargers.repository.IRepository;
import es.unican.carchargers.repository.Repositories;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;


public class MainPresenterTest {

    @Mock
    IMainContract.View view;
    @Mock
    IMainContract.Presenter sut;
    IRepository repositorio; //Para simular el repositorio de datos
    List<Charger> listaCargadores = new ArrayList<>();
    ArgumentCaptor<List<Charger>> captor; //Para captar los argumnetos pasados al mock
    List<Charger>capturados = new ArrayList<>();


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        repositorio = Repositories.getSyncFake(listaCargadores);//Inicializa repositorio falso
        captor = ArgumentCaptor.forClass((List.class));
        sut = new MainPresenter();

    }


    //CASO1: Filtrar por un operador con dos cargadores como resultado
    @Test
    public void testOnOperator1FilterClickedTest() {
        //Filtro por operador Tesla
        EOperator operator = EOperator.TESLA;

        //Operadores
        //Tesla
        Operator operador1 = new Operator();
        operador1.id = 3534;
        //Repsol
        Operator operador2 = new Operator();
        operador2.id = 91;
        //Iberdrola
        Operator operador3 = new Operator();
        operador3.id = 2247;

        //Creo los puntos de carga y le asigno un Operador
        Charger c1 = new Charger();
        c1.operator = operador1;//tesla
        Charger c2 = new Charger();
        c2.operator = operador2;
        Charger c3 = new Charger();
        c3.operator = operador3;
        Charger c4 = new Charger();
        c4.operator = operador1;//tesla

        //Anhado los caradores a la lista
        listaCargadores.add(c1);
        listaCargadores.add(c2);
        listaCargadores.add(c3);
        listaCargadores.add(c4);

        //Establezco comportamiento del mock
        when(view.getRepository()).thenReturn(repositorio);
        sut.init(view);

        //Llamada al metodo y se verifica que se ha llamado
        sut.onOperatorFilterClicked(operator);

        verify(view, atLeast(1)).showChargers(captor.capture());

        //Verifico que los elementos correctos son los dos cargadores tesla:1 y 4
        capturados = captor.getValue();
        assertEquals(capturados.get(0), c1);
        assertEquals(capturados.get(1), c4);

        //Verifico tamaho de la lista
        assertEquals(capturados.size(),2);

    }

    //CASO 2: Filtrar por un operador con un cargador como resultado
    @Test
    public void testOnOperator2FilterClickedTest() {
        //Filtro por operador Repsol
        EOperator operator = EOperator.REPSOL;

        //Operadores
        //Tesla
        Operator operador1 = new Operator();
        operador1.id = 3534;
        //Repsol
        Operator operador2 = new Operator();
        operador2.id = 91;
        //IBERDROLA
        Operator operador3 = new Operator();
        operador3.id = 2247;

        //Creo los puntos de carga y le asigno un Operador
        Charger c1 = new Charger();
        c1.operator = operador1;//tesla
        Charger c2 = new Charger();
        c2.operator = operador2;//Repsol
        Charger c3 = new Charger();
        c3.operator = operador3;
        Charger c4 = new Charger();
        c4.operator = operador1;//tesla

        //Anhado los caradores a la lista
        listaCargadores.add(c1);
        listaCargadores.add(c2);
        listaCargadores.add(c3);
        listaCargadores.add(c4);

        //Establezco comportamiento del mock
        when(view.getRepository()).thenReturn(repositorio);
        sut.init(view);

        //Llamada al metodo y se verifica que se ha llamado
        sut.onOperatorFilterClicked(operator);

        //verify(processor).setActiveOperator(operator);
        verify(view, atLeast(1)).showChargers(captor.capture());

        //Verifico que el elemento correcto es el cargador 2
        capturados = captor.getValue();
        assertEquals(capturados.get(0), c2);

        //Verifico tamaho de la lista
        assertEquals(capturados.size(), 1);

    }

    //CASO 3: Se filtra por un operador que ningun cargador tiene asociado
    @Test
    public void testOnOperator3FilterClickedTest() {
        //Filtro por operador Zunder
        EOperator operator = EOperator.ZUNDER;

        //Operadores
        //Tesla
        Operator operador1 = new Operator();
        operador1.id = 3534;
        //Repsol
        Operator operador2 = new Operator();
        operador2.id = 91;
        //Iberdrola
        Operator operador3 = new Operator();
        operador3.id = 2247;

        //Creo los puntos de carga y le asigno un Operador
        Charger c1 = new Charger();
        c1.operator = operador1;//tesla
        Charger c2 = new Charger();
        c2.operator = operador2;//Repsol
        Charger c3 = new Charger();
        c3.operator = operador3;
        Charger c4 = new Charger();
        c4.operator = operador1;//tesla

        //Establezco comportamiento del mock
        when(view.getRepository()).thenReturn(repositorio);
        sut.init(view);

        //Llamada al metodo y se verifica que se ha llamado
        sut.onOperatorFilterClicked(operator);

        verify(view, atLeast(1)).showChargers(captor.capture());

        capturados = captor.getValue();
        //Verifico tamaho de la lista
        assertEquals(capturados.size(), 0);

    }

    //CASO 4: Filtrar por un operador y que todos los cargadores tengan ese operador
    @Test
    public void testOnOperator4FilterClickedTest() {
        //Filtro por operador TIonity
        EOperator operator = EOperator.IONITY;

        //Operadores
        //Ionity
        Operator operador1 = new Operator();
        operador1.id = 3299;

        //Creo los puntos de carga y le asigno un Operador
        Charger c1 = new Charger();
        c1.operator = operador1;
        Charger c2 = new Charger();
        c2.operator = operador1;
        Charger c3 = new Charger();
        c3.operator = operador1;
        Charger c4 = new Charger();
        c4.operator = operador1;

        //Anhado los caradores a la lista
        listaCargadores.add(c1);
        listaCargadores.add(c2);
        listaCargadores.add(c3);
        listaCargadores.add(c4);

        //Establezco comportamiento del mock
        when(view.getRepository()).thenReturn(repositorio);
        sut.init(view);

        //Llamada al metodo y se verifica que se ha llamado
        sut.onOperatorFilterClicked(operator);

        verify(view, atLeast(1)).showChargers(captor.capture());

        //Verifico que se captan todos los cargadores
        capturados = captor.getValue();
        assertEquals(capturados.get(0), c1);
        assertEquals(capturados.get(1), c2);
        assertEquals(capturados.get(2), c3);
        assertEquals(capturados.get(3), c4);

        //Verifico tamaho de la lista
        assertEquals(capturados.size(), listaCargadores.size());

    }


}
