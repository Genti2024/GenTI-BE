FROM openjdk:21-jdk as builder

WORKDIR application
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:21-jdk

WORKDIR application
ENV port 8080
EXPOSE 8080
ENV spring.profiles.active deploy
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

ENTRYPOINT ["java", "-Dspring.profiles.active=deploy", "-Duser.timezone=Asia/Seoul", "org.springframework.boot.loader.launch.JarLauncher"]