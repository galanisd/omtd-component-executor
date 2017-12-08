#!/bin/bash

galaxyToolsDir=/srv/galaxy/tools/

ln -s $(pwd)/reload.sh $galaxyToolsDir"reload.sh" 
ln -s $(pwd)/testReload.sh $galaxyToolsDir"testReload.sh"

