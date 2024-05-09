FROM openjdk:17-oracle
COPY EnglishMessenger/target/*.jar application.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "application.jar"]