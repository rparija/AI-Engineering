# 08 Spring AI Vector Database

This folder teaches how Spring AI stores and retrieves embedded content from a vector-backed system. It is designed as a learning module rather than a polished standalone product.

## Why this folder matters

Use this folder to connect embeddings with actual retrieval infrastructure.

This is a key transition point between embeddings and full RAG applications.

## Folder contents

- `SpringAIDemo`
- `SpringAIDemoGemini`

## What to study here

- Read the smallest runnable example first
- Focus on the concept change introduced in this module rather than every file
- Compare this folder with the one before or after it to see how the architecture evolves

## Prerequisites

- Use the language runtime and package manager required by the subproject in this folder
- Add your own local model-provider credentials where needed
- Prefer local placeholder or `.env`-style setup instead of editing committed secrets

## How to run

Pick either subproject and run `./mvnw.cmd spring-boot:run` inside it.

## Expected result

After running this folder, you should understand the main idea introduced here and be able to explain how it differs from the earlier lessons in the repo.

## Troubleshooting

- Check provider keys, dependency installation, and runtime version first
- If a subproject exists, run commands from inside that subproject rather than the module root
- Read any local README inside the subproject for stack-specific details

## What to study next

Move next to `09_Spring_AI_RAG_9-12-2025`.
