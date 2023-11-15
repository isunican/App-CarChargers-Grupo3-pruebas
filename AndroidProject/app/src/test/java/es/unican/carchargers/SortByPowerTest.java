package es.unican.carchargers;

import static org.junit.Assert.assertEquals;

import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import es.unican.carchargers.common.ChargersSorting;
import es.unican.carchargers.model.Charger;
import es.unican.carchargers.model.Connection;


public class SortByPowerTest {
    Charger c1,c2,c3,c4,c5;
    ChargersSorting.PowerComparator comparator;
    @Before
    public void ini() {

        c1 = new Charger();
        c2 = new Charger();
        c3 = new Charger();
        c4 = new Charger();
        c5 = new Charger();

        c1.connections = new ArrayList<>();
        c2.connections = new ArrayList<>();
        c3.connections = new ArrayList<>();
        c4.connections = new ArrayList<>();
        c5.connections = new ArrayList<>();

        comparator = new ChargersSorting.PowerComparator();


    }
    @Test
    public void testSortByPower1() {

        Connection connect1 = new Connection();
        connect1.powerKw = String.valueOf(50);
        c1.connections.add(connect1);

        Connection connect2 = new Connection();
        connect2.powerKw = String.valueOf(40);
        c2.connections.add(connect2);

        Connection connect3 = new Connection();
        connect3.powerKw = String.valueOf(60);
        c3.connections.add(connect3);

        Connection connect4 = new Connection();
        connect4.powerKw = String.valueOf(30);
        c4.connections.add(connect4);

        Connection connect5 = new Connection();
        connect5.powerKw = String.valueOf(45);
        c5.connections.add(connect5);


        List<Charger> chargers = new ArrayList<>();
        chargers.add(c1);
        chargers.add(c2);
        chargers.add(c3);
        chargers.add(c4);
        chargers.add(c5);

        //Compruebo que los cargadores tienen sus valores
        assertEquals(50, ChargersSorting.getChargerPower(c1),0.01);
        assertEquals(40, ChargersSorting.getChargerPower(c2),0.01);
        assertEquals(60, ChargersSorting.getChargerPower(c3),0.01);
        assertEquals(30, ChargersSorting.getChargerPower(c4),0.01);
        assertEquals(45, ChargersSorting.getChargerPower(c5),0.01);

        //Ordeno por potencia
        chargers.sort(comparator);

        //Compruebo que la lista esté ordenada según el comparador
        assertEquals(c3, chargers.get(0));
        assertEquals(c1, chargers.get(1));
        assertEquals(c5, chargers.get(2));
        assertEquals(c2, chargers.get(3));
        assertEquals(c4, chargers.get(4));


    }
    @Test
    public void testSortByPower2() {

        Connection connect1 = new Connection();
        connect1.powerKw = null;
        c1.connections.add(connect1);

        Connection connect2 = new Connection();
        connect2.powerKw = String.valueOf(40);
        c2.connections.add(connect2);

        Connection connect3 = new Connection();
        connect3.powerKw = String.valueOf(60);
        c3.connections.add(connect3);

        Connection connect4 = new Connection();
        connect4.powerKw = String.valueOf(30);
        c4.connections.add(connect4);

        Connection connect5 = new Connection();
        connect5.powerKw = String.valueOf(45);
        c5.connections.add(connect5);


        List<Charger> chargers = new ArrayList<>();
        chargers.add(c1);
        chargers.add(c2);
        chargers.add(c3);
        chargers.add(c4);
        chargers.add(c5);

        //Compruebo que los cargadores tienen sus valores
        assertEquals(0, ChargersSorting.getChargerPower(c1),0.01);
        assertEquals(40, ChargersSorting.getChargerPower(c2),0.01);
        assertEquals(60, ChargersSorting.getChargerPower(c3),0.01);
        assertEquals(30, ChargersSorting.getChargerPower(c4),0.01);
        assertEquals(45, ChargersSorting.getChargerPower(c5),0.01);

        //Ordeno por potencia
        chargers.sort(comparator);

        //Compruebo que la lista esté ordenada según el comparador
        assertEquals(c3, chargers.get(0));
        assertEquals(c5, chargers.get(1));
        assertEquals(c2, chargers.get(2));
        assertEquals(c4, chargers.get(3));
        assertEquals(c1, chargers.get(4));


    }

    @Test
    public void testSortByPower3() {

        Connection connect1 = new Connection();
        connect1.powerKw = String.valueOf(20);
        c1.connections.add(connect1);

        Connection connect2 = new Connection();
        connect2.powerKw = String.valueOf(20);
        c2.connections.add(connect2);

        Connection connect3 = new Connection();
        connect3.powerKw = String.valueOf(20);
        c3.connections.add(connect3);

        Connection connect4 = new Connection();
        connect4.powerKw = String.valueOf(20);
        c4.connections.add(connect4);

        Connection connect5 = new Connection();
        connect5.powerKw = String.valueOf(20);
        c5.connections.add(connect5);


        List<Charger> chargers = new ArrayList<>();
        chargers.add(c1);
        chargers.add(c2);
        chargers.add(c3);
        chargers.add(c4);
        chargers.add(c5);

        //Compruebo que los cargadores tienen sus valores
        assertEquals(20, ChargersSorting.getChargerPower(c1),0.01);
        assertEquals(20, ChargersSorting.getChargerPower(c2),0.01);
        assertEquals(20, ChargersSorting.getChargerPower(c3),0.01);
        assertEquals(20, ChargersSorting.getChargerPower(c4),0.01);
        assertEquals(20, ChargersSorting.getChargerPower(c5),0.01);

        //Ordeno por potencia
        chargers.sort(comparator);

        //Compruebo que la lista esté ordenada según el comparador
        assertEquals(c1, chargers.get(0));
        assertEquals(c2, chargers.get(1));
        assertEquals(c3, chargers.get(2));
        assertEquals(c4, chargers.get(3));
        assertEquals(c5, chargers.get(4));



    }
    @Test
    public void testSortByPower4() {

        Connection connect1 = new Connection();
        connect1.powerKw = String.valueOf(15);
        c1.connections.add(connect1);

        Connection connect2 = new Connection();
        connect2.powerKw = String.valueOf(25);
        c2.connections.add(connect2);

        Connection connect3 = new Connection();
        connect3.powerKw = String.valueOf(10);
        c3.connections.add(connect3);

        Connection connect4 = new Connection();
        connect4.powerKw = String.valueOf(30);
        c4.connections.add(connect4);

        Connection connect5 = new Connection();
        connect5.powerKw = String.valueOf(20);
        c5.connections.add(connect5);


        List<Charger> chargers = new ArrayList<>();
        chargers.add(c1);
        chargers.add(c2);
        chargers.add(c3);
        chargers.add(c4);
        chargers.add(c5);

        //Compruebo que los cargadores tienen sus valores
        assertEquals(15, ChargersSorting.getChargerPower(c1),0.01);
        assertEquals(25, ChargersSorting.getChargerPower(c2),0.01);
        assertEquals(10, ChargersSorting.getChargerPower(c3),0.01);
        assertEquals(30, ChargersSorting.getChargerPower(c4),0.01);
        assertEquals(20, ChargersSorting.getChargerPower(c5),0.01);

        //Ordeno por potencia
        chargers.sort(comparator);

        //Compruebo que la lista esté ordenada según el comparador
        assertEquals(c4, chargers.get(0));
        assertEquals(c2, chargers.get(1));
        assertEquals(c5, chargers.get(2));
        assertEquals(c1, chargers.get(3));
        assertEquals(c3, chargers.get(4));



    }
}
