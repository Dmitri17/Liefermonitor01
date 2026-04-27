@echo off
echo ========================================
echo    Сборка Liefermonitor01
echo ========================================
echo.

REM Проверяем наличие Maven
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ОШИБКА: Maven не найден!
    echo Установите Maven и добавьте его в PATH.
    echo.
    pause
    exit /b 1
)

echo Maven найден. Начинаем сборку...
echo.

REM Очищаем и собираем проект
mvn clean package -DskipTests

if %errorlevel% neq 0 (
    echo.
    echo ========================================
    echo ОШИБКА при сборке проекта!
    echo ========================================
    echo.
    echo Проверьте ошибки выше и исправьте их.
    echo.
    pause
    exit /b 1
)

echo.
echo ========================================
echo Сборка завершена успешно!
echo ========================================
echo.
echo Созданные файлы:
if exist "target\Liefermonitor01-1.0-SNAPSHOT.jar" (
    echo - Liefermonitor01-1.0-SNAPSHOT.jar
)
if exist "target\Liefermonitor01-1.0-SNAPSHOT-jar-with-dependencies.jar" (
    echo - Liefermonitor01-1.0-SNAPSHOT-jar-with-dependencies.jar ^(используйте этот для запуска^)
)
echo.
echo Для запуска используйте: run.bat или start.bat
echo.
pause
