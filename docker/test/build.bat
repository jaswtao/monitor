cd ../../

call mvn clean package -DskipTests

del /S /Q docker\test\lib\*.*

copy /Y target\*.jar docker\test\lib\

cd docker/test

call docker build -t 10.1.8.40:1080/csci/monitor:test-v1.0.1 .

call docker push 10.1.8.40:1080/csci/monitor:test-v1.0.1

@rem docker service update --image 10.1.8.40:1080/csci/financial:test-v1.0.15 fin-backend