@echo off
copy target\Liefermonitor01-1.0-SNAPSHOT.jar Liefermonitor01-Distribution\
if exist target\classes\de\zmiter\liefermonitor01\Logik\*.cer (
    copy target\classes\de\zmiter\liefermonitor01\Logik\*.cer Liefermonitor01-Distribution\
)
if exist target\classes\de\zmiter\liefermonitor01\Logik\*.form (
    copy target\classes\de\zmiter\liefermonitor01\Logik\*.form Liefermonitor01-Distribution\
)
echo Files copied successfully!
pause
