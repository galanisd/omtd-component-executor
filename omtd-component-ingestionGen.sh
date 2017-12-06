#!/bin/bash

dir=$(pwd)/
echo -e $dir$1
echo "Reading config...." >&2
. $dir$1
echo -e "Started.." $dir$1

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

locRepo=/opt/TDMlocalRepo/
if [ $UseCache == "yes" ]; then
	echo "RemoveCache:$RemoveCache"
	if [ $RemoveCache == "yes" ]; then
		rm -rf $locRepo
	fi
	
	templocRepo=TDMlocalRepo
	
	# If local repo does not exist
	# download jars and create "./TDMCoordinatesList.txt" and "./TDMClasspathLists/"
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
fi

# Ingest components.
#./omtd-component-ingestion.sh $DockerRegistyHOST $OMTDSHAREDescriptorsFolderRoot $OMTDSHAREDescriptorsFolder $GalaxyID $ComponentID $ComponentVersion $DockerFile $DockerID $DockerVersion $Push $DockerImgTag
./omtd-component-ingestion.sh $DockerRegistyHOST $OMTDSHAREDescriptorsFolderRoot $OMTDSHAREDescriptorsFolder $GalaxyID $DockerFile $DockerID $DockerVersion $Push $DockerImgTag

if [ $UseCache == "yes" ]; then
	# Remove repo.
	rm -rf $templocRepo
fi
