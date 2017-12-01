#!/bin/bash


dir=$(pwd)/
echo -e $dir$1
echo "Reading config...." >&2
. $dir$1
echo -e "Started.." $dir$1

#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

./omtd-component-ingestion.sh $DockerRegistyHOST $OMTDSHAREDescriptorsFolderRoot $OMTDSHAREDescriptorsFolder $GalaxyID $ComponentID $ComponentVersion none
