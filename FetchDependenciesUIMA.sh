locRepo=/opt/TDMlocalRepo/

if [ ! -d "$locRepo" ]; then
	mkdir $locRepo
else
	echo $locRepo" exists"
fi

bash FetchDependencies.sh ./TDMCoordinatesList.txt ./TDMClasspathLists/ $locRepo   
