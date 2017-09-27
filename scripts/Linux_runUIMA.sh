#!/bin/bash

rootFolder=/opt/omtd-component-executor
#rootFolder=/home/ilsp/Desktop/DG/OMTD/omtd-component-executor

coordinates=$1
className=$2
inDir=$3
otDir=$4

jarList=$(cat $rootFolder/TDMClasspathLists/"classpath."$coordinates)

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
java -Xmx4096m -Dloader.path=$jarList -jar $rootFolder/omtd-component-uima-2.8.1/target/omtd-component-uima-2.8.1-0.0.1-SNAPSHOT-exec.jar -className $className -input $inDir -output $otDir $uimaParams 
