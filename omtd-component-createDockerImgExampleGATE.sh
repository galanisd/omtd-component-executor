#!/bin/bash

OMTDSHAREDescriptorsFolderRoot="/home/ilsp/Desktop/OMTDTemp/"
OMTDSHAREDescriptorsFolder="annie-descriptors"
DockerRegistyHOST="snf-1301.ok-kno.grnetcloud.net"
GalaxyID="omtdGATE"
DockerFile="omtd-component-executor-gate.dockerfile"
ComponentID="gate"
ComponentVersion="8.5-SNAPSHOT"

./omtd-component-createDockerImg.sh $DockerRegistyHOST $OMTDSHAREDescriptorsFolderRoot $OMTDSHAREDescriptorsFolder $GalaxyID $ComponentID $ComponentVersion $DockerFile
