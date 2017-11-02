#!/bin/bash

clear
DockerRegistyHOST=$1
OMTDSHAREDescriptorsFolderRoot=$2
OMTDSHAREDescriptorsFolder=$3
GalaxyID=$4
ComponentID=$5
ComponentVersion=$6
Dockerfile=$7

# Docker image name.
DockerImg="omtd-component-executor-"${ComponentID}":"${ComponentVersion}

DockerImgTag="${DockerRegistyHOST}/openminted/${DockerImg}"

echo "-----"
echo "Dockerfile:"$Dockerfile 
echo "DockerImg:"$DockerImg
echo "Docker	ImgTag:"$DockerImgTag
echo "-----"

# Build project
mvn clean install

# Generate Galaxy Wrappers & TDMCoordinatesList  file from omtd-share descriptors
echo "Generate galaxy wrappers and TDMCoordinatesList" 
java -jar ./omtd-component-galaxy/target/omtd-component-galaxy-0.0.1-SNAPSHOT-exec.jar $OMTDSHAREDescriptorsFolderRoot $OMTDSHAREDescriptorsFolder $GalaxyID $DockerImgTag
# Copy output coordinates to TDMCoordinatesList.txt
cat $OMTDSHAREDescriptorsFolderRoot$GalaxyID"coordinates.list" > TDMCoordinatesList.txt

# Build image.
echo "-- -- Build image" 
sudo docker build -f $Dockerfile -t $DockerImg .

# Tag the image.
echo "-- -- Tag image" 
sudo docker tag -f $DockerImg $DockerImgTag

# Push it to Registry.
echo "-- -- Push image:"$DockerImgTag
sudo docker push $DockerImgTag

# TBA: Now that image is pushed copy wrappers to target machine/dir
# so that everything appears in Galaxy UI. 
echo "-- -- Copying wrappers"
# ...
 
