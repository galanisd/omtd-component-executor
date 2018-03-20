#!/bin/bash

dir=$(pwd)/
echo -e $dir$1
echo "Reading config...." >&2
. $dir$1
echo -e "Started.." $dir$1

serviceDown=$baseURL/$resourceType/$resourceID
serviceDele=$baseURL/$resourceType/
echo "serviceDown:"$serviceDown

# Download resource xml.
curl -H "Accept: application/xml" -X "GET" $serviceDown -o resourceMetadata.xml
# Download resource json.
curl -H "Accept: application/json" -X "GET" $serviceDown -o resourceMetadata.json
#echo "\n" >> resourceMetadata.json

# Delete it
if [ $delete == "yes" ]; then
	echo "serviceDele:"$serviceDele
	curl -H "Content-Type: application/json" -X "DELETE" $serviceDele -d @resourceMetadata.json 
	echo "deleting:"$delete
fi
