FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/reading-is-good-0.0.1-SNAPSHOT.jar readingisgood.jar
ENTRYPOINT ["java", "-jar","readingisgood.jar"]