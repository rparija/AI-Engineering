-- CRITICAL: This file defines the table structure including the VECTOR column.
-- Do NOT delete this file.
-- Hibernate cannot automatically create the VECTOR(1536) column correctly.

CREATE TABLE IF NOT EXISTS course_embeddings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id VARCHAR(255),
    section VARCHAR(255),
    content TEXT,
    metadata JSON,
    embedding VECTOR(1536)
);

-- Attempt to add the column if the table exists but the column is missing
-- This handles the case where Hibernate previously created a "bad" version of the table.
ALTER TABLE course_embeddings ADD COLUMN IF NOT EXISTS embedding VECTOR(1536);

CREATE TABLE IF NOT EXISTS chat_sessions (
    session_id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255),
    title VARCHAR(255),
    conversation_history JSON, -- Using JSON type for MariaDB compatibility/performance
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
