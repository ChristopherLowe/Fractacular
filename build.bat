@echo off
echo Checking system
echo .
echo You path is:   
echo %PATH%
echo .
echo Your JRE is
java -version
echo ..
echo Your Java compiler is
javac -version
echo ...
echo Your Ant is
call ant.bat -version
echo .....
echo Building
call ant.bat build
echo .......
echo Saving
call ant.bat save
echo ............
echo Running
call ant.bat run
set /P theuserinput="Press [ENTER] to finish"
