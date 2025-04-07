@echo off
REM Portable chat application runner
REM Includes embedded JAR file

echo Extracting application...
jar xf chat-app.jar

echo Starting chat application...
java -jar chat-app.jar
pause
