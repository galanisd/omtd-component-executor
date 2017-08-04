

coordinates=$1
className=$2
inDir=$3
otDir=$4

jarList=$(cat ../TDMClasspathLists/"classpath."$coordinates)
echo ../TDMClasspathLists/"classpath."$coordinates
#echo $jarList
java -Dloader.path=$jarList -jar $(pwd)/../omtd-component-dkpro-1.8.0-uima-2.8.1/target/omtd-component-dkpro-1.8.0-uima-2.8.1-0.0.1-SNAPSHOT-exec.jar $className $inDir $otDir
