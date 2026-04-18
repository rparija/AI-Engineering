package com.mcp.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleAiController {

    ChatClient chatClient;
    ChatMemory chatMemory= MessageWindowChatMemory.builder().build();

    public SimpleAiController(ChatClient.Builder  builder, ToolCallbackProvider toolCallbackProvider)
    {
        chatClient=builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
        .defaultToolCallbacks(toolCallbackProvider).
                build();
    }

    @GetMapping("/api/getAIResponse")
    public String getAIResponse(@RequestParam String message) {
       return chatClient.prompt(message).call().content();
    }
}
