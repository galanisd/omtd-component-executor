#!/bin/bash

dir=$(pwd)/
echo -e $dir$1
echo "Reading config...." >&2
. $dir$1
echo -e "Started.." $dir$1

service=$baseURL/$resourceType/$resourceID
echo "service:"$service

# Download resource xml.
curl -H "Accept: application/xml" -X "GET" $service -o resourceMetadata.xml
# Download resource json.
curl -H "Accept: application/json" -X "GET" $service -o resourceMetadata.json

# Retrieve json 
metaJSON=`cat resourceMetadata.json`
#echo $metaJSON

# Delete it
if [ $delete == "yes" ]; then
	echo "service:"$service
	#curl -H "Content-Type: application/json" -X "DELETE" -d $metaJSON $service 
	curl  -H "Content-Type: application/json" -d "anything" -X "DELETE"  $service 
	echo "deleting:"$delete
fi
