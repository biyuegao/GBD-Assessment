Create a network bridge for containers connect to each other
Step 1 :

docker network create -d bridge local-network

postgreSql start command :

Step 2 : pull db and run it 
docker pull postgres:latest
docker run --name my-postgres-local -p 6603:5432 -e POSTGRES_USER=xp-mgmt -e POSTGRES_PASSWORD=password --network=local-network -d postgres:latest


Step 3 : jar image build
    mvn spring-boot:build-image

Step 4: create container and run application
docker run -d --name xp-mgmt --hostname xp-mgmt -p 8081:8080 --network=local-network xp-management:0.0.1-SNAPSHOT


Step5 : check api swagger-ui
http://localhost:8081/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/

Step6: call api please refer xp-mgmt.postman_collection.json

