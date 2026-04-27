@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

echo ========================================
echo    Сборка Liefermonitor01 (Portable)
echo ========================================
echo.

set MAVEN_VERSION=3.9.6
set MAVEN_DIR=%~dp0maven
set MAVEN_ZIP=%~dp0apache-maven-%MAVEN_VERSION%-bin.zip
set MAVEN_HOME=%MAVEN_DIR%\apache-maven-%MAVEN_VERSION%

REM Проверяем, установлен ли Maven локально
if exist "%MAVEN_HOME%\bin\mvn.cmd" (
    echo Maven найден локально: %MAVEN_HOME%
    goto :build
)

REM Проверяем глобальный Maven
where mvn >nul 2>&1
if %errorlevel% equ 0 (
    echo Используем глобально установленный Maven
    set MVN_CMD=mvn
    goto :build_global
)

echo Maven не найден. Скачиваем портативную версию...
echo.

REM Создаем папку maven если не существует
if not exist "%MAVEN_DIR%" mkdir "%MAVEN_DIR%"

REM Скачиваем Maven
echo Скачиваем Apache Maven %MAVEN_VERSION%...
curl -L -o "%MAVEN_ZIP%" "https://dlcdn.apache.org/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip"
if %errorlevel% neq 0 (
    echo Ошибка при скачивании Maven!
    echo Попробуйте скачать вручную:
    echo https://dlcdn.apache.org/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip
    pause
    exit /b 1
)

REM Распаковываем Maven
echo Распаковываем Maven...
powershell -Command "Expand-Archive -Path '%MAVEN_ZIP%' -DestinationPath '%MAVEN_DIR%' -Force"
if %errorlevel% neq 0 (
    echo Ошибка при распаковке Maven!
    pause
    exit /b 1
)

REM Удаляем zip файл
del "%MAVEN_ZIP%"

:build
set MVN_CMD=%MAVEN_HOME%\bin\mvn.cmd

:build_global
echo.
echo Начинаем сборку проекта...
echo.

REM Собираем проект
call %MVN_CMD% clean package -DskipTests
if %errorlevel% neq 0 (
    echo.
    echo ========================================
    echo ОШИБКА при сборке проекта!
    echo ========================================
    pause
    exit /b 1
)

echo.
echo ========================================
echo Сборка завершена успешно!
echo ========================================
echo.

REM Показываем созданные файлы
echo Созданные JAR файлы:
if exist "target\Liefermonitor01-1.0-SNAPSHOT-jar-with-dependencies.jar" (
    echo   [OK] target\Liefermonitor01-1.0-SNAPSHOT-jar-with-dependencies.jar
    echo.
    echo Этот файл содержит все зависимости и готов к запуску:
    echo   java -jar target\Liefermonitor01-1.0-SNAPSHOT-jar-with-dependencies.jar
) else if exist "target\Liefermonitor01-1.0-SNAPSHOT.jar" (
    echo   [OK] target\Liefermonitor01-1.0-SNAPSHOT.jar
    echo.
    echo ВНИМАНИЕ: Этот JAR не содержит зависимости!
)

echo.
pause
