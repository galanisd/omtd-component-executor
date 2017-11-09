#!/bin/bash

omtdStoreCorpusID=$1
outDir=$2

# Create working dir.
cd  /root/omtd-installations/omtd-store/scripts/;
workDir=$(mktemp -d -p "$(pwd)");
echo $workDir

# Download archive and unzip it.
echo $omtdStoreCorpusID
zip=$workDir/$omtdStoreCorpusID".zip"
bash LinuxStartOMTDStoreClient.sh url http://83.212.101.85:8090 downloadArch $omtdStoreCorpusID $zip;
cd $workDir;
unzip $omtdStoreCorpusID".zip";

# Move files to outDir
find $omtdStoreCorpusID"/fulltext/" > out.txt;
#mkdir -p $outDir;
find $omtdStoreCorpusID"/fulltext/" -type f | xargs cp -t $outDir;


