FROM openjdk:8-jre-alpine
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
#ONBUILD ADD . /usr/src/app

ADD cluster/cluster.xml /user/src/app/
COPY write-verticle-0.1-jar-with-dependencies.jar /usr/src/app

CMD ["java", "-jar", "/usr/src/app/write-verticle-0.1-jar-with-dependencies.jar", "-cluster","-cp","."]