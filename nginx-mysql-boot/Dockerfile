FROM java:8
WORKDIR /app
COPY ./target/spring-boot-simple-0.0.1-SNAPSHOT.jar /simple.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/simple.jar"]

# docker build -t spring-boot-simple:v1 .