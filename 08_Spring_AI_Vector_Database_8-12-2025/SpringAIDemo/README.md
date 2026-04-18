Spring AI Demo
===============

Small demo Spring Boot project that demonstrates integration with Spring AI components (embedding models, vector stores, chat client and tools). The app builds a local vector store from a product details text file and exposes several REST endpoints showing chat, RAG (retrieval-augmented generation), embeddings and simple tools.

Quick start
-----------

Prerequisites
- Java 17+ (or the version required by the project)
- Maven (or use the included mvnw / mvnw.cmd on Windows)

Run locally (Windows PowerShell):

    .\mvnw.cmd spring-boot:run

Or build jar and run:

    .\mvnw.cmd package; java -jar target\your-artifact-name.jar

Project structure (important files)
- src/main/java/com/telusko/SpringAIDemo/AIController.java  - main REST controller exposing the endpoints
- src/main/java/com/telusko/SpringAIDemo/AppConfig.java    - Spring configuration: VectorStore bean (MariaDBVectorStore)
- src/main/java/com/telusko/SpringAIDemo/DataInit.java     - Reads product_details.txt, splits it into documents, and loads them into the vector store at startup
- src/main/java/com/telusko/SpringAIDemo/DateTimeTool.java - Small tool exposing current date/time methods (annotated with @Tool)
- src/main/java/com/telusko/SpringAIDemo/NewsTool.java     - Tool to fetch news headlines for a topic (uses RestTemplate; replace the API key for production)
- src/main/java/com/telusko/SpringAIDemo/StockMarketTool.java - Tool that fetches latest stock price JSON from Yahoo Finance endpoints

REST endpoints
-------------
All endpoints are under the default server port (8080) unless changed in application.properties.

- GET /api/{message}
  - Sends a free-form prompt to the configured ChatClient with tools attached (DateTimeTool, NewsTool, StockMarketTool).
  - Returns the text output from the chat response.

- GET /api/movie?type={type}&year={year}&lang={lang}
  - Constructs a PromptTemplate asking the chat model to suggest a movie of the given type/year/language and return a structured response (name, cast, length, rating, plot).
  - Returns the model's textual content.

- GET /api/embedding?text={text}
  - Returns the embedding vector (float[]) for the provided text using the configured EmbeddingModel.

- GET /api/similarity?text1={t1}&text2={t2}
  - Computes cosine similarity between the embeddings of two texts and returns a percentage-like double value.

- GET /api/products?text={text}
  - Performs a similarity search on the configured VectorStore (topK = 2) and returns matching Document records.

- GET /api/ask/{query}
  - Runs an automatic RAG flow using the VectorStore and a QuestionAnswerAdvisor. It retrieves context documents for the query and asks the chat model to answer using only the retrieved context (prompt template enforced).

Supporting components
---------------------
- AppConfig
  - Provides a VectorStore bean. The project is configured to use a MariaDB-backed vector store (table name `vector_store`, dimensions 1536, cosine distance). There is a commented-out option to use a simple in-memory vector store.

- DataInit
  - On startup (@PostConstruct) reads `product_details.txt` from resources, splits the text into smaller documents using `TokenTextSplitter`, and adds them to the vector store.

- Tools
  - DateTimeTool: utility methods annotated with @Tool to expose current datetime (optionally for a given timezone) to chat prompts.
  - NewsTool: fetches news JSON from NewsAPI for a topic (note: an API key is hard-coded in this demo — replace with a secure configuration like environment variables in real projects).
  - StockMarketTool: fetches price data from Yahoo Finance endpoints and returns JSON. It uses extra headers to avoid simple blocking.

Notes & troubleshooting
-----------------------
- Swagger UI: If you see a Whitelabel Error Page when opening /swagger-ui/index.html, that means Swagger/OpenAPI UI is not available or not configured. This project does not include explicit Swagger configuration or dependencies in the code shown here. To enable Swagger UI, add a Springdoc (springdoc-openapi-ui) or springfox dependency and the appropriate configuration.

- Secrets & keys: The demo contains a hard-coded NewsAPI key. Do not commit real secrets. Use application.properties, environment variables or a secrets manager.

- Database/Vector Store: The MariaDBVectorStore requires a running MariaDB instance and correct JDBC configuration in application.properties. If you don't have MariaDB available, switch to SimpleVectorStore in `AppConfig` (commented-out) for local testing without DB.

- Build checks: If you change dependencies or Java versions, re-run the maven build: `mvnw.cmd clean package`.

Contact
-------
This README is auto-generated from the project sources. Inspect the listed classes for more details.

