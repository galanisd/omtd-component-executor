#!/bin/bash

OMTDSHAREDescriptorsFolderRoot="/home/ilsp/Desktop/OMTDTemp/"
OMTDSHAREDescriptorsFolder="DKPro_omtds-dkpro-core-1.9.0-SNAPSHOT-20170925-1"
DockerRegistyHOST="snf-1301.ok-kno.grnetcloud.net"
GalaxyID="omtdDKPro"
DockerFile="omtd-component-executor-uima.dockerfile"
ComponentID="dkpro-core"
ComponentVersion="1.9.0-SNAPSHOT"

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

locRepo=/opt/TDMlocalRepo/
templocRepo=TDMlocalRepo

# If local repo does not exist
# download jars and create
# ./TDMCoordinatesList.txt and ./TDMClasspathLists/
if [ ! -d "$locRepo" ]; then
	echo "Fetch deps to $locRepo" 
	mkdir $locRepo
	./FetchDependencies.sh ./TDMCoordinatesList.txt ./TDMClasspathLists/ $locRepo   
else
	echo "$locRepo already exists"
fi

# Copy repo here.
# This is required so that the localRepo is included in docker image.
if [ ! -d "$templocRepo" ]; then
	echo "-- -- Copy $locRepo to $(pwd)" 
	cp -R $locRepo .
fi

# Build image.
./omtd-component-ingestion.sh $DockerRegistyHOST $OMTDSHAREDescriptorsFolderRoot $OMTDSHAREDescriptorsFolder $GalaxyID $ComponentID $ComponentVersion $DockerFile

# Remove repo.
rm -rf $templocRepo
