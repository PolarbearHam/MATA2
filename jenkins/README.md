# Jenkins

## Container build
```
sudo docker build -t jenkins/jenkins:custom .
```

## Container start-up
```
sudo docker run -d -it -u root -v ./jenkins_home:/var/jenkins_home \
-v /var/run/docker.sock:/var/run/docker.sock -p 9090:8080 -p 50000:50000 \
--name jenkins_container jenkins/jenkins:custom
```

## spring_app build script
```
cd sping-api
docker build -t java/maven:custom .
docker stop spring_app
docker rm spring_app
docker run -it -d --name spring_app java/maven:custom /bin/bash
docker cp . spring_app:/home/sping-api
docker exec -w /home/sping-api spring_app sh -c "mvn -Dspring.profiles.active=stage clean compile install package"
docker exec -w /home/sping-api -d spring_app sh -c "java -jar -Dspring.profiles.active=stage target/api-0.0.1-SNAPSHOT.jar >> /home/sping-api/webserver.log"
docker exec spring_app /etc/init.d/redis-server start
```

## react_app build script
```
cd mata2
docker build -t node/nginx:custom .
docker stop react_app
docker rm react_app
docker run -it -d -p 80:80 -p 443:443 --name react_app node/nginx:custom /bin/bash
docker cp . react_app:/home/mata2
docker exec -w /home/mata2 react_app sh -c "npm i"
docker exec -w /home/mata2 react_app sh -c "npm run build"
docker exec react_app /etc/init.d/nginx start
```