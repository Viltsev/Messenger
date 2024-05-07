FROM openjdk:17-oracle
COPY EnglishMessenger/target/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]