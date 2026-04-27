# PowerShell script to check and fix JAR file
Write-Host "Checking JAR file in distribution folder..." -ForegroundColor Green

# Check if JAR file exists in distribution
$distJar = "Liefermonitor01-Distribution\Liefermonitor01-1.0-SNAPSHOT.jar"
if (Test-Path $distJar) {
    $distJarInfo = Get-Item $distJar
    Write-Host "Distribution JAR found: $($distJarInfo.Name)" -ForegroundColor Cyan
    Write-Host "Size: $($distJarInfo.Length) bytes" -ForegroundColor Cyan
    Write-Host "Last modified: $($distJarInfo.LastWriteTime)" -ForegroundColor Cyan
} else {
    Write-Host "Distribution JAR not found!" -ForegroundColor Red
    exit 1
}

# Check if JAR file exists in target
$targetJar = "target\Liefermonitor01-1.0-SNAPSHOT.jar"
if (Test-Path $targetJar) {
    $targetJarInfo = Get-Item $targetJar
    Write-Host "`nTarget JAR found: $($targetJarInfo.Name)" -ForegroundColor Cyan
    Write-Host "Size: $($targetJarInfo.Length) bytes" -ForegroundColor Cyan
    Write-Host "Last modified: $($targetJarInfo.LastWriteTime)" -ForegroundColor Cyan
    
    # Compare files
    if ($distJarInfo.Length -ne $targetJarInfo.Length -or $distJarInfo.LastWriteTime -ne $targetJarInfo.LastWriteTime) {
        Write-Host "`nFiles are different! Copying updated JAR..." -ForegroundColor Yellow
        Copy-Item $targetJar $distJar -Force
        Write-Host "✓ JAR file updated in distribution folder" -ForegroundColor Green
    } else {
        Write-Host "`nFiles are identical" -ForegroundColor Green
    }
} else {
    Write-Host "`nTarget JAR not found! Rebuilding project..." -ForegroundColor Yellow
    
    # Try to run Maven build
    try {
        Write-Host "Running Maven build..." -ForegroundColor Yellow
        $mavenResult = & mvn clean package -DskipTests 2>&1
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✓ Maven build successful" -ForegroundColor Green
            
            # Copy the new JAR
            if (Test-Path $targetJar) {
                Copy-Item $targetJar $distJar -Force
                Write-Host "✓ JAR file copied to distribution folder" -ForegroundColor Green
            } else {
                Write-Host "✗ JAR file still not found after build" -ForegroundColor Red
            }
        } else {
            Write-Host "✗ Maven build failed" -ForegroundColor Red
            Write-Host $mavenResult
        }
    } catch {
        Write-Host "✗ Maven not found or failed to run" -ForegroundColor Red
        Write-Host "Please ensure Maven is installed and in PATH" -ForegroundColor Yellow
    }
}

# Test the JAR file
Write-Host "`nTesting JAR file..." -ForegroundColor Yellow
try {
    $testResult = & java -jar $distJar 2>&1
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ JAR file runs successfully" -ForegroundColor Green
    } else {
        Write-Host "✗ JAR file failed to run" -ForegroundColor Red
        Write-Host "Error: $testResult" -ForegroundColor Red
    }
} catch {
    Write-Host "✗ Java not found or failed to run" -ForegroundColor Red
    Write-Host "Please ensure Java is installed and in PATH" -ForegroundColor Yellow
}

Write-Host "`nCheck completed!" -ForegroundColor Green
