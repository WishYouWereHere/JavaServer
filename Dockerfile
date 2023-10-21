FROM openjdk:8

WORKDIR /server

COPY . /server/

EXPOSE 8000

RUN javac Server.java

ENTRYPOINT [ "java", "Server"]