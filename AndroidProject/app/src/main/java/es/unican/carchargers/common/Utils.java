package es.unican.carchargers.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import es.unican.carchargers.model.Charger;
import es.unican.carchargers.repository.service.IOpenChargeMapAPI;

public class Utils {

    /**
     * Converts the given input stream to a String
     * @param is
     * @return
     */
    public static String toString(InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }

            String fileContent = out.toString();
            reader.close();
            return fileContent;

        } catch (IOException e) {}
        return null;
    }

    public static List<Charger> fakeSuccess(InputStream is) {
        Type typeToken = new TypeToken<List<Charger>>() { }.getType();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        List<Charger> chargers = new Gson().fromJson(reader, typeToken);
        return chargers;
    }

}
