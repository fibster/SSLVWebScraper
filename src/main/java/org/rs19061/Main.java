//package org.rs19061;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.List;
//
//public class Main {
//
//    private static JComboBox<String> propertyTypeCombo;
//    private static JTextField urlField, pagesField;
//    private static JButton fetchButton;
//    private static JComboBox<String> cityCombo;
//    private static Property property;
//    private static Fetcher fetcher;
//
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Property Fetcher");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        propertyTypeCombo = new JComboBox<>(new String[]{"Flat", "House"});
//        cityCombo = new JComboBox<>();
//        urlField = new JTextField(30);
//        pagesField = new JTextField(5);
//        fetchButton = new JButton("Fetch and Write");
//        fetcher = new Fetcher();
//
//        JPanel panel = new JPanel();
//        panel.setLayout(new FlowLayout());
//
//        panel.add(new JLabel("Property Type:"));
//        panel.add(propertyTypeCombo);
//        panel.add(new JLabel("City:"));
//        panel.add(cityCombo);
//        panel.add(new JLabel("URL:"));
//        panel.add(urlField);
//        panel.add(new JLabel("Max Pages:"));
//        panel.add(pagesField);
//        panel.add(fetchButton);
//
//        frame.add(panel);
//        frame.pack();
//        frame.setVisible(true);
//
//        propertyTypeCombo.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Integer selectedOption = propertyTypeCombo.getSelectedIndex();
//                if (selectedOption == 0) {
//                    property = new Flat();
//                } else if (selectedOption == 1) {
//                    property = new House();
//                }
//                List<String> cities = property.fetchCities();
//                cityCombo.removeAllItems();
//                for(String city : cities){
//                    cityCombo.addItem(city);
//                }
//            }
//        });
//
//        cityCombo.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String city = (String) cityCombo.getSelectedItem();
//                String url = property.constructCityUrl(city);
//                urlField.setText(url);
//            }
//        });
//
//        fetchButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                PropertyFetcher propertyFetcher;
//                String fileName;
//
//                if (property instanceof House) {
//                    propertyFetcher = new HouseFetcher();
//                    fileName = "houses.csv";
//                } else {
//                    propertyFetcher = new FlatFetcher();
//                    fileName = "flats.csv";
//                }
//
//                int maxPages = Integer.parseInt(pagesField.getText());
//
//                List<String[]> propertyData = propertyFetcher.fetchProperties(urlField.getText(), maxPages);
//                CSVWriter csvWriter = new CSVWriter();
//                csvWriter.writeDataToCSV(propertyData, fileName);
//            }
//        });
//    }
//}

package org.rs19061;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Integer selectedOption = Fetcher.fetchOption();
        String baseUrl = Fetcher.constructUrl(selectedOption);
        System.out.println(baseUrl);

        int maxPages = 3;
        PropertyFetcher propertyFetcher;
        String fileName;

        if (selectedOption == 2) {
            propertyFetcher = new HouseFetcher();
            fileName = "houses.csv";
        } else {
            propertyFetcher = new FlatFetcher();
            fileName = "flats.csv";
        }

        List<String[]> propertyData = propertyFetcher.fetchProperties(baseUrl, maxPages);
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeDataToCSV(propertyData, fileName);
    }
}