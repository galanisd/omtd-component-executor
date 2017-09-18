coordinates=de.tudarmstadt.ukp.dkpro.core_de.tudarmstadt.ukp.dkpro.core.readability-asl_1.8.0
className=de.tudarmstadt.ukp.dkpro.core.readability.ReadabilityAnnotator
inD=../testOutput_de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter/
otD=../testOutput_$className/

bash ../Linux_runUIMA.sh $coordinates $className $inD $otD
