#!/bin/bash

clear

# -- Script parameters
DockerRegistyHOST=$1
OMTDSHAREDescriptorsFolderRoot=$2
OMTDSHAREDescriptorsFolder=$3
GalaxyID=$4
ComponentID=$5
ComponentVersion=$6
Dockerfile=$7
DockerID=$8
DockerVersion=$9
Push=${10}
DockerImg="NOTPROVIDED"
DockerImgTag="NOTPROVIDED"

echo "args:"$@
echo $DockerID
echo $DockerVersion
echo $Push
# -- 

# Before everything build project.
mvn clean install

# If dockerfile is provided
# then build DockerImg, DockerImgTag values.
if [ $Dockerfile != "none" ]; then
	# Docker image name.
	DockerImg="omtd-component-executor-"${DockerID}":"${DockerVersion}
	# Docker image tag.
	DockerImgTag="${DockerRegistyHOST}/openminted/${DockerImg}"

	echo "-----"
	echo "Dockerfile:"$Dockerfile 
	echo "DockerImg:"$DockerImg
else
	DockerImg=""
	DockerImgTag=${11}
fi

echo "DockerImgTag:"$DockerImgTag
echo "-----"

# Generate 
# * Galaxy Wrappers 
# * TDMCoordinatesList file (only for Maven-based components)
# from omtd-share descriptors
suffix=".wrapper."$ComponentID"."$ComponentVersion".xml"
echo "Generate galaxy wrappers and TDMCoordinatesList" 
java -jar ./omtd-component-galaxy/target/omtd-component-galaxy-0.0.1-SNAPSHOT-exec.jar $OMTDSHAREDescriptorsFolderRoot $OMTDSHAREDescriptorsFolder $GalaxyID $DockerImgTag $suffix

# If needed copy coordinates file.
GeneratedCoordinatesList=$OMTDSHAREDescriptorsFolderRoot$GalaxyID"coordinates.list"
if [ -f $GeneratedCoordinatesList ]; then
	# Copy output coordinates to TDMCoordinatesList.txt
	cat $GeneratedCoordinatesList > TDMCoordinatesList.txt
fi

# If dockerfile is provided
if [ $Dockerfile != "none" ]; then
	# Build image.
	echo "-- -- Build image" 
	sudo docker build -f $Dockerfile -t $DockerImg .
fi

echo "Push:"$Push
if [ $Push == "yes" ]; then
	# Tag the image for OMTD docker registry.
	echo "-- -- Tag image" 
	sudo docker tag -f $DockerImg $DockerImgTag

	# Push it to OMTD docker Registry.
	echo "-- -- Push image:"$DockerImgTag
	sudo docker push $DockerImgTag
fi

# Now that image is pushed and is available to docker registry 
# copy wrappers to target machine/dir
# so that everything appears in Galaxy UI. 
echo "-- -- Copying wrappers"
wrappersDir=$OMTDSHAREDescriptorsFolderRoot$OMTDSHAREDescriptorsFolder"_wrappers"
scp -r $wrappersDir/* user@snf-1289.ok-kno.grnetcloud.net:/srv/galaxy/tools/$GalaxyID
scp -r $wrappersDir/* root@snf-1480.ok-kno.grnetcloud.net:/srv/galaxy/tools/$GalaxyID 

