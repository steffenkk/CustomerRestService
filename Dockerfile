FROM postgres 

# create dir for the jvm
RUN mkdir /usr/lib/jvm

# setting neccessary env variables for postgres
ENV POSTGRES_PASSWORD postgres 
ENV POSTGRES_DB test1

# copying files 
COPY ./resources/init.sql /docker-entrypoint-initdb.d/
COPY ./target /usr/src/app/target
COPY ./resources/postgresql-42.2.14.jar /opt/driver/
COPY ./resources/jdk-14.0.1_linux-x64_bin.tar.gz /usr/lib/jvm


# And add config to postgres that enables DB Access for the app
RUN echo "host all  all    0.0.0.0/0  md5" >> /usr/share/postgresql/pg_hba.conf && \
    echo "listen_addresses='*'" >> /usr/share/postgresql/postgresql.conf


# install JDK 14
RUN tar -xvzf /usr/lib/jvm/jdk-14.0.1_linux-x64_bin.tar.gz -C /usr/lib/jvm/
ENV JAVA_HOME=/usr/lib/jvm/jdk-14.0.1
RUN export PATH=$PATH:$JAVA_HOME/bin:$JAVA_HOME/db/bin:$JAVA_HOME/jre/bin
ENV J2SDKDIR=$JAVA_HOME
ENV J2REDIR =$JAVA_HOME/jre
ENV J2SDKDIR=$JAVA_HOME
ENV DERBY_HOME=$JAVA_HOME/db
RUN update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk-14.0.1/bin/java" 0 && \
    update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk-14.0.1/bin/javac" 0 && \
    update-alternatives --set java /usr/lib/jvm/jdk-14.0.1/bin/java  && \
    update-alternatives --set javac /usr/lib/jvm/jdk-14.0.1/bin/javac

# config class path to contain the jdbc driver
ENV CLASSPATH=/opt/driver/postgresql-42.2.14.jar
RUN export CLASSPATH

# define the port number the container should expose
EXPOSE 8083 5432

# copy the wrapper script into the container
COPY ./resources/all_cmds.sh ./

# start the service
CMD /bin/bash ./all_cmds.sh


