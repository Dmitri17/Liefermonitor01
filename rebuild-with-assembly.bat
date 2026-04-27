@echo off
echo Rebuilding with Maven Assembly Plugin...
echo.

echo Step 1: Clean and package...
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo Maven build failed!
    pause
    exit /b 1
)

echo.
echo Step 2: Looking for JAR files in target...
dir target\*.jar

echo.
echo Step 3: Copying JAR file to distribution...
if exist target\Liefermonitor01-1.0-SNAPSHOT-jar-with-dependencies.jar (
    echo Found jar-with-dependencies.jar
    copy target\Liefermonitor01-1.0-SNAPSHOT-jar-with-dependencies.jar Liefermonitor01-Distribution\Liefermonitor01-1.0-SNAPSHOT.jar /Y
) else if exist target\Liefermonitor01-1.0-SNAPSHOT.jar (
    echo Found regular JAR
    copy target\Liefermonitor01-1.0-SNAPSHOT.jar Liefermonitor01-Distribution\ /Y
) else (
    echo No JAR file found!
    pause
    exit /b 1
)

echo.
echo Step 4: Testing JAR file...
java -jar Liefermonitor01-Distribution\Liefermonitor01-1.0-SNAPSHOT.jar

echo.
echo Build completed!
pause
