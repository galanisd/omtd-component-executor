#!/usr/bin/env bash

maven="uk.ac.gate.plugins:annie:8.5-SNAPSHOT"
classname=gate.creole.splitter.SentenceSplitter
inD=../testOutput-gate-tokens/
otD=../testOutput-gate-sentences/
../Linux_runGATEScript.sh $maven $classname $inD $otD
