@echo off
echo author itning
set ROOT_DIR=%~dp0
set JAR_DIR=%ROOT_DIR%pic\
set INSTALL_VERSION=1.0.0
if not exist %JAR_DIR%aspose-cells-8.6.2.jar call:file_not_found_func aspose-cells-8.6.2.jar
if not exist %JAR_DIR%aspose-slides-15.7.0-edu.jar call:file_not_found_func aspose-slides-15.7.0-edu.jar
if not exist %JAR_DIR%aspose-words-15.8.0-jdk16.jar call:file_not_found_func aspose-words-15.8.0-jdk16.jar
call mvn install:install-file -Dfile=%JAR_DIR%aspose-cells-8.6.2.jar -DgroupId=top.itning.aspose -DartifactId=cells -Dversion=%INSTALL_VERSION% -Dpackaging=jar
call mvn install:install-file -Dfile=%JAR_DIR%aspose-slides-15.7.0-edu.jar -DgroupId=top.itning.aspose -DartifactId=slides -Dversion=%INSTALL_VERSION% -Dpackaging=jar
call mvn install:install-file -Dfile=%JAR_DIR%aspose-words-15.8.0-jdk16.jar -DgroupId=top.itning.aspose -DartifactId=words -Dversion=%INSTALL_VERSION% -Dpackaging=jar
pause
exit

:file_not_found_func
echo file %~1 not found !!!
pause
exit
goto:eof