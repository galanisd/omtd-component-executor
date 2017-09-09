#!/bin/bash

DockerRegistyHOST=$1

DockerImg="omtd-workflows-executor"
Dockerfile="./omtd-workflows-executor.dockerfile"
DockerImgTag="${DockerRegistyHOST}/openminted/${DockerImg}"

echo "Dockerfile:"$Dockerfile 
echo "DockerImg:"$DockerImg
echo "DockerImgTag:"$DockerImgTag

# Build project
#mvn clean install

# TBA: Generate wrappers&TDMCoordinatesList from omtd descriptors 

# TBA: Copy wrappers to target machine/dir 

# Build image
docker build -f $Dockerfile -t $DockerImg .

# Tag the image 
docker tag -f $DockerImg $DockerImgTag

# Push
docker $DockerImgTag