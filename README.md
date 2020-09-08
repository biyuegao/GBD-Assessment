Environment setting up to run the demo
   software : docker,postman

Step 1 : pull jar image and db image 
docker pull biyue/postgres:latest
docker pull biyue/xp-management:0.0.1-SNAPSHOT 

Step 2 : create docker network in ur local docker
docker network create -d bridge local-network

Step 3 : start db and application container (note : db container should be up before app container)
docker run -d --name my-postgres-local -p 6603:5432 -e POSTGRES_USER=xp-mgmt -e POSTGRES_PASSWORD=password --network=local-network biyue/postgres:latest
docker run -d --name xp-mgmt --hostname xp-mgmt -p 8081:8080 --network=local-network biyue/xp-management:0.0.1-SNAPSHOT
   
Step4 : access application swagger-ui
http://localhost:8081/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/

Step5: import xp-mgmt.postman_collection.json into postman to test it
giturl : https://github.com/biyuegao/GBD-Assessment/blob/master/postman-api-collection/xp-mgmt.postman_collection.json


Alternative if u want to build image by yourself , pls follow below steps 

Step 1 : clone the code from git repo
https://github.com/biyuegao/GBD-Assessment.git

Step 2 : build application image
cd xp-management
mvn spring-boot:build-image -> new image will generate with xp-management:0.0.1-SNAPSHOT

Step 3 : docker network creation and container bring up
docker pull postgres:latest
docker network create -d bridge local-network
docker run -d --name my-postgres-local -p 6603:5432 -e POSTGRES_USER=xp-mgmt -e POSTGRES_PASSWORD=password --network=local-network postgres:latest
docker run -d --name xp-mgmt --hostname xp-mgmt -p 8081:8080 --network=local-network xp-management:0.0.1-SNAPSHOT

the rest step is same as option 1 , have fun 