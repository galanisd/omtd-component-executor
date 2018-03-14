#!/bin/bash

dir=$(pwd)/
echo -e $dir$1
echo "Reading config...." >&2
. $dir$1
echo -e "Started.." $dir$1

service1=$baseURL/$resourceType/$resourceID
service2=$baseURL/$resourceType/
echo "service1:"$service1

# Download resource xml.
curl -H "Accept: application/xml" -X "GET" $service1 -o resourceMetadata.xml
# Download resource json.
curl -H "Accept: application/json" -X "GET" $service1 -o resourceMetadata.json
#echo "\n" >> resourceMetadata.json

# Delete it
if [ $delete == "yes" ]; then
	echo "service2:"$service2
	curl -H "Content-Type: application/json" -X "DELETE" $service2 -d @resourceMetadata.json 
	echo "deleting:"$delete
fi
