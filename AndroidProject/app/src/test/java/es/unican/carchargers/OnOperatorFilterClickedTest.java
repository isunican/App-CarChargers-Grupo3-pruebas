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
import es.unican.carchargers.common.ChargersProcessor;
import es.unican.carchargers.constants.EOperator;
import es.unican.carchargers.model.Charger;
import es.unican.carchargers.model.Operator;
import es.unican.carchargers.repository.IRepository;
import es.unican.carchargers.repository.Repositories;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class OnOperatorFilterClickedTest {

    @Mock
    IMainContract.View view;
    @Mock
    ChargersProcessor processor;
    IMainContract.Presenter sut;

    IRepository repositorio; //Para simular el repositorio de datos

    EOperator[] operadores;
    List<Charger> listaCargadores = new ArrayList<>();
    ArgumentCaptor<List<Charger>> captor; //Para captar los argumnetos pasados al mock
    List<Charger>capturados = new ArrayList<>();



    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        repositorio = Repositories.getSyncFake(listaCargadores);//Inicializa repositorio falso
        captor = ArgumentCaptor.forClass((List.class));
        sut = new MainPresenter();
        processor = Mockito.mock(ChargersProcessor.class); // Crear un mock de ChargersProcessor
        //processor = sut.getProcessor();


    }
    //Filtrar por un operador
    @Test
    public void testOnOperator1FilterClickedTest() {
        //Filtro por operador Tesla
        EOperator operator = EOperator.TESLA;
        //Anhado el operador a la lista
       //operadores.add(operator.id);

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
        //when(processor.setActiveOperator(operator)).;
        sut.init(view);

        //Llamada al metodo y se verifica que se ha llamado
        sut.onOperatorFilterClicked(operator);

        //verify(processor).setActiveOperator(operator);
        verify(view, atLeast(1)).showChargers(captor.capture());

        //verify(view).updateChargers(listaCargadores);

        //Verifico que los elementos correctos son los dos cargadores tesla:1 y 4
        capturados = captor.getValue();
        assertEquals(capturados.get(0), c1);
        assertEquals(capturados.get(1), c4);

        //Verifico tamaho de la lista
        assertEquals(capturados.size(),2);

    }

    //CASO 2: Filtrado por dos operadores
   /* public void testOnOperator2FilterClickedTest() {
        //Filtro por operador Tesla y Repsol
        EOperator operator1 = EOperator.TESLA;
        EOperator operator2 = EOperator.REPSOL;
        //Anhado el operador a la lista
        operadores.add(operator1.id);
        operadores.add(operator2.id);

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
        //when(processor.setActiveOperator(operator)).;
        sut.init(view);

        //Llamada al metodo y se verifica que se ha llamado
        sut.onOperatorFilterClicked(operadores);

        //verify(processor).setActiveOperator(operator);
        verify(view, atLeast(1)).showChargers(captor.capture());

        //verify(view).updateChargers(listaCargadores);

        //Verifico que los elementos correctos son los dos cargadores tesla:1 y 4
        capturados = captor.getValue();
        assertEquals(capturados.get(0), c1);
        assertEquals(capturados.get(1), c4);
        assertEquals(capturados.get(2), c2);

        //Verifico tamaho de la lista
        assertEquals(capturados.size(),2);

    */

    
}
