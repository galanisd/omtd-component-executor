#!/bin/bash

# Get cmd parameters
SRC=$1
echo $SRC
TRG=$2
echo $TRG

# Get timestamp
current_time=$(date "+%Y.%m.%d-%H.%M.%S-%3N")
echo "Current Time : $current_time"

# Clear TRG dir
sudo rm -rf $TRG/*

# CP each file in SRC to TRG dir.
# Final files have a timestamp prefix.
for f in $SRC/*
do
        echo "Processing $f"
        fileName=$(basename $f)
        targetFile=$TRG"/"$current_time$fileName
        echo "Target $targetFile"
        cp $f $targetFile
done

