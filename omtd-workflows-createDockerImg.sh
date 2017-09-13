#!/bin/bash

clear
DockerRegistyHOST=$1
DesciptorsFolderRoot=$2
DesciptorsFolder=$3
GalaxyID=$4

DockerImg="omtd-workflows-executor"
Dockerfile="./omtd-workflows-executor.dockerfile"
DockerImgTag="${DockerRegistyHOST}/openminted/${DockerImg}"

echo "-----"
echo "Dockerfile:"$Dockerfile 
echo "DockerImg:"$DockerImg
echo "DockerImgTag:"$DockerImgTag
echo "-----"

# Build project
mvn clean install

# Generate Galaxy Wrappers & TDMCoordinatesList  file from omtd-share descriptors
echo "Generate galaxy wrappers and TDMCoordinatesList" 
java -jar ./omtd-component-galaxywrappers/target/omtd-component-galaxywrappers-0.0.1-SNAPSHOT-exec.jar $DesciptorsFolderRoot $DesciptorsFolder $GalaxyID
# Copy output coordinates to DMCoordinatesList.txt
cat $DesciptorsFolderRoot$GalaxyID"coordinates.list" > TDMCoordinatesList.txt

# Build image.
echo "-- -- Build image" 
docker build -f $Dockerfile -t $DockerImg .

# Tag the image.
echo "-- -- Tag image" 
docker tag -f $DockerImg $DockerImgTag

# Push it to Registry.
echo "-- -- Push image"
docker push $DockerImgTag

# TBA: Now that image is pushed copy wrappers to target machine/dir
# so that everything appears in Galaxy UI. 
echo "-- -- Copying wrappers"

 

