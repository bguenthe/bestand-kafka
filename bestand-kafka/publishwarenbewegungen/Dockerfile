FROM ewolff/docker-java
COPY build/libs/publishwarenbewegungen-0.0.1-SNAPSHOT.jar .
CMD /usr/bin/java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -Xmx400m -Xms400m -jar publishwarenbewegungen-0.0.1-SNAPSHOT.jar
EXPOSE 8080
EXPOSE 8000