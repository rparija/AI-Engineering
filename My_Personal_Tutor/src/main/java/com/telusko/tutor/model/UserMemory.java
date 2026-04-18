package com.telusko.tutor.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_memory")
@Data
@NoArgsConstructor
public class UserMemory {

    @Id
    private String userId;

    @Column(columnDefinition = "JSON")
    private String conversationHistory; // List of messages

    @Column(columnDefinition = "JSON")
    private String learningProgress; // Map of topic -> status/score

    private String userLevel; // beginner, intermediate, advanced
}
