#!/bin/bash

clear
DockerRegistyHOST=$1
OMTDSHAREDescriptorsFolderRoot=$2
OMTDSHAREDescriptorsFolder=$3
GalaxyID=$4
DockerID=$5

#
DockerImg="omtd-component-executor"
Dockerfile="./omtd-component-executor.dockerfile"

DockerImgTag="${DockerRegistyHOST}/openminted/${DockerImg}-${DockerID}"

echo "-----"
echo "Dockerfile:"$Dockerfile 
echo "DockerImg:"$DockerImg
echo "DockerImgTag:"$DockerImgTag
echo "-----"

# Build project
mvn clean install

# Generate Galaxy Wrappers & TDMCoordinatesList  file from omtd-share descriptors
echo "Generate galaxy wrappers and TDMCoordinatesList" 
java -jar ./omtd-component-galaxy/target/omtd-component-galaxy-0.0.1-SNAPSHOT-exec.jar $OMTDSHAREDescriptorsFolderRoot $OMTDSHAREDescriptorsFolder $GalaxyID $DockerImgTag
# Copy output coordinates to DMCoordinatesList.txt
cat $OMTDSHAREDescriptorsFolderRoot$GalaxyID"coordinates.list" > TDMCoordinatesList.txt

# Build image.
echo "-- -- Build image" 
sudo docker build -f $Dockerfile -t $DockerImg .

# Tag the image.
echo "-- -- Tag image" 
sudo docker tag -f $DockerImg $DockerImgTag

# Push it to Registry.
echo "-- -- Push image"
sudo docker push $DockerImgTag

# TBA: Now that image is pushed copy wrappers to target machine/dir
# so that everything appears in Galaxy UI. 
echo "-- -- Copying wrappers"

 

