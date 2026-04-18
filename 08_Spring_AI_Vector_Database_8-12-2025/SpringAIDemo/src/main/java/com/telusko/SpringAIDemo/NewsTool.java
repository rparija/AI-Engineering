package com.telusko.SpringAIDemo;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NewsTool {


    @Tool(description = "Get latest news headlines for a given topic")
    public String getLatestNewsHeadlines(String topic) {
        // The URL provided in your query
        String apiUrl = "https://newsapi.org/v2/everything?q="+topic+"&from=2025-11-15&sortBy=publishedAt&apiKey=92c803efe7ac4fcd8ad577d270d72b22";

        // 1. Create the RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        try {
            System.out.println("Sending request to: " + apiUrl);

            // 2. Make the GET request using getForObject
            // This method automatically converts the response to the specified type (String in this case)
            String response = restTemplate.getForObject(apiUrl, String.class);

            // 3. Handle the success response
            System.out.println("Response Body:");
            System.out.println(response);
            return response;

        } catch (Exception e) {
            // RestTemplate throws RuntimeExceptions (like RestClientException)
            System.err.println("Request failed: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
