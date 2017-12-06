#!/bin/bash

monitoredDir="/var/lib/docker/volumes/stack_registrydata/_data/"
dir="ComponentsForGalaxy"

if [ ! -d "$dir" ]; then
	mkdir $dir
	cd $dir
	mkdir "UIMA"
	mkdir "GATE"
	mkdir "Docker"
fi

for x in `find $monitoredDir -type f`
do
	echo $x
	if grep -q 'framework>UIMA' "$x"; then
   		echo "UIMA"
		cp $x $dir/UIMA/
 	elif grep -q 'framework>GATE' "$x";
	then
		echo "GATE" 
		 cp $x $dir/GATE/
	else
		echo "Docker"
		 cp $x $dir/Docker/
	fi
done

./omtd-component-ingestionGen.sh ingestion.docker.conf
./omtd-component-ingestionGen.sh ingestion.GATE.conf
./omtd-component-ingestionGen.sh ingestion.UIMA.conf
