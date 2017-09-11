#!/bin/bash

DockerRegistyHOST="snf-765691.vm.okeanos.grnet.gr"
DesciptorsFolderRoot="/home/ilsp/Desktop/OMTDTemp/"
DesciptorsFolder="omtds-dkpro-core-1.9.0-SNAPSHOT"
GalaxyID="omtdDKPro"


bash omtd-workflows-createDockerImg.sh $DockerRegistyHOST $DesciptorsFolderRoot $DesciptorsFolder $GalaxyID
