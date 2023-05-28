package org.rs19061;

import java.util.List;

public class Flat implements Property {
    @Override
    public String getPropertyType() {
        return "flats";
    }

    @Override
    public String getUrl() {
        return "https://www.ss.lv/lv/real-estate/flats/";
    }

    @Override
    public List<String> fetchCities() {
        return Fetcher.fetchCities(this.getUrl());
    }


    @Override
    public String constructCityUrl(String city) {
        return Fetcher.constructCityUrl(this, city);
    }
}