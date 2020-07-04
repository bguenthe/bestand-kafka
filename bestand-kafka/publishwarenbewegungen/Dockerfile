FROM openjdk:11-jre-slim
COPY build/libs/publishwarenbewegungen-0.0.1-SNAPSHOT.jar .
CMD java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000 -Xmx400m -Xms400m -jar publishwarenbewegungen-0.0.1-SNAPSHOT.jar
EXPOSE 8080
EXPOSE 8000