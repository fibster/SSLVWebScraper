package org.rs19061;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class Main {
    private static JComboBox<String> propertyTypeCombo;
    private static JTextField urlField, pagesField;
    private static JButton fetchButton;
    private static JComboBox<String> cityCombo;
    private static Property property;
    private static JTextArea resultArea;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Data scraper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        propertyTypeCombo = new JComboBox<>(new String[]{"Flat", "House"});
        cityCombo = new JComboBox<>();
        urlField = new JTextField(30);
        pagesField = new JTextField(5);
        fetchButton = new JButton("Fetch and Write");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        panel.add(new JLabel("Īpašuma veids:"));
        panel.add(propertyTypeCombo);
        panel.add(new JLabel("Pilsēta:"));
        panel.add(cityCombo);
        panel.add(new JLabel("URL:"));
        panel.add(urlField);
        panel.add(new JLabel("Lapas:"));
        panel.add(pagesField);
        panel.add(fetchButton);
        panel.add(scrollPane);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        propertyTypeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (propertyTypeCombo.getSelectedIndex() == 0) {
                    property = new Flat();
                } else {
                    property = new House();
                }
                List<String> cities = property.fetchCities();
                cityCombo.removeAllItems();
                for(String city : cities){
                    cityCombo.addItem(city);
                }
            }
        });

        cityCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = (String) cityCombo.getSelectedItem();
                String url = property.constructCityUrl(city);
                urlField.setText(url);
            }
        });

        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PropertyFetcher propertyFetcher;
                String fileName;

                if (propertyTypeCombo.getSelectedIndex() == 1) {
                    propertyFetcher = new HouseFetcher();
                    fileName = "houses.csv";
                } else {
                    propertyFetcher = new FlatFetcher();
                    fileName = "flats.csv";
                }

                int maxPages = Integer.parseInt(pagesField.getText());

                List<String[]> propertyData = propertyFetcher.fetchProperties(urlField.getText(), maxPages);
                CSVWriter csvWriter = new CSVWriter();
                csvWriter.writeDataToCSV(propertyData, fileName);

                int avgPrice = DataAnalytics.calculateAveragePrice(propertyData);
                int minPrice = DataAnalytics.getMinimumPrice(propertyData);
                int maxPrice = DataAnalytics.getMaximumPrice(propertyData);
                Map<String, Integer> typeCount = DataAnalytics.countPropertyTypes(propertyData);


                resultArea.setText("");  // clear previous results
                resultArea.append("Vidējā cena: " + avgPrice + "\n");
                resultArea.append("Min: " + minPrice + "\n");
                resultArea.append("Max: " + maxPrice + "\n");

                if (propertyTypeCombo.getSelectedIndex() != 1) {
                    for (Map.Entry<String, Integer> entry : typeCount.entrySet()) {
                        resultArea.append( entry.getKey() + ": " + entry.getValue() + "\n");
                    }
                }
            }
        });
    }
}