FROM postgres 

# Install OpenJDK-8
RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get install -y ant && \
    apt-get clean;

# Fix certificate issues
RUN apt-get update && \
    apt-get install ca-certificates-java && \
    apt-get clean && \
    update-ca-certificates -f;

# setting neccessary env variables
ENV POSTGRES_PASSWORD postgres 
ENV POSTGRES_DB test1
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JAVA_HOME

# copying from dump - will run during startup by default
COPY ./init.sql /docker-entrypoint-initdb.d/

# set a working directory
WORKDIR /usr/src/app

# COPY the target folder thet contains the jar
COPY ./target ./target

# Copy postgres jdbc and add du class path

# define the port number the container should expose
EXPOSE 8083

# start the service
CMD java -jar ./target/hausarbeit-0.0.1-SNAPSHOT.jar