This is code for java springboot microservcies with mongodb as backend and explains how to override application properties externally i.e how to override proeprties set and packaged in the application jar.

# MongoDB data

The data required is sample mysql database - [here](https://www.mysqltutorial.org/mysql-sample-database.aspx#:~:text=%20The%20MySQL%20sample%20database%20schema%20consists%20of,such%20as%20who%20reports%20to%20whom.%20More%20)

* Data needs to be in database named **classicmodels**
* Following collections are needed, data for these is in /data directory
* You can use mongoimport of compass to import this data
* For **Customer collection**, below is the type for columns for import. Rest of all columns are string.
```
_id int32
creditLimit double
postalCode int32
saleRepEmployeeNumber int32
```
* Below is the schema for  **orders collection**
```
_id int32
comments string
customerNumber int32
orderDate date
requiredDate date
shipperDate date
status string
``` 

## Create two instances for mongo DB
* One local, this is default in application.properties
* One on MongoAtlas, this we will  use to override application properties t oconnect to cloud without compiling / building the code again.

# Local Execution
```
cd customer
mvn package
java -jar target/customer-0.0.1-SNAPSHOT.jar
```
* Once the code is running open [swagger-ui](http://localhost:8080/swagger-ui.html)  
* * Now you can run any of the api available on the screen.

## Remote connect to MongoAtlas - Non-Container
There are multiple ways to override application properties without changing the application jar. We will look at two of these:-
* Override using ENV variables  
    * Set environment variable **SPRING_DATA_MONGODB_URI**=```<Atlas URI>```  
This variable corresponds to spring.data.mongodb.uri set in application.properties in the jar. See how it is capitalized and **dot** are replaced by **underscore**.

    * *There is no need to rebuild the code, just execute the existing jar. This time code will connect to Atlas.*

* Override using external proeprties files
    * Create **config** directory in current working directory and place application.properties file with overridden values.  
    * place this line in the properties file  
    ```spring.data.mongodb.uri=<Atlas URI>```
    * Make sure you have unset the variable and then execute the jar again. (No need to recompile the code)


### Now that we know how to externalize configuration for spring boot application, we can move to next step of containerizing it.

# Containerized execution

## Dockerfile
The dockerfile should be multi-staged to keep it lean by ensuring no unnecessary files make into the image. 
[Read here](https://docs.docker.com/develop/develop-images/multistage-build/), and review Dockerfile in this repository in **customer** folder

## Create Image
Run below command to create docker image. You should be in **customer** directory i.e directory where Dockerfile is present
```
docker build -t customer .
```
## Run container
```
docker run -p 8080:8080 --name customer customer
```
**This will error out, there is no localhost mongodb inside container so application cannot connect**

## Run container with ENV set to Atlas
```
docker rm customer
docker run -p 8080:8080  -e "SPRING_DATA_MONGODB_URI=<Your Atlas URI>" --name customer customer
```
If you were able to connect to atlas earlier, this should connect as well and you will be able to launch swagger-ui page.

## Run container with external config file
Local path is path to config dir you created above.
```
docker rm customer
docker run -p 8080:8080  -v "<local path to config dir>:/home/customer/config:ro" --name customer customer
```

Again you will see that this works and connects to Mongo Atlas.

Understanding how to override spring properties is important for running these with docker and subsequently on Kubernetes.

# Kubernetes Execution

Now that this application is container ready, we can look at how to deploy this to Cloud Run and Kubernetes

## Push image to GCR
```
gcloud artifacts repositories create customer --repository-format docker --location <region> --description "spring boot service image"
docker tag customer <region>-docker.pkg.dev/<projectid>/customer/customer:v1
docker push <region>-docker.pkg.dev/<projectid>/customer/customer:v1
```

* Update image name in **deployment.yaml**

## Create Cluster and deploy service
```
gcloud container clusters create gke-mongo --zone asia-south1-a
kubectl apply -f customer/kustomize/deployment.yaml
kubectl expose deployment customer-dep  --name customer --port 8080 --target-port 8080
```
## Test it
```
kubectl port-forward  customer 8080:8080
```
* Open swagger-ui.html on localhost - localhost:8080/swagger-ui.html

# Cloud Run execution (WIP)
To run this on GCP Cloud Run, associated infrastructure needs to bve created. Terraform template for the same is provided [here](https://github.com/skamalj/gcp-terraform/tree/master/stacks/cloudrun_project)

The setup created by this template is described in image below.  Components which are created by terraform are highlighted with TF logo.
![Cloud Run Setup](cloudrun-all.jpg)