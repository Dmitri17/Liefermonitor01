Write-Host "Simple JAR copy and test script" -ForegroundColor Green

# Copy JAR file
Write-Host "Copying JAR file..." -ForegroundColor Yellow
Copy-Item "target\Liefermonitor01-1.0-SNAPSHOT.jar" "Liefermonitor01-Distribution\" -Force
Write-Host "JAR file copied" -ForegroundColor Green

# Test JAR file
Write-Host "Testing JAR file..." -ForegroundColor Yellow
try {
    $result = & java -jar "Liefermonitor01-Distribution\Liefermonitor01-1.0-SNAPSHOT.jar" 2>&1
    Write-Host "JAR test result: $result" -ForegroundColor Cyan
} catch {
    Write-Host "Error testing JAR: $_" -ForegroundColor Red
}

Write-Host "Script completed" -ForegroundColor Green
