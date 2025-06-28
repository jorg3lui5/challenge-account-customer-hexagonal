FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY build/libs/jba-msa-customer-management-1.0.1.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]