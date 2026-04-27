@echo off
echo Rebuilding project with Maven...
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo Maven build failed!
    pause
    exit /b 1
)

echo.
echo Copying JAR file to distribution folder...
copy target\Liefermonitor01-1.0-SNAPSHOT.jar Liefermonitor01-Distribution\ /Y
if %errorlevel% neq 0 (
    echo Failed to copy JAR file!
    pause
    exit /b 1
)

echo.
echo Copying additional files...
if exist target\classes\de\zmiter\liefermonitor01\Logik\*.cer (
    copy target\classes\de\zmiter\liefermonitor01\Logik\*.cer Liefermonitor01-Distribution\ /Y
)
if exist target\classes\de\zmiter\liefermonitor01\Logik\*.form (
    copy target\classes\de\zmiter\liefermonitor01\Logik\*.form Liefermonitor01-Distribution\ /Y
)

echo.
echo Build and copy completed successfully!
echo You can now run the application from Liefermonitor01-Distribution folder.
pause
