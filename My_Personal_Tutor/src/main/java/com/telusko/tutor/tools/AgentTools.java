package com.telusko.tutor.tools;

import com.telusko.tutor.service.MemoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AgentTools {

    private final MemoryService memoryService;

    @Tool(description = "Explain a concept to the student based on their difficulty level.")
    public String explainConcept(String topic, String difficulty) {
        // In a real scenario, this might query a knowledge base or use an LLM to
        // generate it.
        // Since we are inside an agent loop, this tool might just return a prompt or
        // specific content.
        // But to make it useful, let's say it returns a structured request for the LLM
        // to fulfill in the final step,
        // or it could be a placeholder if the LLM does the explanation itself.
        // However, the spec says "Output: Explanation prompt".
        return "Please explain the concept of '" + topic + "' at a '" + difficulty
                + "' level. Use analogies and examples.";
    }

    @Tool(description = "Generate a quiz for the student on a specific topic.")
    public String generateQuiz(String topic) {
        return "Create a 3-question quiz about '" + topic
                + "'. Include multiple-choice options and the correct answer hidden.";
    }

    @Tool(description = "Summarize the provided content.")
    public String summarizeContent(String content) {
        return "Summarize the following content concisely:\n" + content;
    }

    @Tool(description = "Update the learner's progress on a topic.")
    public String updateProgress(String userId, String topic, String status) {
        memoryService.updateProgress(userId, topic, status);
        return "Progress updated for user " + userId + ": " + topic + " is now " + status;
    }

    @Tool(description = "Get the learner's progress.")
    public String getProgress(String userId) {
        return memoryService.getProgress(userId);
    }
}
