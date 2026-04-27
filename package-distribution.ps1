# PowerShell script to package Liefermonitor01 distribution
Write-Host "Packaging Liefermonitor01 distribution..." -ForegroundColor Green

# Create distribution directory if it doesn't exist
if (!(Test-Path "Liefermonitor01-Distribution")) {
    New-Item -ItemType Directory -Name "Liefermonitor01-Distribution"
}

# Copy the JAR file
if (Test-Path "target\Liefermonitor01-1.0-SNAPSHOT.jar") {
    Copy-Item "target\Liefermonitor01-1.0-SNAPSHOT.jar" "Liefermonitor01-Distribution\" -Force
    Write-Host "✓ Copied JAR file" -ForegroundColor Green
} else {
    Write-Host "✗ JAR file not found in target directory" -ForegroundColor Red
    Write-Host "Please run 'mvn clean package' first" -ForegroundColor Yellow
    exit 1
}

# Copy any certificate files if they exist
if (Test-Path "target\classes\de\zmiter\liefermonitor01\Logik\*.cer") {
    Copy-Item "target\classes\de\zmiter\liefermonitor01\Logik\*.cer" "Liefermonitor01-Distribution\" -Force
    Write-Host "✓ Copied certificate files" -ForegroundColor Green
}

# Copy any form files if they exist
if (Test-Path "target\classes\de\zmiter\liefermonitor01\Logik\*.form") {
    Copy-Item "target\classes\de\zmiter\liefermonitor01\Logik\*.form" "Liefermonitor01-Distribution\" -Force
    Write-Host "✓ Copied form files" -ForegroundColor Green
}

# Make shell script executable (if on Unix-like system)
if ($IsLinux -or $IsMacOS) {
    chmod +x "Liefermonitor01-Distribution\run.sh"
}

Write-Host "`nDistribution package created successfully!" -ForegroundColor Green
Write-Host "Location: $(Get-Location)\Liefermonitor01-Distribution" -ForegroundColor Cyan
Write-Host "`nTo run the application:" -ForegroundColor Yellow
Write-Host "  Windows: Double-click run.bat" -ForegroundColor White
Write-Host "  Linux/Mac: ./run.sh" -ForegroundColor White
