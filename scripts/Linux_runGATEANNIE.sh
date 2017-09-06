#!/usr/bin/env bash

app="creole://uk.ac.gate.plugins;annie;8.5-SNAPSHOT/resources/ANNIE_with_defaults.gapp"
inD=../testInput/
otD=../testOutput-annie/
./Linux_runGATE.sh $app $inD $otD
