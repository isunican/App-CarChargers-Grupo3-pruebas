package es.unican.carchargers;

import static org.junit.Assert.assertEquals;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import es.unican.carchargers.common.ChargersSorting;
import es.unican.carchargers.model.Address;
import es.unican.carchargers.model.Charger;
@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.R}) //O_MR1})
public class ChargersSortingTest {
    ArrayList<Charger> cargadores = new ArrayList<>();
    @Test
    public void  ordenadoConYaOrdenadastest() {
        Charger c1 = new Charger();
        Charger c2 = new Charger();
        Charger c3 = new Charger();
        Address a1 = new Address("52.343197","-0.170632");
        Address a2 = new Address("-29.6866", "1.170632");
        Address a3 = new Address("-30.6866", "1.170632");
        c1.setAddress(a1);
        c2.setAddress(a2);
        c3.setAddress(a3);
        cargadores.add(c1);
        cargadores.add(c2);
        cargadores.add(c3);
        List<Charger> ordenados = ChargersSorting.sortByDistance(cargadores,29.6866, - 123.3046);

        assertEquals(ordenados.get(0), c1);
        assertEquals(ordenados.get(1), c2);
        assertEquals(ordenados.get(2), c3);

    }
    @Test
    public void  ordenadoConDesordenadastest() {
        Charger c1 = new Charger();
        Charger c2 = new Charger();
        Charger c3 = new Charger();
        Address a1 = new Address("52.343197","-0.170632");
        Address a2 = new Address("53.343197", "1.170632");
        Address a3 = new Address("-30.6866", "1.170632");
        c1.setAddress(a1);
        c2.setAddress(a2);
        c3.setAddress(a3);
        cargadores.add(c1);
        cargadores.add(c2);
        cargadores.add(c3);
        List<Charger> ordenados = ChargersSorting.sortByDistance(cargadores,29.6866, - 123.3046);

        assertEquals(ordenados.get(0), c2);
        assertEquals(ordenados.get(1), c1);
        assertEquals(ordenados.get(2), c3);

    }
    @Test
    public void  ordenadoConIgualestest() {
        Charger c1 = new Charger();
        Charger c2 = new Charger();
        Charger c3 = new Charger();
        Address a1 = new Address("52.343197", "-0.170632");
        Address a2 = new Address("-30.6866", "1.170632");
        Address a3 = new Address("52.343197", "-0.170632");
        c1.setAddress(a1);
        c2.setAddress(a2);
        c3.setAddress(a3);
        cargadores.add(c1);
        cargadores.add(c2);
        cargadores.add(c3);
        List<Charger> ordenados = ChargersSorting.sortByDistance(cargadores, 29.6866, -123.3046);

        assertEquals(ordenados.get(0), c1);
        assertEquals(ordenados.get(1), c3);
        assertEquals(ordenados.get(2), c2);
    }
    //los casos con null no deja invocar al sortByDistance

}
