# 05 LangChain

This folder teaches basic LangChain prompt and model usage in Python. It is designed as a learning module rather than a polished standalone product.

## Why this folder matters

This folder is the Python-side counterpart to the early Spring AI examples.

Use it to understand chains and provider abstraction before moving into memory or RAG.

## Folder contents

- `langchain_openai_demo.py`
- `langchain_gemini_demo.py`
- `.env`

## What to study here

- Read the smallest runnable example first
- Focus on the concept change introduced in this module rather than every file
- Compare this folder with the one before or after it to see how the architecture evolves

## Prerequisites

- Use the language runtime and package manager required by the subproject in this folder
- Add your own local model-provider credentials where needed
- Prefer local placeholder or `.env`-style setup instead of editing committed secrets

## How to run

Create a local `.env`, install dependencies if needed, then run one of the scripts with Python.

## Expected result

After running this folder, you should understand the main idea introduced here and be able to explain how it differs from the earlier lessons in the repo.

## Troubleshooting

- Check provider keys, dependency installation, and runtime version first
- If a subproject exists, run commands from inside that subproject rather than the module root
- Read any local README inside the subproject for stack-specific details

## What to study next

Move next to `07_LangChain_ChatMemory_27-11-2025`.
