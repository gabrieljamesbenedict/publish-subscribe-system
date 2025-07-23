FROM openjdk:17-oracle

LABEL mentainer="ltgabriel@mapua.edu.ph"

WORKDIR /opt/app

EXPOSE 8080

COPY target/sbpsssvc-1.0-SNAPSHOT.jar /opt/app/sbpsssvc.jar

ENTRYPOINT ["java", "-jar", "sbpsssvc.jar"]