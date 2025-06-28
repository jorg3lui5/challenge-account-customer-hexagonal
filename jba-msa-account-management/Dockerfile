FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY build/libs/jba-msa-account-management-1.0.1.jar app.jar

EXPOSE 8082

CMD ["java", "-jar", "app.jar"]