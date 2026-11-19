@echo off
setlocal
cd /d "%~dp0"

call build.bat
if errorlevel 1 exit /b %errorlevel%

java -cp "%~dp0src\bin" ui.LoginFrame
