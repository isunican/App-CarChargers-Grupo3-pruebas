package es.unican.carchargers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import es.unican.carchargers.common.ChargersSorting;
import es.unican.carchargers.model.Charger;

public class ChargersSortingTest {
    ArrayList<Charger> cargadores = new ArrayList<>();

    //CASO 1:Ordena de menor precio a mayor
    @Test
    public void sortingByCostAscTest() {
        //Creo cargadores
        Charger cargador1 = new Charger();
        Charger cargador2 = new Charger();
        Charger cargador3 = new Charger();
        Charger cargador4 = new Charger();
        Charger cargador5 = new Charger();

        //Anhado los cargadores al array
        cargadores.add(cargador1);
        cargadores.add(cargador2);
        cargadores.add(cargador3);
        cargadores.add(cargador4);
        cargadores.add(cargador5);

        //Asigno precio a los cargadores
        cargador1.usageCost = "15€/kWh";
        cargador2.usageCost = "30€/kWh";
        cargador3.usageCost = "12€/kWh";
        cargador4.usageCost = "32€/kWh";
        cargador5.usageCost = "40€/kWh";

        //Obtengo la lista de los cargadores ordenados
        List<Charger> cargadoresOrdenados = ChargersSorting.sortByCost(cargadores);

        //Compruebo que estan ordenados de menor a mayor precio
        assertEquals(cargadoresOrdenados.get(0), cargador3);
        assertEquals(cargadoresOrdenados.get(1), cargador1);
        assertEquals(cargadoresOrdenados.get(2), cargador2);
        assertEquals(cargadoresOrdenados.get(3), cargador4);
        assertEquals(cargadoresOrdenados.get(4), cargador5);
    }

    //CASO 2: Ordena de menor a mayor dejando en ultima posicion el que no tiene precio.
    @Test
    public void sortingByCostNullTest() {
        //Creo cargadores
        Charger cargador1 = new Charger();
        Charger cargador2 = new Charger();
        Charger cargador3 = new Charger();
        Charger cargador4 = new Charger();
        Charger cargador5 = new Charger();

        //Anhado los cargadores al array
        cargadores.add(cargador1);
        cargadores.add(cargador2);
        cargadores.add(cargador3);
        cargadores.add(cargador4);
        cargadores.add(cargador5);

        //Asigno precio a los cargadores
        cargador1.usageCost = "15€/kWh";
        cargador2.usageCost = "null";
        cargador3.usageCost = "12€/kWh";
        cargador4.usageCost = "32€/kWh";
        cargador5.usageCost = "40€/kWh";

        //Obtengo la lista de los cargadores ordenados
        List<Charger> cargadoresOrdenados = ChargersSorting.sortByCost(cargadores);

        //Compruebo que estan ordenados de menor a mayor precio y null esta el ultimo
        assertEquals(cargadoresOrdenados.get(0), cargador3);
        assertEquals(cargadoresOrdenados.get(1), cargador1);
        assertEquals(cargadoresOrdenados.get(2), cargador4);
        assertEquals(cargadoresOrdenados.get(3), cargador5);
        assertEquals(cargadoresOrdenados.get(4), cargador2);
    }

    //CASO 3: Ordena de menor a mayor y en caso de que los precios sean iguales los ordena segun el orden de cargadores
    @Test
    public void sortingByCostIgualesTest() {
        //Creo cargadores
        Charger cargador1 = new Charger();
        Charger cargador2 = new Charger();
        Charger cargador3 = new Charger();
        Charger cargador4 = new Charger();
        Charger cargador5 = new Charger();

        //Anhado los cargadores al array
        cargadores.add(cargador1);
        cargadores.add(cargador2);
        cargadores.add(cargador3);
        cargadores.add(cargador4);
        cargadores.add(cargador5);

        //Asigno precio a los cargadores
        cargador1.usageCost = "15€/kWh";
        cargador2.usageCost = "10€/kWh";
        cargador3.usageCost = "15€/kWh";
        cargador4.usageCost = "40€/kWh";
        cargador5.usageCost = "15€/kWh";

        //Obtengo la lista de los cargadores ordenados
        List<Charger>cargadoresOrdenados = ChargersSorting.sortByCost(cargadores);

        //Compruebo que estan ordenados
        assertEquals(cargadoresOrdenados.get(0), cargador2);
        assertEquals(cargadoresOrdenados.get(1), cargador1);
        assertEquals(cargadoresOrdenados.get(2), cargador3);
        assertEquals(cargadoresOrdenados.get(3), cargador5);
        assertEquals(cargadoresOrdenados.get(4), cargador4);
    }

    //CASO3: Ordena los caradores de menor a mayor precio con distinto valores respecto al primer test
    @Test
    public void sortingByCostDescTest() {
        //Creo cargadores
        Charger cargador1 = new Charger();
        Charger cargador2 = new Charger();
        Charger cargador3 = new Charger();
        Charger cargador4 = new Charger();
        Charger cargador5 = new Charger();

        //Anhado los cargadores al array
        cargadores.add(cargador1);
        cargadores.add(cargador2);
        cargadores.add(cargador3);
        cargadores.add(cargador4);
        cargadores.add(cargador5);

        //Asigno precio a los cargadores
        cargador1.usageCost = "30€/kWh";
        cargador2.usageCost = "15€/kWh";
        cargador3.usageCost = "12€/kWh";
        cargador4.usageCost = "32€/kWh";
        cargador5.usageCost = "40€/kWh";

        //Obtengo la lista de los cargadores ordenados
        List<Charger> cargadoresOrdenados = ChargersSorting.sortByCost(cargadores);

        //Compruebo que estan ordenados de menor a mayor
        assertEquals(cargadoresOrdenados.get(0), cargador3);
        assertEquals(cargadoresOrdenados.get(1), cargador2);
        assertEquals(cargadoresOrdenados.get(2), cargador1);
        assertEquals(cargadoresOrdenados.get(3), cargador4);
        assertEquals(cargadoresOrdenados.get(4), cargador5);
    }

}
