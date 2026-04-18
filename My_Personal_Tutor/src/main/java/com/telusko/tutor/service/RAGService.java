package com.telusko.tutor.service;

import com.telusko.tutor.model.CourseEmbedding;
import com.telusko.tutor.repository.CourseEmbeddingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class RAGService {

    private final CourseEmbeddingRepository repository;
    private final EmbeddingModel embeddingModel;

    public void ingestContent(Resource resource, String courseId, String section) {
        // 1. Read PDF/Text
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(resource);
        List<Document> documents = pdfReader.get();

        // 2. Split into chunks
        TokenTextSplitter splitter = new TokenTextSplitter();
        List<Document> chunks = splitter.apply(documents);

        // 3. Embed and Store
        for (Document chunk : chunks) {
            float[] embedding = embeddingModel.embed(chunk.getText());
            String embeddingStr = Arrays.toString(embedding); // Fix: Use Arrays.toString() for arrays

            // For MariaDB Vector, we often pass the vector as a string representation like
            // "[0.1, 0.2, ...]"
            // The `embedding.toString()` usually produces "[0.1, 0.2, ...]" which is
            // compatible with many vector parsers.

            repository.saveEmbedding(courseId, section, chunk.getText(), "{}", embeddingStr);
        }
    }

    public List<String> retrieveRelevantContent(String query) {
        // 1. Embed query
        float[] queryEmbedding = embeddingModel.embed(query);
        String queryEmbeddingStr = Arrays.toString(queryEmbedding);

        // 2. Search in DB
        List<CourseEmbedding> results = repository.findSimilar(queryEmbeddingStr, 5);

        // 3. Return content
        return results.stream().map(CourseEmbedding::getContent).collect(Collectors.toList());
    }
}
