package org.rs19061;

import java.util.List;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class House implements Property {
    @Override
    public String getPropertyType() {
        return "homes-summer-residences";
    }

    @Override
    public String getUrl() {
        return "https://www.ss.lv/lv/real-estate/homes-summer-residences/";
    }

    @Override
    public List<String> fetchCities() {
        return Fetcher.fetchCities(this.getUrl());
    }

    @Override
    public String constructCityUrl(String city) {
        return UrlUtils.constructCityUrl(this, city);
    }
}