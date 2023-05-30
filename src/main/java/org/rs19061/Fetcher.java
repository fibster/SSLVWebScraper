package org.rs19061;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Fetcher {

    public static List<String> fetchCities(String url) {
        List<String> cities = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements h4Elements = doc.select("h4.category");

            for (Element h4 : h4Elements) {
                String city = h4.select("a").text();
                if (!city.equals("Dzīvokļi ārpus Latvijas") && !city.equals("Mājas ārpus Latvijas")) {
                    cities.add(city);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }
}