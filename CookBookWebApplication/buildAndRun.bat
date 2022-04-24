@echo off
call mvn clean package
call docker build -t com.mycompany/CookBookWebApplication .
call docker rm -f CookBookWebApplication
call docker run -d -p 9080:9080 -p 9443:9443 --name CookBookWebApplication com.mycompany/CookBookWebApplication