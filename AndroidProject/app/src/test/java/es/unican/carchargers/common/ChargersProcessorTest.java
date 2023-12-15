package es.unican.carchargers.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
            //Introducir ruta de pc propio
            FileInputStream fis = new FileInputStream("C:\\Users\\Yago Nava\\Desktop\\ProIntPruebas\\App-CarChargers-Grupo3-pruebas\\AndroidProject\\app\\src\\main\\res\\raw\\chargers_es_apply.json");
            chargers = Utils.fakeSuccess(fis);
        } catch (Exception e) {}
    }


    @Test
    public void applyVacioTest() {
        //Test de prueba de llamada al metodo
        procesador = new ChargersProcessor();
        List<Charger> result = procesador.apply(chargers);
        assertEquals(chargers, result);
    }

    @Test
    public void applyCasoUnoTest() {
        //Establezco un formato de fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        //Se genera una fecha apta para el test y otra que no sirva
        LocalDateTime fechaCorrecta = LocalDateTime.now().minus(3,ChronoUnit.MONTHS);
        String stringFechaCorrecta = fechaCorrecta.format(formatter);
        LocalDateTime fechaIncorrecta = LocalDateTime.now().minus(50,ChronoUnit.MONTHS);
        String stringFechaIncorrecta = fechaIncorrecta.format(formatter);
        //Genera cargadores con fechas aptas para que esta prueba se pueda ejecutar siempre
        List<Charger> cargadoresFechas = new ArrayList<>();
        Charger c1 = new Charger();
        c1.operator.id = 2247; //IBERDROLA
        c1.operator.title = "Iberdrola";
        c1.dateLastVerified = stringFechaCorrecta;
        Charger c2 = new Charger();
        c2.operator.id = 3371; //WENEA
        c2.operator.title = "Wenea";
        c2.dateLastVerified = stringFechaCorrecta;
        Charger c3 = new Charger();
        c3.operator.id = 3371; //WENEA
        c3.operator.title = "Wenea";
        c3.dateLastVerified = stringFechaIncorrecta;

        //Anhado los cargadores a la lista
        cargadoresFechas.add(c1);
        cargadoresFechas.add(c2);
        cargadoresFechas.add(c3);

        //Necesario comprobar fecha y companhia
        procesador = new ChargersProcessor();
        procesador.setIgnoreOutdated(true);
        procesador.setActiveOperator(EOperator.IBERDROLA);
        procesador.setSorting(null);

        List<Charger> result = procesador.apply(cargadoresFechas);

        //Comprobar numero de elementos
        assertEquals(1, result.size());
        //Comprobar companhia y fecha
        for(int i = 0; i < result.size(); i++) {
            //Compruebo que la companhia sea correcta
            assertEquals("Iberdrola", result.get(i).operator.toString());
            //Compruebo que el cargador este actualizado
            LocalDateTime fechaLastVerified = LocalDateTime.parse(result.get(i).dateLastVerified, formatter);
            LocalDateTime sixMonthsAgo = LocalDateTime.now().minus(6, ChronoUnit.MONTHS);
            assertTrue(sixMonthsAgo.isBefore(fechaLastVerified));
        }
    }

    @Test
    public void applyCasoDosTest() {
        //Necesario comprobar ordenamiento y companhia
        procesador = new ChargersProcessor();
        procesador.setIgnoreOutdated(false);
        procesador.setActiveOperator(EOperator.ZUNDER);
        procesador.setSorting(ESorting.POWER);

        List<Charger> result = procesador.apply(chargers);

        //Comprobar numero de elementos
        assertEquals(3, result.size());
        //Comprobar ordenamiento: primero, intermedio y último
        assertEquals("213052", result.get(0).id);
        assertEquals("204700", result.get(1).id);
        assertEquals("188299", result.get(2).id);
        //Comprobar companhia y fecha
        for(int i = 0; i < result.size(); i++) {
            //Compruebo que la companhia sea correcta
            assertEquals("Zunder", result.get(i).operator.toString());
        }
    }

    @Test
    public void applyCasoTresTest() {
        //Necesario comprobar ordenamiento y companhia
        procesador = new ChargersProcessor();
        procesador.setIgnoreOutdated(false);
        procesador.setActiveOperator(EOperator.REPSOL);
        procesador.setSorting(ESorting.COST);

        List<Charger> result = procesador.apply(chargers);

        //Comprobar numero de elementos
        assertEquals(3, result.size());
        //Comprobar ordenamiento: primero, intermedio y último
        assertEquals("209443", result.get(0).id);
        assertEquals("201410", result.get(1).id);
        assertEquals("274508", result.get(2).id);
        //Comprobar companhia y fecha
        for(int i = 0; i < result.size(); i++) {
            //Compruebo que la companhia sea correcta
            assertEquals("Repsol - Ibil (ES)", result.get(i).operator.toString());
        }
    }

    @Test
    public void applyCasoCuatroTest() {
        //Necesario comprobar ordenamiento y companhia
        procesador = new ChargersProcessor();
        procesador.setIgnoreOutdated(false);
        procesador.setActiveOperator(EOperator.WENEA);
        procesador.setSorting(ESorting.DISTANCE);
        procesador.setLocation(43.46472, -3.80444);

        List<Charger> result = procesador.apply(chargers);

        //Comprobar numero de elementos
        assertEquals(3, result.size());
        //Comprobar ordenamiento: primero, intermedio y último
        assertEquals("203417", result.get(0).id);
        assertEquals("201994", result.get(1).id);
        assertEquals("212956", result.get(2).id);
        //Comprobar companhia y fecha
        for(int i = 0; i < result.size(); i++) {
            //Compruebo que la companhia sea correcta
            assertEquals("Wenea", result.get(i).operator.toString());
        }
    }

}
