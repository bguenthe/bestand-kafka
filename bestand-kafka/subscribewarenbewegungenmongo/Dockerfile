FROM ewolff/docker-java
COPY build/libs/subscribewarenbewegungenmongo-0.0.1-SNAPSHOT.jar .
CMD /usr/bin/java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8002 -Xmx400m -Xms400m -jar subscribewarenbewegungenmongo-0.0.1-SNAPSHOT.jar
EXPOSE 8080
