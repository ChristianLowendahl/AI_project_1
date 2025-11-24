# AI-project-1

This repository contains a small example application with a Spring Boot backend and an Angular frontend. The backend exposes endpoints for a simple hello test, summarization and question generation that call a local Ollama service. The frontend includes components and services that call those endpoints.

This README explains how to run the backend and frontend locally, available API endpoints, notes about sanitization/security policy for LLM calls, and a few troubleshooting tips.

---

## Table of contents

- Requirements
- Start the backend (Spring Boot)
- Start the frontend (Angular)
- Available API endpoints
- Notes about the Ollama sanitizer behavior
- Notes about SQLite and PRAGMA foreign_keys (if you use the included DDL scripts)
- Troubleshooting
- Tests

---

## Requirements

- Java 17 (project uses Gradle toolchain configured for Java 17)
- Gradle wrapper (included in the repo)
- Node.js and npm (to run the Angular frontend)
- Angular CLI (recommended, but `npm start` uses the local CLI)
- Optional: Ollama running locally at `http://localhost:11434` if you want summarization/questions to work.


## Start the backend

1. Open a terminal in the repository root (where `gradlew` is located).

2. Build and run with the Gradle wrapper:

```bash
# build
./gradlew :backend:build

# run
./gradlew :backend:bootRun
```

The backend will start on port `8080` by default. If the build succeeds, you should see Spring Boot logs and the application listening on port 8080.

Quick check (in another terminal):

```bash
curl http://localhost:8080/api/hello
# expected: Hello from backend
```

Notes:
- The backend uses the `OllamaService` to call a local Ollama instance at `http://localhost:11434/api/generate` for streaming LLM responses. If you don't run Ollama, the summarization and question endpoints will throw errors when invoked.


## Start the frontend

1. Change into the `frontend` folder and install dependencies:

```bash
cd frontend
npm install
```

2. Start the frontend dev server:

```bash
npm start
# or
ng serve
```

The Angular dev server runs on `http://localhost:4200` by default.

Notes:
- The frontend services call the backend at `http://localhost:8080`. Cross origin is enabled in the controllers for `http://localhost:4200`.
- If you get the error "This command is not available when running the Angular CLI outside a workspace", make sure you're running the command inside the `frontend` folder (the folder that contains `angular.json` and `package.json`).


## Available API endpoints (backend)

- `GET /api/hello` — simple text response ("Hello from backend").
- `POST /api/summarize` — request body: `{ "text": "..." }` — response: `{ "summary": "..." }`.
- `POST /api/questions` — request body: `{ "text": "..." }` — response: `{ "questions": "..." }`.

Example `curl` calls:

```bash
curl -X POST http://localhost:8080/api/summarize -H 'Content-Type: application/json' -d '{"text":"This is a test"}'

curl -X POST http://localhost:8080/api/questions -H 'Content-Type: application/json' -d '{"text":"This is a test"}'
```


## Ollama sanitizer behavior

To reduce prompt-injection risks the backend implements a simple sanitizer inside `OllamaService`:

- It looks for phrases that appear to ask for or reveal system instructions (for example "system prompt", "reveal your instructions", "show prompt", "explain prompt", "repeat instruction" etc.).
- If such phrases are detected the service returns the literal text `ILLEGAL COMMAND` (no call to Ollama is performed).
- This is a conservative, regex-based check and not a comprehensive security mechanism. You can customize or extend the regex in `OllamaService`.

If you want a different behavior (HTTP 400 or a JSON error), consider updating the controllers to throw an exception and map it to an appropriate HTTP status and JSON response.


## Notes about SQLite and PRAGMA foreign_keys

- If you use the included SQL DDL scripts, be aware that SQLite does not enforce foreign keys unless `PRAGMA foreign_keys = ON` is set on each connection.
- That means when your application (or any SQLite client) opens a new connection, you need to execute `PRAGMA foreign_keys = ON` for that connection. In many drivers you can run this as an initialization SQL or configure the connection pool to execute it on new connections.
- When running `sqlite3` from the terminal interactively, you can either run `PRAGMA foreign_keys = ON;` before running other statements or start SQLite with `sqlite3 mydb.sqlite "PRAGMA foreign_keys=ON;"`.


## Troubleshooting

- JSON parse errors from the frontend when calling the backend:
  - If you request plain text from the backend but the Angular `HttpClient` expects JSON, you'll get a parse error. Use `{ responseType: 'text' }` in the front-end `HttpClient.get()` if the endpoint returns plain text.
  - The backend `/api/hello` returns a raw string (text). The frontend's `HelloService` is already configured to request `responseType: 'text'`.

- If `ng serve` reports it's not in a workspace: make sure your current working directory contains `angular.json` (the `frontend` folder). Run the command there.

- If icons from Angular Material don't appear: ensure `MatIconModule` (or the relevant Material module) is imported and the Material icon font is available, e.g. include `<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">` in `index.html`.

- If a streaming LLM call fails: verify the Ollama endpoint is running and reachable at `http://localhost:11434/api/generate`.


## Tests

The backend has standard Spring Boot tests. Run them with Gradle:

```bash
./gradlew :backend:test
```

The frontend has unit test scripts configured in `package.json`:

```bash
cd frontend
npm test
```


---

If you want I can:
- Add example `curl`/Postman collections for the API.
- Add a small `docker-compose.yml` to run the backend + a local Ollama mock for testing.
- Change the `ILLEGAL COMMAND` behavior to return a JSON error with HTTP 400.

Tell me which of those you'd like and I'll implement it.
