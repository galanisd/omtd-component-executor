#!/bin/bash

export JAVA_OPTS="$JAVA_OPTS -Xmx2G"

# Decide where the GATE executor is installed.
componentExecutorInstallationDir=/opt/omtd-component-executor

if [ -d "$componentExecutorInstallationDir" ]; then
	echo "Using default installation dir $componentExecutorInstallationDir"
else
	componentExecutorInstallationDir=$(pwd)/../..
	echo "Using dir $componentExecutorInstallationDir"
fi

# Run Executor.
groovy $componentExecutorInstallationDir/scripts/Linux_GATEStarter.sh $@
