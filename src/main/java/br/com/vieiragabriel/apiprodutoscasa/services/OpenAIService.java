package br.com.vieiragabriel.apiprodutoscasa.services;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAIService {

    private final OpenAiService openAiService;
    private final String model;

    public OpenAIService(@Value("${openai.api.key}") String apiKey, 
                         @Value("${openai.model}") String model) {
        this.openAiService = new OpenAiService(apiKey, Duration.ofSeconds(30));
        this.model = model;
    }

    public String generateProductSummary(String productName, String description) {
        List<ChatMessage> messages = new ArrayList<>();
        
        // System message to set the context
        messages.add(new ChatMessage("system", 
                "You are a helpful assistant that summarizes product descriptions concisely."));
        
        // User message with the product information
        String prompt = String.format(
                "Resuma de forma clara e atrativa o que este produto faz:\n" +
                        "Nome: %s\nDescrição: %s",
                productName, description);
        
        messages.add(new ChatMessage("user", prompt));

        // Create the completion request
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model(model)
                .maxTokens(150)
                .temperature(0.7)
                .build();

        // Call the API and get the response
        return openAiService.createChatCompletion(completionRequest)
                .getChoices().get(0).getMessage().getContent();
    }
}