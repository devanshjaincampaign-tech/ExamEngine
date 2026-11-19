@echo off
setlocal
cd /d "%~dp0"
setlocal EnableDelayedExpansion

if not exist "%~dp0src\bin" mkdir "%~dp0src\bin"
set "SOURCES="
for /r "src" %%F in (*.java) do set "SOURCES=!SOURCES! "%%~fF""

javac -encoding UTF-8 -d "%~dp0src\bin" %SOURCES%
set EXIT_CODE=%ERRORLEVEL%

if not %EXIT_CODE%==0 (
    echo.
    echo Build failed. Fix the Java errors listed above.
    exit /b %EXIT_CODE%
)

echo.
echo Build successful. Run:
echo java -cp src\bin ui.LoginFrame
exit /b 0
