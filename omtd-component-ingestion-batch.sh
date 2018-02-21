#!/bin/bash

# This scripts finds the OMTD-SHARE xml files in 
# monitoredDir and copies them to the respective folders
# depending on the component type; UIMA, GATE, Docker-based.
# Then components are ingested to Galaxy. I.e., the respective Galaxy xml files
# are generated and copied to the Galaxy server. 

monitoredDir="/var/lib/docker/volumes/stack_registrydata/_data/"
targetDir="ComponentsForGalaxy"

if [ ! -d "$targetDir" ]; then
	mkdir $targetDir
	cd $targetDir
	mkdir "UIMA"
	mkdir "GATE"
	mkdir "Docker"
fi

for x in `find $monitoredDir -type f`
do
	echo $x
	if grep -q 'framework>UIMA' "$x"; then
   		echo "UIMA"
		cp $x $targetDir/UIMA/
 	elif grep -q 'framework>GATE' "$x";
	then
		echo "GATE" 
		cp $x $targetDir/GATE/
	else
		echo "Docker"
		cp $x $targetDir/Docker/
	fi
done

./omtd-component-ingestionGen.sh ingestion-confs/omtdReg/ingestion.docker.conf
./omtd-component-ingestionGen.sh ingestion-confs/omtdReg/ingestion.GATE.conf
./omtd-component-ingestionGen.sh ingestion-confs/omtdReg/ingestion.UIMA.conf
