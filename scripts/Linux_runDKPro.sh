clear

coordinates=$1
className=$2
inDir=$3
otDir=$4

jarList=$(cat ../TDMClasspathLists/"classpath."$coordinates)

#echo $jarList
java -Dloader.path=$jarList -jar $(pwd)/../omtd-component-dkpro-uima-1.9.0/target/omtd-component-dkpro-uima-1.9.0-0.0.1-SNAPSHOT-exec.jar $className $inDir $otDir
