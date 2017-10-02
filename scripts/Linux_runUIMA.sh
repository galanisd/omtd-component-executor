#!/bin/bash

componentExecutorInstallationDir=/opt/omtd-component-executor

if [ -d "$componentExecutorInstallationDir" ]; then
	echo "Using default installation dir $componentExecutorInstallationDir"
else
	componentExecutorInstallationDir=$(pwd)/../..
	echo "Using dir $componentExecutorInstallationDir"
fi

coordinates=$1
className=$2
inDir=$3
otDir=$4

# Retrieve dependencies jar list.
jarList=$(cat $componentExecutorInstallationDir/TDMClasspathLists/"classpath."$coordinates)

uimaParams=""
cnt=1

for arg in "$@"
do
  echo "Arg #$cnt= $arg"
  if [ "$cnt" -gt "4" ] 
  then
  	uimaParams=$uimaParams" "$arg
  fi
  let "cnt+=1"
done

echo $uimaParams

#echo $jarList
java -Xmx4096m -Dloader.path=$jarList -jar $componentExecutorInstallationDir/omtd-component-uima-2.8.1/target/omtd-component-uima-2.8.1-0.0.1-SNAPSHOT-exec.jar -className $className -input $inDir -output $otDir $uimaParams 
