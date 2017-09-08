#!/bin/bash

Dockerfile="./omtd-workflows-executor.dockerfile"
DockerImg="omtd-workflows-executor"

# Build project
#mvn clean install

# Build image
docker build -f $Dockerfile -t $DockerImg .

