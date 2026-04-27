# PowerShell скрипт для скрытого запуска
Start-Process -FilePath "java" -ArgumentList "-jar", "target\Liefermonitor01-1.0-SNAPSHOT-jar-with-dependencies.jar" -WindowStyle Hidden

