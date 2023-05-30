package org.rs19061;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePropertyFetcher implements PropertyFetcher {

    // Inner interface to define the contract for a parser
    protected interface Parser {
        String[] parseRow(Element row);
    }

    private Parser parser;

    protected BasePropertyFetcher(Parser parser) {
        this.parser = parser;
    }

    @Override
    public List<String[]> fetchProperties(String baseUrl, int maxPages) {
        List<String[]> data = new ArrayList<>();

        for (int pageNumber = 1; pageNumber <= maxPages; pageNumber++) {
            try {
                // Construct the URL for each page
                String url = baseUrl + "page" + pageNumber + ".html";
                // Connect to the URL and retrieve the HTML document
                Document doc = Jsoup.connect(url).get();
                // Select all table rows with IDs starting with "tr_"
                Elements rows = doc.select("tr[id^=tr_]");

                for (Element row : rows) {
                    // Parse the current row using the provided parser
                    String[] parsedRow = parser.parseRow(row);
                    if (parsedRow != null) {
                        // If the parsed row is not null, add it to the data list
                        data.add(parsedRow);
                    }
                }
            } catch (IOException e) {
                // Handle any IO exceptions that occur during the data fetching process
                System.out.println("Error fetching data: " + e.getMessage());
            }
        }

        return data;
    }
}