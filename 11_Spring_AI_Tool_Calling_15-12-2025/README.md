# 11 Spring AI Tool Calling

This folder teaches how Spring AI gives models access to structured tools. It is designed as a learning module rather than a polished standalone product.

## Why this folder matters

Study this after RAG so you can clearly separate retrieval from tool use.

Tool calling is important when the model must fetch live or structured data instead of only generating text.

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

Run either Spring Boot subproject with `./mvnw.cmd spring-boot:run`.

## Expected result

After running this folder, you should understand the main idea introduced here and be able to explain how it differs from the earlier lessons in the repo.

## Troubleshooting

- Check provider keys, dependency installation, and runtime version first
- If a subproject exists, run commands from inside that subproject rather than the module root
- Read any local README inside the subproject for stack-specific details

## What to study next

Move next to `14_MCP_23-12-2025` to see tool exposure evolve toward MCP.
