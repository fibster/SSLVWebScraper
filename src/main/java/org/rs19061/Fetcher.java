package org.rs19061;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Fetcher {

    private static final Map<Character, String> characterReplacements;

    static {
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

    public static void main(String[] args) {
        int selectedOption = fetchOption();
        String url = constructUrl(selectedOption);

        System.out.println("Selected Option: " + selectedOption);
        System.out.println("URL: " + url);
        // Use the selectedOption and url for further processing
        // ...
    }

    public static int fetchOption() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Flats");
        System.out.println("2. Houses");
        System.out.print("Enter the option number: ");
        int selectedOption = scanner.nextInt();

        if (selectedOption != 1 && selectedOption != 2) {
            System.out.println("Invalid option selected.");
            return -1; // Or throw an exception if needed
        }

        return selectedOption;
    }

    public static String constructUrl(int selectedOption) {
        Property property;
        if (selectedOption == 1) {
            property = new Flat();
        } else if (selectedOption == 2) {
            property = new House();
        } else {
            System.out.println("Invalid option selected.");
            return null; // Or throw an exception if needed
        }

        List<String> cities = property.fetchCities();
        System.out.println("\n" + property.getPropertyType() + " - Cities:");
        displayCities(cities);

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the option number to choose a city: ");
        int selectedCityOption = scanner.nextInt();

        String selectedCity = getSelectedCity(cities, selectedCityOption);

        if (!selectedCity.isEmpty()) {
            return property.constructCityUrl(selectedCity);
        } else {
            System.out.println("Invalid option selected.");
            return null; // Or throw an exception if needed
        }
    }

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

    public static String getSelectedCity(List<String> cities, int selectedOption) {
        if (selectedOption >= 1 && selectedOption <= cities.size()) {
            return cities.get(selectedOption - 1);
        } else {
            return "";
        }
    }

    private static void displayCities(List<String> cities) {
        int option = 1;
        for (String city : cities) {
            System.out.println(option + ". " + city);
            option++;
        }
    }
    public static String constructCityUrl(Property property, String city) {
        String propertyType = property.getPropertyType();
        String selectedCity = city.trim().toLowerCase();
        String cityUrl = "https://www.ss.lv/lv/real-estate/";
        String replacedCity = replaceLatvianCharacters(selectedCity);

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