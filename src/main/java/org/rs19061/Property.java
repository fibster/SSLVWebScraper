package org.rs19061;

import java.util.List;

public interface Property {
    String getPropertyType();

    List<String> fetchCities();

    String constructCityUrl(String city);

    String getUrl();
}