coordinates=de.tudarmstadt.ukp.dkpro.core_de.tudarmstadt.ukp.dkpro.core.arktools-gpl_1.9.0-SNAPSHOT
className=de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetPosTagger
de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer
inD=../testOutput_de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer/
otD=../testOutput_$className/

bash ../Linux_runUIMA.sh $coordinates $className -input $inD -output $otD
