#!/bin/bash

#imageName="snf-1301.ok-kno.grnetcloud.net/openminted/omtd-component-executor-gate:8.5-SNAPSHOT"
imageName="snf-1301.ok-kno.grnetcloud.net/openminted/omtd-component-executor-dkpro-core:1.9.0-SNAPSHOT"

command="docker rmi -f $imageName;exit"
echo $command

ssh -t root@83.212.72.96 $command
ssh -t root@83.212.72.97 $command
ssh -t root@83.212.72.98 $command
ssh -t root@83.212.72.99 $command
ssh -t root@83.212.72.100 $command
