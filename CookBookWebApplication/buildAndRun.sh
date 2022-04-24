#!/bin/sh
mvn clean package && docker build -t com.mycompany/CookBookWebApplication .
docker rm -f CookBookWebApplication || true && docker run -d -p 9080:9080 -p 9443:9443 --name CookBookWebApplication com.mycompany/CookBookWebApplication