# Getting Started

### Basic application operations
Run application 
```shell script
cd deployment
docker stack deploy -c stack.yml isotherm
```

Stop application 
```shell script
docker stack rm isotherm
```

Run local db for running in dev 
```shell script
cd deployment
docker stack deploy -c stack.yml database
```

Stop local db for running in dev 
```shell script
docker stack rm database
```

### create container
```shell script
docker build -t isotherm .
```
### Troubleshoot
Create swarm
```shell script
docker swarm init
```

### Access pgadmin4
[http://127.0.0.1:5555](http://127.0.0.1:5555)

### Termometer installation
https://www.todavianose.com/leer-temperatura-de-un-sensor-ds18b20-con-raspberry-pi/