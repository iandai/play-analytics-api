# Analytics Api
An api response with json data for analytics use.

### Setup
* Set up application.conf file.
* Set up mysql database, create user and retention_rate table.


### Run on local

 ```sh
 ./activator run -Dconfig.file=conf/dev.conf
 ./activator run -Dconfig.file=conf/prod.conf
 ```
Then, the application can be seen at [http://localhost:9000/](http://localhost:9000/).


### Deploy from local with Fabric

 - Step1: install fabric(a deployment tool)
 
 ```sh 
 pip install fabric
 ```

 - Step2: enable one step ssh login to remote server

 ```
 ssh ana-dev
 ```

 - Step3: run fabirc
 
 ```sh
 fab dev deploy
 fab dev stop_server
 fab dev start_server
 ```
 
