FROM eclipse-temurin:25-jdk-jammy AS builder

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

# ==========================================

FROM eclipse-temurin:25-jre-jammy

WORKDIR /app
# hadolint ignore=DL3008
RUN apt-get update && \
    apt-get install -y --no-install-recommends fontconfig libfreetype6 libwebp7 libwebp-dev && \
    rm -rf /var/lib/apt/lists/*

# สร้างและสลับไปใช้ non-root user
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 9000

ENTRYPOINT ["java", "--enable-native-access=ALL-UNNAMED", "-jar", "app.jar"]
