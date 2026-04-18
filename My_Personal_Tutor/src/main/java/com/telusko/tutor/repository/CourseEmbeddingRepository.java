package com.telusko.tutor.repository;

import com.telusko.tutor.model.CourseEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CourseEmbeddingRepository extends JpaRepository<CourseEmbedding, Long> {

    // Native query to insert vector
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO course_embeddings (course_id, section, content, metadata, embedding) VALUES (:courseId, :section, :content, :metadata, VEC_FromText(:embeddingStr))", nativeQuery = true)
    void saveEmbedding(@Param("courseId") String courseId,
            @Param("section") String section,
            @Param("content") String content,
            @Param("metadata") String metadata,
            @Param("embeddingStr") String embeddingStr);

    // Native query for vector search using cosine similarity (or Euclidean
    // distance)
    // MariaDB Vector uses VEC_DISTANCE_COSINE or similar functions.
    // Assuming VEC_DISTANCE_COSINE(vec1, vec2) returns distance.
    // We want the closest, so order by distance ASC.
    @Query(value = "SELECT * FROM course_embeddings ORDER BY VEC_DISTANCE_COSINE(embedding, VEC_FromText(:queryEmbedding)) ASC LIMIT :limit", nativeQuery = true)
    List<CourseEmbedding> findSimilar(@Param("queryEmbedding") String queryEmbedding, @Param("limit") int limit);
}
