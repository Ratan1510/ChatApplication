# Chat Application Running Options

## Option 1: Run Directly from Source
1. Uses run_direct.bat
2. Runs main class directly from source files
3. Best for quick testing without building

## Option 2: Run with Classpath
1. Uses run_classpath.bat 
2. Requires compiled classes in target/classes
3. More reliable than direct source running

## Requirements for All Options
- Java 17 or later
- Port 8080 available

## Quick Start
1. Copy these files to target machine:
   - chat-app.jar (the application)
   - run_portable.bat (launcher script)
2. Run `run_portable.bat`

## Accessing the Application
- Open browser to: http://localhost:8080/chat/chatapp

## Configuration Options
To customize:
1. Edit `application.properties` (inside the JAR)
   - Change port: `server.port=8081`
   - Modify context path: `server.servlet.context-path=/myapp`

## Troubleshooting
- If Java isn't found: Install Java 17+ from https://adoptium.net/
- If port is in use: Change server.port in application.properties
