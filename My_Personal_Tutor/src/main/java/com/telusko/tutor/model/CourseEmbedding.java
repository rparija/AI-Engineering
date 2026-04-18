package com.telusko.tutor.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_embeddings")
@Data
@NoArgsConstructor
public class CourseEmbedding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseId;
    private String section;

    @Column(columnDefinition = "TEXT")
    private String content;

    // We store the embedding as a JSON string or specific vector type if supported
    // by driver/dialect
    // For MariaDB Vector, it is often treated as a special binary or array.
    // Here we will use a native query to handle the vector data, so we might not
    // map it directly
    // or map it as a byte array/string depending on the driver.
    // Let's assume we handle insertion via native query for the vector column.
    // But to keep it simple for JPA, we might omit it here or mark it @Transient if
    // we only use it in native queries.
    // However, we need to store it.
    // Let's try mapping it as an Object or String and see if Hibernate handles it,
    // otherwise we rely on the repository to save it.

    // Actually, for simplicity and correctness with the vector plugin,
    // we will use a custom repository method to save and search.

    @Column(columnDefinition = "JSON")
    private String metadata;

    // This field is for JPA to ignore the vector column if it can't handle it
    // We will manage the vector column via native SQL.
}
