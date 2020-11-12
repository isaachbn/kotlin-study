FROM library/openjdk:8-alpine3.9
WORKDIR /opt/app
RUN echo https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh > /bin/wait-for-it.sh && chmod +x /bin/wait-for-it.sh
ARG JAR_FILE=target/tour-*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]