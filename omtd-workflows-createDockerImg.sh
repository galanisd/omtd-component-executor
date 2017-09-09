#!/bin/bash

clear
DockerRegistyHOST=$1

DockerImg="omtd-workflows-executor"
Dockerfile="./omtd-workflows-executor.dockerfile"
DockerImgTag="${DockerRegistyHOST}/openminted/${DockerImg}"

echo "-----"
echo "Dockerfile:"$Dockerfile 
echo "DockerImg:"$DockerImg
echo "DockerImgTag:"$DockerImgTag
echo "-----"

# Build project
#mvn clean install

# TBA
# Generate Galaxy Wrappers & TDMCoordinatesList  file from omtd-share descriptors
echo "Generate galaxy wrappers and TDMCoordinatesList" 

# Build image.
docker build -f $Dockerfile -t $DockerImg .

# Tag the image.
echo "Tag image" 
docker tag -f $DockerImg $DockerImgTag

# Push it to Registry.
echo "Push image"
docker push $DockerImgTag

# TBA: Now that image is pushed copy wrappers to target machine/dir
# so that everything appears in Galaxy UI. 
echo "Copying wrappers"

 

