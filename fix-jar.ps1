# PowerShell script to fix JAR packaging issue
Write-Host "Fixing JAR packaging issue..." -ForegroundColor Green

# Try to run Maven build
Write-Host "Running Maven build..." -ForegroundColor Yellow
try {
    $mavenResult = & mvn clean package -DskipTests 2>&1
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ Maven build successful" -ForegroundColor Green
    } else {
        Write-Host "✗ Maven build failed" -ForegroundColor Red
        Write-Host $mavenResult
        exit 1
    }
} catch {
    Write-Host "✗ Maven not found or failed to run" -ForegroundColor Red
    Write-Host "Please ensure Maven is installed and in PATH" -ForegroundColor Yellow
    exit 1
}

# Check what JAR files were created
Write-Host "`nChecking created JAR files..." -ForegroundColor Yellow
$jarFiles = Get-ChildItem target\*.jar -ErrorAction SilentlyContinue
if ($jarFiles) {
    foreach ($jar in $jarFiles) {
        Write-Host "Found JAR: $($jar.Name)" -ForegroundColor Cyan
    }
} else {
    Write-Host "No JAR files found in target directory" -ForegroundColor Red
    exit 1
}

# Copy the main JAR file
$mainJar = "target\Liefermonitor01-1.0-SNAPSHOT.jar"
if (Test-Path $mainJar) {
    Copy-Item $mainJar "Liefermonitor01-Distribution\" -Force
    Write-Host "✓ Copied main JAR file" -ForegroundColor Green
} else {
    Write-Host "✗ Main JAR file not found: $mainJar" -ForegroundColor Red
    exit 1
}

# Copy additional files
Write-Host "`nCopying additional files..." -ForegroundColor Yellow
$certFiles = Get-ChildItem "target\classes\de\zmiter\liefermonitor01\Logik\*.cer" -ErrorAction SilentlyContinue
if ($certFiles) {
    Copy-Item $certFiles "Liefermonitor01-Distribution\" -Force
    Write-Host "✓ Copied certificate files" -ForegroundColor Green
}

$formFiles = Get-ChildItem "target\classes\de\zmiter\liefermonitor01\Logik\*.form" -ErrorAction SilentlyContinue
if ($formFiles) {
    Copy-Item $formFiles "Liefermonitor01-Distribution\" -Force
    Write-Host "✓ Copied form files" -ForegroundColor Green
}

Write-Host "`n✓ JAR packaging completed successfully!" -ForegroundColor Green
Write-Host "Location: $(Get-Location)\Liefermonitor01-Distribution" -ForegroundColor Cyan
