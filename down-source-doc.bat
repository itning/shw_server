@echo off
rem 下载源码和文档
rem author itning
call mvn dependency:sources
call mvn dependency:resolve -Dclassifier=javadoc
pause
exit