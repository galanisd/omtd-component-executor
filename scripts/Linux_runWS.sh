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

# We need the following to call the WS executor.
inDir=""
otDir=""
wsParams=""
typesystems=""

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
    echo "###:"$1
    wsParams=$wsParams" "$1
    #if [[ $1 == "-Ptypesystems=" ]]  
  	#then  
  		typesystems=$1
	#fi
    shift # past argument
    ;;
    *)    # unknown option
    shift # past argument
    ;;
esac
done

echo "wsParams:"$wsParams
echo "inDir:"$inDir
echo "otDir:"$otDir
echo "typesystems:"$typesystems
typesystems=${typesystems:14}
echo "typesystems:"$typesystems

dir=$(pwd)
# Clear TDMCoordinatesList.txt
rm $componentExecutorInstallationDir/TDMCoordinatesList.txt
# Clear TDMClasspathLists.
rm $componentExecutorInstallationDir/TDMClasspathLists/*
# For each ts 
for ts in $(echo $typesystems | tr ";" "\n")
do
  # process
  echo "ts:"$ts
  ocoord=${ts//_/:}
  echo $ocoord >> $componentExecutorInstallationDir/TDMCoordinatesList.txt
done

# Fetch dependencies.
cd $componentExecutorInstallationDir
bash FetchDependenciesUIMA.sh	
cd $dir

# Build jar list
jarList=""
for f in $componentExecutorInstallationDir/TDMClasspathLists/*
do
	echo "Processing $f"
	
	if [ -z "$jarList" ]
	then
		jarList=$(cat $f)
	else
		jarList=$(cat $f)","$jarList
	fi
	
done

echo $jarList

# Call executor which is a java Spring-Boot based executable. 
java -Xmx4096m -Dloader.path=$jarList -jar $componentExecutorInstallationDir/omtd-component-webservice/target/omtd-component-webservice-0.0.1-SNAPSHOT-exec.jar -input $inDir -output $otDir $wsParams 
