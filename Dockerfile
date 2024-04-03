FROM openjdk:17
EXPOSE 8082
ADD ./build/libs/User-Profile-0.0.1-SNAPSHOT.jar User-Profile-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/User-Profile-0.0.1-SNAPSHOT.jar"]