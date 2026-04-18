# 15 MCP Client and Server

This folder teaches how an MCP server and MCP client work as separate applications. It is designed as a learning module rather than a polished standalone product.

## Why this folder matters

Run the server first, then the client, and watch how the client consumes remote tools.

This module is one of the clearest MCP learning points in the repo.

## Folder contents

- `mcpServer`
- `mcp_demo`

## What to study here

- Read the smallest runnable example first
- Focus on the concept change introduced in this module rather than every file
- Compare this folder with the one before or after it to see how the architecture evolves

## Prerequisites

- Use the language runtime and package manager required by the subproject in this folder
- Add your own local model-provider credentials where needed
- Prefer local placeholder or `.env`-style setup instead of editing committed secrets

## How to run

Run both Spring Boot apps with `./mvnw.cmd spring-boot:run` in their own folders.

## Expected result

After running this folder, you should understand the main idea introduced here and be able to explain how it differs from the earlier lessons in the repo.

## Troubleshooting

- Check provider keys, dependency installation, and runtime version first
- If a subproject exists, run commands from inside that subproject rather than the module root
- Read any local README inside the subproject for stack-specific details

## What to study next

Move next to `16_MCP_Langchain_29-12-2025`.
