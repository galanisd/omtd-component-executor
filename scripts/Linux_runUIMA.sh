#!/bin/bash

# Decide where the UIMA executor is installed.
componentExecutorInstallationDir=/opt/omtd-component-executor
locRepo=/opt/TDMlocalRepo/

if [ -d "$componentExecutorInstallationDir" ]; then
	echo "Using default installation dir $componentExecutorInstallationDir"
else
	componentExecutorInstallationDir=$(pwd)/../..
	echo "Using dir $componentExecutorInstallationDir"
fi

# We need the following to call the UIMA executor.
coordinates=$1
className=$2
inDir=""
otDir=""
uimaParams=""

# Parse arguments and fill.
while [[ $# -gt 0 ]]
do
key="$1"

case $key in
    -input)
    inDir="$2"
    shift # past argument
    shift # past value
    ;;
    -output)
    otDir="$2"
    shift # past argument
    shift # past value
    ;;
    -P*)
    uimaParams=$uimaParams" "$1
    shift # past argument
    ;;
    *)    # unknown option
    shift # past argument
    ;;
esac
done

dir=$(pwd)
if [ ! -d "$componentExecutorInstallationDir/TDMlocalRepo" ]; then
	ocoord=${coordinates//_/:}
	echo "ocoord:"$ocoord
	echo $ocoord > $componentExecutorInstallationDir/TDMCoordinatesList.txt
	cd $componentExecutorInstallationDir
	bash FetchDependenciesUIMA.sh
	cd $dir
else
	echo "$locRepo exists...no need to fetch deps."
fi

echo "coordinates:"$coordinates
echo "inDir:"$inDir
echo "otDir:"$otDir
echo "uimaParams:"$uimaParams

# Retrieve dependencies jar list.
jarList=$(cat $componentExecutorInstallationDir/TDMClasspathLists/"classpath."$coordinates)
#echo $jarList

# Call executor which is a java Spring-Boot based executable. 
java -Xmx4096m -Dloader.path=$jarList -jar $componentExecutorInstallationDir/omtd-component-uima/target/omtd-component-uima-0.0.1-SNAPSHOT-exec.jar -className $className -input $inDir -output $otDir $uimaParams 
