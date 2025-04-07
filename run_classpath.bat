@echo off
set CLASSPATH=.;app\app\target\classes;app\app\target\dependency\*
java com.chat.app.AppApplication
pause
