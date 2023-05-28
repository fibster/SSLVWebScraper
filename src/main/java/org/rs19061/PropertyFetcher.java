package org.rs19061;

import java.util.List;

public interface PropertyFetcher {
    List<String[]> fetchProperties(String baseUrl, int maxPages);
}


