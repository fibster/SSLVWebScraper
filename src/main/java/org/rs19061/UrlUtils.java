package org.rs19061;

import java.util.Map;
import java.util.HashMap;

public class UrlUtils {
    private static final Map<Character, String> characterReplacements;

    static {
        // Mapping of Latvian characters to their replacements
        characterReplacements = new HashMap<>();
        characterReplacements.put('ā', "a");
        characterReplacements.put('č', "c");
        characterReplacements.put('ģ', "g");
        characterReplacements.put('ķ', "k");
        characterReplacements.put('ļ', "l");
        characterReplacements.put('ņ', "n");
        characterReplacements.put('š', "s");
        characterReplacements.put('ū', "u");
        characterReplacements.put('ž', "z");
        characterReplacements.put('ē', "e");
        characterReplacements.put('ī', "i");
    }

    public static String constructCityUrl(Property property, String city) {
        String propertyType = property.getPropertyType();
        String selectedCity = city.trim().toLowerCase();
        String cityUrl = "https://www.ss.lv/lv/real-estate/";
        String replacedCity = replaceLatvianCharacters(selectedCity);

        // Constructing the city-specific URL based on the selected city
        if (selectedCity.equals("rīga")) {
            cityUrl += propertyType + "/" + replacedCity.replace(" ", "-") + "/all/";
        } else if(selectedCity.equals("rīgas rajons")){
            cityUrl += propertyType + "/riga-region/all/";
        } else if(replacedCity.endsWith("un rajons")) {
            replacedCity = replacedCity.replace(" un rajons", "").replace(" ", "-");
            cityUrl += propertyType + "/" + replacedCity + "-and-reg/all/";
        } else {
            cityUrl += propertyType + "/" + replacedCity.replace(" ", "-") + "/";
        }

        return cityUrl;
    }

    private static String replaceLatvianCharacters(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (characterReplacements.containsKey(c)) {
                sb.append(characterReplacements.get(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}