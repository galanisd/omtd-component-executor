#!/usr/bin/env bash

maven="uk.ac.gate.plugins:annie:8.5-SNAPSHOT"
classname=gate.creole.tokeniser.DefaultTokeniser
inD=../testInput/
otD=../testOutput-gate-tokens/
../Linux_runGATE.sh $maven $classname $inD $otD "-PtokeniserRulesURL=creole://uk.ac.gate.plugins;annie;8.5-SNAPSHOT/resources/tokeniser/AlternateTokeniser.rules" -PannotationSetName=Tokens
