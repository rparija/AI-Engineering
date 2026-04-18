package com.telusko.tutor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telusko.tutor.model.UserMemory;
import com.telusko.tutor.repository.UserMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemoryService {

    private final UserMemoryRepository repository;
    private final com.telusko.tutor.repository.ChatSessionRepository sessionRepository;
    private final ObjectMapper objectMapper;

    public UserMemory getOrCreateMemory(String userId) {
        return repository.findById(userId).orElseGet(() -> {
            UserMemory memory = new UserMemory();
            memory.setUserId(userId);
            memory.setConversationHistory("[]");
            memory.setLearningProgress("{}");
            memory.setUserLevel("beginner");
            return repository.save(memory);
        });
    }

    // --- Session Management ---

    public com.telusko.tutor.model.ChatSession createSession(String userId) {
        com.telusko.tutor.model.ChatSession session = new com.telusko.tutor.model.ChatSession();
        session.setUserId(userId);
        session.setTitle("New Chat");
        session.setConversationHistory("[]");
        return sessionRepository.save(session);
    }

    public List<com.telusko.tutor.model.ChatSession> getUserSessions(String userId) {
        return sessionRepository.findByUserIdOrderByUpdatedAtDesc(userId);
    }

    public com.telusko.tutor.model.ChatSession getSession(String sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Session not found"));
    }

    @org.springframework.transaction.annotation.Transactional
    public void addMessage(String sessionId, Message message) {
        com.telusko.tutor.model.ChatSession session = getSession(sessionId);
        try {
            List<Map<String, String>> history = objectMapper.readValue(session.getConversationHistory(),
                    new TypeReference<>() {
                    });
            Map<String, String> msgMap = new HashMap<>();
            msgMap.put("role", message.getMessageType().getValue());
            msgMap.put("content", message.getText());
            history.add(msgMap);
            session.setConversationHistory(objectMapper.writeValueAsString(history));
            session.setUpdatedAt(java.time.LocalDateTime.now());

            // Auto-update title if it's the first user message
            if ("user".equals(message.getMessageType().getValue()) && history.size() <= 2) {
                String content = message.getText();
                // Simple truncation for title
                String title = content.length() > 30 ? content.substring(0, 30) + "..." : content;
                session.setTitle(title);
            }

            sessionRepository.save(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Message> getHistory(String sessionId) {
        // Now retrieves based on sessionId
        com.telusko.tutor.model.ChatSession session = getSession(sessionId);
        List<Message> messages = new ArrayList<>();
        try {
            List<Map<String, String>> history = objectMapper.readValue(session.getConversationHistory(),
                    new TypeReference<>() {
                    });
            for (Map<String, String> msg : history) {
                String role = msg.get("role");
                String content = msg.get("content");
                if ("user".equals(role)) {
                    messages.add(new UserMessage(content));
                } else if ("assistant".equals(role)) {
                    messages.add(new AssistantMessage(content));
                } else if ("system".equals(role)) {
                    messages.add(new SystemMessage(content));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    public void updateProgress(String userId, String topic, String status) {
        UserMemory memory = getOrCreateMemory(userId);
        try {
            Map<String, String> progress = objectMapper.readValue(memory.getLearningProgress(), new TypeReference<>() {
            });
            progress.put(topic, status);
            memory.setLearningProgress(objectMapper.writeValueAsString(progress));
            repository.save(memory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProgress(String userId) {
        return getOrCreateMemory(userId).getLearningProgress();
    }
}
