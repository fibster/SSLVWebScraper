package org.rs19061;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public abstract class BasePropertyFetcher implements PropertyFetcher {
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
                String url = baseUrl + "page" + pageNumber + ".html";
                Document doc = Jsoup.connect(url).get();
                Elements rows = doc.select("tr[id^=tr_]");

                for (Element row : rows) {
                    String[] parsedRow = parser.parseRow(row);
                    if (parsedRow != null) {
                        data.add(parsedRow);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error fetching data: " + e.getMessage());
            }
        }

        return data;
    }
}
