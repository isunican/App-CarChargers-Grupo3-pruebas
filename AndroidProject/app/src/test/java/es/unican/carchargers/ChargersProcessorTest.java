package es.unican.carchargers;

import static org.junit.Assert.assertEquals;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import es.unican.carchargers.common.ChargersProcessor;
import es.unican.carchargers.common.Utils;
import es.unican.carchargers.constants.EOperator;
import es.unican.carchargers.constants.ESorting;
import es.unican.carchargers.model.Charger;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class ChargersProcessorTest {
    //Aqui se realizan las pruebas para el metodo ChargersProcessor.Apply sin mocks
    private ChargersProcessor procesador;
    private List<Charger> chargers = new ArrayList<>();

    @Before
    public void before() {
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\Yago Nava\\Desktop\\ProIntPruebas\\App-CarChargers-Grupo3-pruebas\\AndroidProject\\app\\src\\main\\res\\raw\\chargers_es_apply.json");
            chargers = Utils.fakeSuccess(fis);
        } catch (Exception e) {}
    }


    @Test
    public void applyVacioTest() {
        procesador = new ChargersProcessor();
        List<Charger> result = procesador.apply(chargers);
        assertEquals(chargers, result);
    }

    @Test
    public void applyCasoUnoTest() {
        procesador = new ChargersProcessor();
        procesador.setIgnoreOutdated(true);
        procesador.setActiveOperator(EOperator.IBERDROLA);
        procesador.setSorting(null);

        List<Charger> result = procesador.apply(chargers);

        //Comprobar numero de elementos
        assertEquals(1, result.size());
        //Comprobar companhia y fecha
        for(int i = 0; i < result.size(); i++) {
            assertEquals("Iberdrola", result.get(i).operator.toString());
            //assertTrue(LocalDateTime.now().minus(6, ChronoUnit.MONTHS).isBefore(result.get(i).dateLastVerified));
        }
    }

    @Test
    public void applyCasoDosTest() {
        procesador = new ChargersProcessor();
        procesador.setIgnoreOutdated(false);
        procesador.setActiveOperator(EOperator.ZUNDER);
        procesador.setSorting(ESorting.POWER);

        List<Charger> result = procesador.apply(chargers);

        //Comprobar numero de elementos
        assertEquals(3, result.size());
        //Comprobar el primero, intermedio y último
        assertEquals("213052", result.get(0).id);
        assertEquals("204700", result.get(1).id);
        assertEquals("188299", result.get(2).id);
        //Comprobar companhia y fecha
        for(int i = 0; i < result.size(); i++) {
            assertEquals("Zunder", result.get(i).operator.toString());
        }
    }

    @Test
    public void applyCasoTresTest() {
        procesador = new ChargersProcessor();
        procesador.setIgnoreOutdated(false);
        procesador.setActiveOperator(EOperator.REPSOL);
        procesador.setSorting(ESorting.COST);

        List<Charger> result = procesador.apply(chargers);

        //Comprobar numero de elementos
        assertEquals(3, result.size());

        //Comprobar el primero, intermedio y último
        assertEquals("209443", result.get(0).id);
        assertEquals("201410", result.get(1).id);
        assertEquals("274508", result.get(2).id);
        //Comprobar companhia y fecha
        for(int i = 0; i < result.size(); i++) {
            assertEquals("Repsol - Ibil (ES)", result.get(i).operator.toString());
        }
    }

    @Test
    public void applyCasoCuatroTest() {
        procesador = new ChargersProcessor();
        procesador.setIgnoreOutdated(false);
        procesador.setActiveOperator(EOperator.WENEA);
        procesador.setSorting(ESorting.DISTANCE);
        procesador.setLocation(43.46472, -3.80444);

        List<Charger> result = procesador.apply(chargers);

        //Comprobar numero de elementos
        assertEquals(3, result.size());
        //Comprobar el primero, intermedio y último
        assertEquals("203417", result.get(0).id);
        assertEquals("201994", result.get(1).id);
        assertEquals("212956", result.get(2).id);
        //Comprobar companhia y fecha
        for(int i = 0; i < result.size(); i++) {
            assertEquals("Wenea", result.get(i).operator.toString());
        }
    }

}
