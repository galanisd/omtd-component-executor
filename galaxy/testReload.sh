#!/bin/bash

# Get cmd parameter
NUMOFFILES=$1
echo $NUMOFFILES

POOL=omtdDKProAll
TRG=omtdDKPro
TMP=tmp

# Clear TMP
sudo rm -rf $TMP/*

# Select subset from a dir and copy to TMP
find $POOL -type f | shuf -n $NUMOFFILES | sort | sudo xargs cp -t $TMP

./reload.sh $TMP $TRG

ls -l omtdDKPro 
ls -l omtdDKPro | wc -l
