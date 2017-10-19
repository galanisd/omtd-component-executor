#!/usr/bin/env bash

maven="uk.ac.gate.plugins:annie:8.5-SNAPSHOT"
classname=gate.creole.tokeniser.DefaultTokeniser
inD=../testInput/
otD=../testOutput-gate-tokens/
../Linux_runGATEScript.sh $maven $classname $inD $otD
