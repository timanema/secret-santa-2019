FROM gradle:6.0-jdk11 as compile-stage

WORKDIR /build
COPY . .
RUN gradle shadowJar -Dorg.gradle.daemon=false

FROM openjdk:11-jre-slim
WORKDIR /run
COPY --from=compile-stage /build/wrapper/build/libs/ss-wrapper.jar .
COPY --from=compile-stage /build/checker/build/libs/ss-checker.jar .

CMD exec java -jar ss-wrapper.jar
