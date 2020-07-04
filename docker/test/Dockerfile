FROM openjdk:8-jre-slim
VOLUME /tmp
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone
ARG DEPENDENCY=lib
ADD ${DEPENDENCY}/*.jar app.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=test", "app.jar"]
EXPOSE 8090