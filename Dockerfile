FROM eclipse-temurin:17-jdk-alpine AS builder
ENV PROJECT_NAME=ExampleTelegramBot

ENV GRADLE_OPTS="-Dorg.gradle.daemon=false"
ENV GRADLE_USER_HOME=/tmp/gradle

WORKDIR /tmp/${PROJECT_NAME}

COPY . .

RUN ./gradlew clean installDist

FROM eclipse-temurin:17-jre-alpine
ENV PROJECT_NAME=ExampleTelegramBot

WORKDIR /app

COPY --from=builder /tmp/${PROJECT_NAME}/build/install/${PROJECT_NAME} /app

CMD java -Xmx256m -Dlog4j2.formatMsgNoLookups=true --add-opens java.base/java.lang=ALL-UNNAMED -jar /app/${PROJECT_NAME}.jar
