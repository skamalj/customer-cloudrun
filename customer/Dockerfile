FROM adoptopenjdk/maven-openjdk11
ADD . /tmp/project
WORKDIR /tmp/project
RUN mvn package

FROM adoptopenjdk/openjdk11:x86_64-ubi-minimal-jre-11.0.12_7
WORKDIR /home/customer
COPY --from=0 /tmp/project/target/customer-0.0.1-SNAPSHOT.jar customer.jar
EXPOSE 8080
CMD ["java","-jar","customer.jar"]