package com.telusko.tutor.controller;

import com.telusko.tutor.service.AgentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.telusko.tutor.controller.ChatController.ChatResponse;
import com.telusko.tutor.controller.ChatController.ChatRequest;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final AgentService agentService;

    private final com.telusko.tutor.service.MemoryService memoryService;
    private final org.springframework.messaging.simp.SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void handleMessage(ChatRequest request) {
        String sessionId = request.getSessionId();

        // 1. Send user message acknowledgement (optional, or just rely on client
        // optimism)
        // For now, let's just send the agent response.

        String responseContent = agentService.processUserMessage(sessionId, request.getMessage());

        // 2. Send Agent Response to specific session topic
        ChatResponse response = new ChatResponse(responseContent);
        messagingTemplate.convertAndSend("/topic/session/" + sessionId, response);
    }

    // Create new session
    @org.springframework.web.bind.annotation.PostMapping("/api/chat/sessions")
    public SessionResponse createSession(
            @org.springframework.web.bind.annotation.RequestBody CreateSessionRequest request) {
        com.telusko.tutor.model.ChatSession session = memoryService.createSession(request.getUserId());
        return new SessionResponse(session.getSessionId(), session.getTitle(), session.getCreatedAt().toString());
    }

    // List user sessions
    @GetMapping("/api/chat/sessions")
    public List<SessionResponse> getUserSessions(@org.springframework.web.bind.annotation.RequestParam String userId) {
        return memoryService.getUserSessions(userId).stream()
                .map(s -> new SessionResponse(s.getSessionId(), s.getTitle(), s.getCreatedAt().toString()))
                .collect(Collectors.toList());
    }

    // Get specific session history
    @GetMapping("/api/chat/sessions/{sessionId}")
    public List<HistoryResponse> getSessionHistory(@PathVariable String sessionId) {
        return agentService.getHistory(sessionId).stream()
                .map(msg -> new HistoryResponse(msg.getMessageType().getValue(), msg.getText()))
                .collect(Collectors.toList());
    }

    // Keep old endpoint for backward compatibility or remove?
    // Let's deprecate/remove it as we are moving to session-based.
    // Spec said "history for that user" but now it's "history for that session".

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HistoryResponse {
        private String role;
        private String content;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRequest {
        private String sessionId; // Changed from userId
        private String message;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateSessionRequest {
        private String userId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SessionResponse {
        private String sessionId;
        private String title;
        private String createdAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatResponse {
        private String content;
    }
}
