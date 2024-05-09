FROM amazoncorretto:21
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/libs/webapp.jar /app/webapp.jar
ENTRYPOINT ["java","-jar","/app/webapp.jar"]