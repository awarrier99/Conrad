@echo off
cd .\src
javac ConradPrototype.java  -d %cd%\..\bin
echo Successful!
pause