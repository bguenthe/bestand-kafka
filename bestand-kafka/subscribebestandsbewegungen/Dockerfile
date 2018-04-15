FROM openjdk:9-jre-slim
COPY build/libs/subscribebestandsbewegungen-0.0.1-SNAPSHOT.jar .
CMD /usr/bin/java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8003 -Xmx400m -Xms400m -jar subscribebestandsbewegungen-0.0.1-SNAPSHOT.jar
EXPOSE 8083
EXPOSE 8003
