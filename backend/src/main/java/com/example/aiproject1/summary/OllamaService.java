package com.example.aiproject1.summary;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


@Service
public class OllamaService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";

    public String summarize(String text) {
        try {
            Map<String, Object> request = Map.of(
                    "model", "qwen2.5:3b",
                    "prompt", "Summarize the following text into bullet points:\n\n" + text,
                    "stream", true
            );

            URL url = new URL(OLLAMA_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Send request
            objectMapper.writeValue(connection.getOutputStream(), request);

            // Read streaming response
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            StringBuilder fullResponse = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                Map<String, Object> jsonLine = objectMapper.readValue(
                        line,
                        new TypeReference<Map<String, Object>>() {}
                );
                String responsePart = (String) jsonLine.get("response");
                if (responsePart != null) {
                    fullResponse.append(responsePart);
                }
            }
            reader.close();

            return fullResponse.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error calling Ollama API: " + e.getMessage(), e);
        }
    }


}
