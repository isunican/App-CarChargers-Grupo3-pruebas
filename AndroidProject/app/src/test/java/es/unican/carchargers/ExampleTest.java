package es.unican.carchargers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import es.unican.carchargers.activities.main.IMainContract;
import es.unican.carchargers.activities.main.MainPresenter;
import es.unican.carchargers.model.Charger;
import es.unican.carchargers.repository.IRepository;
import es.unican.carchargers.repository.Repositories;

public class ExampleTest {

    @Mock private IMainContract.View view;
    private IMainContract.Presenter sut;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
        sut = new MainPresenter();
    }

    @Test
    public void test() {
        List<Charger> chargers = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Charger charger = new Charger();
            charger.id = String.valueOf(i);
            chargers.add(charger);
        }

        IRepository repository = Repositories.getSyncFake(chargers);
        when(view.getRepository()).thenReturn(repository);
        sut.init(view);


        verify(view, times(1)).showChargers(anyList());

        ArgumentCaptor<List<Charger>> captor = ArgumentCaptor.forClass(List.class);
        verify(view).showChargers(captor.capture());

        List<Charger> values = captor.getValue();
        for (int i=0; i<10; i++) {
            assertEquals(String.valueOf(i), values.get(i).id);
        }

    }
}