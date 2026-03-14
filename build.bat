@echo off
echo Downloading Maven portable...
powershell -Command "Invoke-WebRequest -Uri 'https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.zip' -OutFile maven.zip"
powershell -Command "Expand-Archive maven.zip -DestinationPath ."
ren apache-maven-3.9.9 maven
set PATH=%CD%\maven\bin;%PATH%
mvn clean package
echo JAR ready in target/
pause
