FROM openjdk:11-jdk-slim as builder
ENV PROJECT_NAME=Gradle-SingleProject

ENV GRADLE_OPTS=-Dorg.gradle.daemon=false \
  GRADLE_USER_HOME=/tmp/gradle

WORKDIR /tmp/${PROJECT_NAME}

COPY . .

RUN ./gradlew clean installDist

FROM openjdk:11-jre-slim
ENV PROJECT_NAME=Gradle-SingleProject

WORKDIR /app

COPY --from=builder /tmp/${PROJECT_NAME}/build/install/${PROJECT_NAME} /app

CMD java -jar /app/${PROJECT_NAME}.jar
