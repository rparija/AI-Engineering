package com.mcp.server.mcpserver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class StockMarketTool {


    @Tool(description = "Get latest stock price of company by its symbol")
    public String getCurrentStockPrice(String symbol) {
        // The URL provided in your query
        String apiUrl = "https://query1.finance.yahoo.com/v8/finance/chart/" + symbol + "?interval=1d&range=1d";

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Keep the browser masquerade headers, they are still helpful
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            headers.set("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8");
            headers.set("Accept-Language", "en-US,en;q=0.9");
            headers.set("Accept-Encoding", "identity");
            headers.set("Connection", "keep-alive");
            headers.set("Upgrade-Insecure-Requests", "1");
            headers.set("Sec-Fetch-Dest", "document");
            headers.set("Sec-Fetch-Mode", "navigate");
            headers.set("Sec-Fetch-Site", "none");
            headers.set("Sec-Fetch-User", "?1");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            System.out.println("Fetching price for: " + symbol);

            // Execute the request
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            // Parse the JSON response
            // New Structure: { "chart": { "result": [ { "meta": { "regularMarketPrice": ... } } ] } }
            JsonNode root = mapper.readTree(response.getBody());

            // Navigate to the 'meta' object inside the chart result
            JsonNode chartResult = root.path("chart").path("result").get(0);
            JsonNode meta = chartResult.path("meta");

            // Extract useful fields
            // Note: 'longName' is not always available in chart API, so we use 'symbol'
            double price = meta.path("regularMarketPrice").asDouble();
            String currency = meta.path("currency").asText();
            String stockSymbol = meta.path("symbol").asText();
            // If the market is open, you might also find 'regularMarketTime' here

            System.out.println("------------------------------------------------");
            System.out.println("Symbol: " + stockSymbol);
            System.out.println("Price: " + price + " " + currency);
            System.out.println("------------------------------------------------");
            return response.getBody();
        } catch (HttpClientErrorException e) {
            // Specific handling for HTTP errors (404, 429, 500)
            System.err.println("HTTP Error: " + e.getStatusCode());
            System.err.println("Response Body: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.err.println("General Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
