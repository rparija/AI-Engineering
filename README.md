# AI Engineering

A curated study repository of hands-on AI engineering examples, classroom demos, and mini-projects built while learning modern AI application development with Java, Python, Spring AI, LangChain, LangGraph, MCP, vector databases, and end-to-end AI products.

GitHub Pages site: `https://rparija.github.io/AI-Engineering/`

## Repository structure

This repo is organized as a learning journey. Most folders are timestamped lesson snapshots, so you can follow the progression from API basics to full AI applications.

### Core learning track

- `01_First_Code_13-11-2025` - first AI code examples and introductory Java demos
- `02_Exploring_SDKs_17-11-2025` - SDK-level experiments with OpenAI and Gemini integrations
- `03_Embedding_And_Vector_Impl_20-11-2025` - embedding generation and vector representation examples
- `04_Spring_AI_21-11-2025` - early Spring AI starter examples
- `05_LangChain_24-11-2025` - foundational LangChain demos
- `06_Spring_AI_Embeddings_25-11-2001` - Spring AI embedding workflows and a conversational UI example
- `07_LangChain_ChatMemory_27-11-2025` - chat memory patterns in LangChain
- `08_Spring_AI_Vector_Database_8-12-2025` - Spring AI with vector databases and retrieval setups
- `09_Spring_AI_RAG_9-12-2025` - retrieval-augmented generation examples
- `10_LangChain_RAG_11-12-2025` - LangChain-based RAG walkthroughs
- `11_Spring_AI_Tool_Calling_15-12-2025` - tool-calling examples with Spring AI
- `12_LangChain__Tool__Calling_17-12-2026` - LangChain tool-calling demos and project setup
- `13_LangChain_Tool_Calling_22-12-2025` - additional Python tool-calling examples
- `14_MCP_23-12-2025` - first MCP-oriented Spring AI experiments
- `15_MCP_26-12-2025` - MCP demo applications and server setup
- `15_MCP_Langchain_29-12-2025_custom` - custom LangChain plus MCP filesystem or server experiments
- `16_MCP_Langchain_29-12-2025` - MCP with LangChain integrations and filesystem tooling
- `17_Langchain_AI_Agents_05-01-2026` - agent-oriented LangChain examples
- `18_LangGraph_08_01_2026` - multi-agent and workflow orchestration with LangGraph
- `19_LangGraph_Sequential_Workflow_12-01-2026` - sequential LangGraph workflow patterns
- `20_LangGraph_Parallel_Workflow_13-01-2026` - parallel workflow orchestration with LangGraph
- `23_Hugging_Face_And_Fine_Tuning` - Hugging Face and model fine-tuning exploration

### Project folders

- `21_Project` - e-commerce project variants combining backend, frontend, and AI features
- `22_Project` - expanded AI-enabled project implementations including LangChain services
- `My_Personal_Tutor` - a more complete tutor-style AI application with Spring AI and MariaDB
- `LANGGRAPH-DEMO` - standalone LangGraph demo project
- `mcp_demo` - MCP client-side demo project
- `mcpserver` - MCP server implementation examples
- `testFileSystemMCP` - filesystem MCP experimentation

## Start here

If you are new to the repo, begin with `START-HERE.md`. It includes a beginner-to-advanced study path, short-track recommendations, and a suggested order for moving from simple demos to full applications.

## Concept map

This repository is easiest to understand if you think of it as a progression of capabilities:

- **SDK basics** - `01`, `02`, and `05` show how to call models directly or through a framework
- **Embeddings** - `03` and `06` introduce semantic representations used later in search and retrieval
- **Vector databases** - `08` shows how embedded content becomes searchable
- **RAG** - `09` and `10` combine retrieval with generation to ground answers in real data
- **Tool calling** - `11`, `12`, and `13` show how models invoke structured capabilities beyond plain prompting
- **MCP** - `14`, `15`, `16`, `mcp_demo`, and `mcpserver` move from tool exposure to client-server tool ecosystems
- **Agents and workflows** - `17`, `18`, `19`, `20`, and `LANGGRAPH-DEMO` show orchestration, state, and multi-step reasoning patterns
- **End-to-end products** - `21_Project`, `22_Project`, and `My_Personal_Tutor` combine these ideas into complete applications
- **Model ecosystem** - `23_Hugging_Face_And_Fine_Tuning` expands the path beyond hosted APIs and into model experimentation

A simple way to read the repo is:

`prompting -> embeddings -> vector search -> RAG -> tool calling -> MCP -> agents -> workflows -> full applications`

## Recommended learning path

1. Start with the lower-numbered folders and move upward.
2. Open local `README.md` files inside individual projects where available.
3. Treat each folder as an independent learning module unless the folder clearly contains subprojects.
4. Add your own API keys, database settings, and environment variables where needed before running examples.

## Standout projects

- `My_Personal_Tutor` - full Spring AI tutor application with chat, ingestion, memory, and MariaDB-backed retrieval
- `21_Project/SpringEcomAI` - Spring Boot e-commerce backend with AI-powered product and chatbot capabilities
- `22_Project/SpringEcomAI-LangChain` - Python and LangChain version of the e-commerce AI project
- `mcp_demo` and `mcpserver` - practical MCP client-server examples
- `LANGGRAPH-DEMO` - multiple workflow-oriented LangGraph examples in one place

## Running the projects

### Java and Spring Boot projects

- Most Java folders use Maven wrappers, so you can run them with `mvnw.cmd spring-boot:run` from the project directory.
- Projects using Spring AI usually require `OPENAI_API_KEY` or another model-provider key before startup.
- Some examples also expect MariaDB running locally with the database names shown in their `application.properties` or `application.yml`.

### Python and LangChain or LangGraph projects

- Create a virtual environment in the project folder.
- Install dependencies from `pyproject.toml`, `requirements.txt`, or `uv.lock` depending on the project.
- Add the required keys in local `.env` files before running the scripts.

### Frontend projects

- Frontend apps live mainly in `21_Project/e-com-Frontend` and `22_Project/e-com-Frontend`.
- Install dependencies with `npm install`.
- Start the development server with `npm run dev`.

## Tech covered

- Java and Spring Boot
- Spring AI
- Python
- LangChain
- LangGraph
- MCP
- Vector databases and embeddings
- RAG applications
- AI tool calling
- Multi-agent workflows
- Full-stack AI project patterns

## Notes

- This repository is intended for study and experimentation.
- Some folders contain independent Maven, Python, or frontend projects with their own setup instructions.
- Generated folders such as `node_modules` and `target` are intentionally excluded from version control.
- Example config files now use placeholders for sensitive values; add your own local keys before running AI-powered demos.

## Credits
special thanks to Naveen Telusko and team who prepared this wonderful course which i attended and gained all this knowledge. Forever Grateful.
