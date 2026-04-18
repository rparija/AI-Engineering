Project summary (brief):

- This is a small Spring Boot demo application that exposes a simple REST API endpoint to generate an AI-style response to a user message.
- The controller `SimpleAiController` handles requests to `/api/getAIResponse?message=...` and returns a generated response (demo logic, not a full AI model).
- The application reads sample configuration from `src/main/resources/mcp-servers.json`.

Key files:
- `McpApplication.java` — Spring Boot application entry point.
- `SimpleAiController.java` — REST controller that implements the `/api/getAIResponse` endpoint.
- `src/main/resources/mcp-servers.json` — example server/config data used by the app.

Quick start (Maven):

- Build: mvn package
- Run: mvn spring-boot:run

Example endpoint:
- GET /api/getAIResponse?message="hello"  -> returns a short AI-style response.

This README provides a short overview; see source files for implementation details.

http://localhost:8080/api/getAIResponse?message="what all command you can execute on the file system"
http://localhost:8080/api/getAIResponse?message="create a file adding description about this project"
