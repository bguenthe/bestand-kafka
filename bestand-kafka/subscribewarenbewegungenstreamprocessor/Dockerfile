FROM openjdk:11-jre-slim
COPY build/libs/subscribewarenbewegungenstreamprocessor-0.0.1-SNAPSHOT.jar .
CMD
CMD java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8002 -Xmx400m -Xms400m -jar subscribewarenbewegungenstreamprocessor-0.0.1-SNAPSHOT.jar
EXPOSE 8082
EXPOSE 8002
