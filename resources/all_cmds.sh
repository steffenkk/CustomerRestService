#!/bin/bash

# run the initdb cmd
gosu postgres /usr/lib/postgresql/12/bin/initdb

# Start the postgres process
gosu postgres pg_ctl -D /var/lib/postgresql/data start

# restore db from dump
gosu postgres psql postgres < /docker-entrypoint-initdb.d/init.sql

# Run the Jar 
java -jar /usr/src/app/target/hausarbeit-0.0.1-SNAPSHOT.jar
