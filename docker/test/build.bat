cd ../../

call mvn clean package -DskipTests

del /S /Q docker\test\lib\*.*

copy /Y target\*.jar docker\test\lib\

cd docker/test

call docker build -t localhost:1080/dhome/monitor:test-v1.0.1 .

@rem call docker push localhost:1080/dhome/monitor:test-v1.0.1
