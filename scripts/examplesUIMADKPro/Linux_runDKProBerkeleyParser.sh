coordinates=de.tudarmstadt.ukp.dkpro.core_de.tudarmstadt.ukp.dkpro.core.berkeleyparser-gpl_1.9.0-SNAPSHOT
className=de.tudarmstadt.ukp.dkpro.core.berkeleyparser.BerkeleyParser
inD=../testOutput_de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter/
otD=../testOutput_$className/

bash ../Linux_runUIMA.sh $coordinates $className -input $inD -output $otD
