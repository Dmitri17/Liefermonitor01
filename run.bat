@echo off
echo ========================================
echo    Liefermonitor01 - Thyssen Schachtbau
echo ========================================
echo.
echo Запуск приложения...
echo.

REM Проверяем наличие Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ОШИБКА: Java не найдена в системе!
    echo Пожалуйста, установите Java 17 или выше.
    echo Скачать можно с: https://www.oracle.com/java/technologies/downloads/
    echo.
    pause
    exit /b 1
)

REM Проверяем наличие JAR файла
if not exist "target\Liefermonitor01-1.0-SNAPSHOT-jar-with-dependencies.jar" (
    echo ОШИБКА: JAR файл не найден!
    echo Выполните сборку проекта: mvn clean package
    echo.
    pause
    exit /b 1
)

echo JAR файл найден. Запуск приложения...
echo.

REM Запускаем приложение
java -jar "target\Liefermonitor01-1.0-SNAPSHOT-jar-with-dependencies.jar"

REM Если программа завершилась с ошибкой
if %errorlevel% neq 0 (
    echo.
    echo ========================================
    echo Приложение завершилось с ошибкой!
    echo ========================================
    echo.
    echo Возможные причины:
    echo - Проблемы с подключением к базе данных
    echo - Отсутствие необходимых библиотек
    echo - Неправильная конфигурация
    echo.
    echo Проверьте логи выше для получения подробной информации.
    echo.
)

echo.
echo Нажмите любую клавишу для выхода...
pause >nul
