#!/usr/bin/env bash

maven="uk.ac.gate.plugins:annie:8.5-SNAPSHOT"
classname=gate.creole.splitter.SentenceSplitter
inD=../testInput/
otD=../testOutput-annie/
../Linux_runGATE.sh $maven $classname $inD $otD
