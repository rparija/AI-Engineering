# 14 MCP Foundations

This folder teaches how MCP concepts show up in an early Spring AI demo. It is designed as a learning module rather than a polished standalone product.

## Why this folder matters

Use this folder as the conceptual bridge between tool calling and standalone MCP client-server designs.

It helps explain why teams separate tool providers from AI clients.

## Folder contents

- `SpringAIDemo`

## What to study here

- Read the smallest runnable example first
- Focus on the concept change introduced in this module rather than every file
- Compare this folder with the one before or after it to see how the architecture evolves

## Prerequisites

- Use the language runtime and package manager required by the subproject in this folder
- Add your own local model-provider credentials where needed
- Prefer local placeholder or `.env`-style setup instead of editing committed secrets

## How to run

From `SpringAIDemo`, run `./mvnw.cmd spring-boot:run`.

## Expected result

After running this folder, you should understand the main idea introduced here and be able to explain how it differs from the earlier lessons in the repo.

## Troubleshooting

- Check provider keys, dependency installation, and runtime version first
- If a subproject exists, run commands from inside that subproject rather than the module root
- Read any local README inside the subproject for stack-specific details

## What to study next

Move next to `15_MCP_26-12-2025`.
