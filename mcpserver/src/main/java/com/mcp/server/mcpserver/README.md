🧠 MCP Server — Spring Boot (Port 8282)
✅ What this README covers

What this project is & what it exposes

How MCP registration works

Quick checks to confirm an MCP client can connect

📌 Project Summary (Brief)

This is a Spring Boot application named mcpserver that runs on:

http://localhost:8282


It acts as an MCP (Model Context Protocol) server / tool provider using Spring AI.
The server exposes several custom tools annotated with @Tool, so AI clients can discover and execute them.

🔧 Tools Provided
🕒 DateTimeTool

getCurrentDateAndTime() → returns server-local ZonedDateTime

getCurrentDateAndTimeTimeZoned(String timezone) → returns time for a specific timezone

📰 NewsTool

Calls a news REST API using RestTemplate

Returns the raw JSON response (currently uses a hard-coded API key & date — demo only)

📈 StockMarketTool

Calls Yahoo Finance chart API

Prints parsed details

Returns JSON response

⚙️ Runtime Configuration

Configured in src/main/resources/application.properties:

spring.application.name=mcpserver
server.port=8282
logging.level.org.springframework.ai.mcp=DEBUG
logging.level.org.springframework.ai=DEBUG

🔗 How MCP Registration Works (Short Overview)

When the MCP server starts, Spring AI:

✔ discovers your @Tool-annotated beans
✔ wraps them as ToolCallbacks
✔ exposes them over the MCP SSE endpoint /mcp
✔ allows MCP-enabled clients to connect & register

For successful registration:

The server must be reachable at the URL configured in the client

The MCP endpoint must exist (ex: http://localhost:8282/mcp)

Client & server configs must align

Security/CORS must not block the SSE connection

🔍 Practical Checks to Ensure Client Registration Works
1️⃣ Server is running
http://localhost:8282


404 is OK — that just means no root handler.

2️⃣ MCP endpoint responds to SSE requests

Run:

curl -H "Accept: text/event-stream" http://localhost:8282/mcp


Expected → connection stays open / streams

❌ 404 = MCP endpoint not mounted
❌ connection refused = server not running

3️⃣ Client config matches server URL

Example:

spring.ai.mcp.client.sse.connection.spring-ai-mcp-server.enabled=true
spring.ai.mcp.client.sse.connection.spring-ai-mcp-server.name=spring-ai-mcp-server
spring.ai.mcp.client.sse.connection.spring-ai-mcp-server.url=http://localhost:8282/mcp

4️⃣ Enable debug logs (optional)
logging.level.org.springframework.ai.mcp=DEBUG


Client logs should show:

Connecting to MCP server spring-ai-mcp-server…
MCP connection opened
Registered tools: …

🤖 Example Output from AI Endpoint

Calling:

http://localhost:8080/api/getAIResponse?message=how can you help me today


Returns a response explaining available capabilities such as:

Information retrieval

News updates

Stock price lookup

Date/time lookup

Example stock query:

http://localhost:8080/api/getAIResponse?message=whats the stock price of tesla


Sample response:

The current stock price of Tesla, Inc. (TSLA) is $475.19 USD on the NasdaqGS exchange…

🏁 Summary

This project provides:

✔ A working Spring Boot MCP server
✔ Custom tools exposed via Spring AI @Tool
✔ ToolCallback registration so MCP clients can discover tools
✔ Debug logging for troubleshooting
✔ A clean interface for AI agents to call tools programmatically