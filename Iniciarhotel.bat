
@echo off
cd /d "%~dp0"
javac *.java
if errorlevel 1 (
    pause
    exit /b 1
)
start "Servidor Hotelero" java Servidor
timeout /t 3 /nobreak >nul
start "" "http://localhost:8081/"