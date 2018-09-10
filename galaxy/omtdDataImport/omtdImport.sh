#!/bin/bash

# Get cmd arguments.
omtdStoreCorpusID=$1
omtdSubArchive=$2
outDir=$3

# Create working dir.
cd  /root/omtd-installations/omtd-store/scripts/;
workDir=$(mktemp -d -p "$(pwd)");
echo $workDir

# Download archive from OMTD-store and unzip it.
echo $omtdStoreCorpusID
zip=$workDir/$omtdStoreCorpusID".zip"
bash LinuxStartOMTDStoreClient.sh url http://83.212.101.85:8090 downloadArch $omtdStoreCorpusID $zip;
cd $workDir;
unzip $omtdStoreCorpusID".zip";

# Log files found in omtdSubArchive
find $omtdStoreCorpusID"/$omtdSubArchive/" > out.txt;
# Move files to outDir
#mkdir -p $outDir;
find $omtdStoreCorpusID"/$omtdSubArchive/" -type f | xargs cp -t $outDir;
# Cleanup working dir.
rm -rf $workDir;


