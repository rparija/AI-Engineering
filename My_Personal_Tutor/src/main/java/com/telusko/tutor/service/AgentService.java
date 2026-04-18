package com.telusko.tutor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telusko.tutor.tools.AgentTools;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgentService {

    private final ChatClient.Builder chatClientBuilder;
    private final MemoryService memoryService;
    private final RAGService ragService;
    private final AgentTools agentTools;
    private final ObjectMapper objectMapper;

    private static final String SYSTEM_PROMPT = "You are an AI tutor that helps students learn deeply. You can explain, quiz, summarize and track learning progress.";

    private static final String PLANNER_PROMPT = """
            Given:
            - User message: {message}
            - Memory: {memory}
            - Retrieved context: {context}

            LLM must output JSON:
            {{
              "action": "EXPLAIN|QUIZ|SUMMARIZE|ASK_FOLLOWUP|NONE",
              "topic": "...",
              "difficulty": "beginner|intermediate|advanced"
            }}
            """;

    private static final String FINAL_PROMPT = """
            Use:
            - Tool output: {tool_output}
            - Retrieved content: {context}
            - Memory: {memory}

            Generate tutor response and optionally ask a follow-up.
            """;

    public String processUserMessage(String sessionId, String userMessage) {
        log.info("Processing message for session {}: {}", sessionId, userMessage);
        ChatClient chatClient = chatClientBuilder.build();

        // 1. Load Memory (Session History)
        List<Message> history = memoryService.getHistory(sessionId);
        // Progress is still per user, but for now we get context from session or we
        // need to look up owner?
        // Let's assume for this scope we stick to just history context or we add userId
        // lookup if needed.
        // But wait, MemoryService.getProgress(userId) needs userId.
        // We will fetch session to get userId.
        com.telusko.tutor.model.ChatSession session = memoryService.getSession(sessionId);
        String userId = session.getUserId();

        String progress = memoryService.getProgress(userId);

        // 2. RAG Search (Simple heuristic: always search if message is long enough or
        // contains keywords, or just search always for context)
        // For this tutor, we'll search always to provide context.
        List<String> retrievedDocs = ragService.retrieveRelevantContent(userMessage);
        String context = String.join("\n", retrievedDocs);

        // 3. Planner
        String plannerPromptText = new PromptTemplate(PLANNER_PROMPT).create(Map.of(
                "message", userMessage,
                "memory", progress, // Passing progress as memory context for planning
                "context", context)).getContents();

        // We don't send the whole history to the planner, just the current state and
        // message to decide action.
        // Or we could. Let's keep it simple as per spec.

        String planJson = chatClient.prompt().user(plannerPromptText).call().content();
        log.info("Planner Output: {}", planJson);

        String action = "NONE";
        String topic = "";
        String difficulty = "beginner";

        try {
            // Clean up JSON if needed (sometimes LLM adds markdown blocks)
            if (planJson.contains("```json")) {
                planJson = planJson.substring(planJson.indexOf("```json") + 7, planJson.lastIndexOf("```"));
            } else if (planJson.contains("```")) {
                planJson = planJson.substring(planJson.indexOf("```") + 3, planJson.lastIndexOf("```"));
            }

            Map<String, String> plan = objectMapper.readValue(planJson, new TypeReference<Map<String, String>>() {
            });
            action = plan.getOrDefault("action", "NONE");
            topic = plan.getOrDefault("topic", "");
            difficulty = plan.getOrDefault("difficulty", "beginner");
        } catch (Exception e) {
            log.error("Failed to parse plan", e);
        }

        // 4. Execute Tool
        String toolOutput = "";
        if ("EXPLAIN".equalsIgnoreCase(action)) {
            toolOutput = agentTools.explainConcept(topic, difficulty);
        } else if ("QUIZ".equalsIgnoreCase(action)) {
            toolOutput = agentTools.generateQuiz(topic);
        } else if ("SUMMARIZE".equalsIgnoreCase(action)) {
            toolOutput = agentTools.summarizeContent(context); // Summarize retrieved context
        } else if ("ASK_FOLLOWUP".equalsIgnoreCase(action)) {
            toolOutput = "Ask a follow-up question about " + topic;
        }

        log.info("Tool Output: {}", toolOutput);

        // 5. Final Response
        String finalPromptText = new PromptTemplate(FINAL_PROMPT).create(Map.of(
                "tool_output", toolOutput,
                "context", context,
                "memory", progress)).getContents();

        // Construct the prompt with history
        // We append the final prompt instructions to the user message or as a system
        // instruction?
        // The spec says "Generate final response".
        // We should provide the system prompt, history, and the specific instruction
        // for this turn.

        List<Message> promptMessages = new java.util.ArrayList<>(history);
        promptMessages.add(new SystemMessage(SYSTEM_PROMPT));
        promptMessages.add(new UserMessage(userMessage)); // The current user message
        promptMessages.add(new SystemMessage(finalPromptText)); // The instructions for this response

        String response = chatClient.prompt().messages(promptMessages).call().content();

        // 6. Update Memory (Session)
        memoryService.addMessage(sessionId, new UserMessage(userMessage));
        memoryService.addMessage(sessionId, new org.springframework.ai.chat.messages.AssistantMessage(response));

        return response;
    }

    public List<Message> getHistory(String sessionId) {
        return memoryService.getHistory(sessionId);
    }
}
