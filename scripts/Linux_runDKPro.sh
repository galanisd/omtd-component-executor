#!/bin/bash

rootFolder=/opt/omtd-workflows-executor
#rootFolder=/home/ilsp/Desktop/DG/OMTD/omtd-workflows-executor

coordinates=$1
className=$2
inDir=$3
otDir=$4

jarList=$(cat $rootFolder/TDMClasspathLists/"classpath."$coordinates)

#echo $jarList
java -Xmx4096m -Dloader.path=$jarList -jar $rootFolder/omtd-component-uima-2.8.1/target/omtd-component-uima-2.8.1-0.0.1-SNAPSHOT-exec.jar $className $inDir $otDir
