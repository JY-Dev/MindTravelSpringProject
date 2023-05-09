FROM amazoncorretto:17
ARG JAR_PATH=./build/libs
VOLUME ["/log"]
COPY ./firebase_admin_sdk.json .
COPY ${JAR_PATH}/MindTravel-0.0.1-SNAPSHOT.jar app.jar
CMD ["java","-jar","app.jar"]