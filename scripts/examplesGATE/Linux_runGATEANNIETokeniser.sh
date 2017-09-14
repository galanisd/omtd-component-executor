#!/usr/bin/env bash

maven="uk.ac.gate.plugins:annie:8.5-SNAPSHOT"
classname=gate.creole.tokeniser.DefaultTokeniser
inD=../testInput/
otD=../testOutput-annie/
../Linux_runGATE.sh $maven $classname $inD $otD
