
coordinatesList=$1
TDMClasspathLists=$2
TDMlocalRepo=$3

java -jar $(pwd)/omtd-component-dependencies-fetcher/target/omtd-component-dependencies-fetcher-0.0.1-SNAPSHOT-exec.jar $coordinatesList $TDMClasspathLists $TDMlocalRepo
 


