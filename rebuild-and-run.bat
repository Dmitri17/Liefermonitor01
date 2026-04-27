@echo off
echo ========================================
echo    Пересборка и запуск Liefermonitor01
echo ========================================
echo.

REM Сначала собираем проект
echo Шаг 1: Сборка проекта...
call build.bat
if %errorlevel% neq 0 (
    echo Сборка не удалась. Завершение.
    pause
    exit /b 1
)

echo.
echo Шаг 2: Запуск приложения...
echo.

REM Запускаем приложение
call run.bat
