coordinates=de.tudarmstadt.ukp.dkpro.core_de.tudarmstadt.ukp.dkpro.core.stanfordnlp-gpl_1.8.0
className=de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger
inD=../testOutput_de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter/
otD=../testOutput_$className/

bash ./Linux_runDKPro.sh $coordinates $className $inD $otD
