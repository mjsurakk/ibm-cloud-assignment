# IBM Cloud Assignment

This project contains implementation of the assignment, with React+Semantic UI frontend and Java (JAX-RS) backend.

## Run development environment
```
cd react-ui && npm start
mvn clean install liberty:run-server
```

## Production build and deploy to IBM Cloud
```
cd react-ui && npm run build
mvn clean install -Pprod
ibmcloud cf push
```

