coordinates=de.tudarmstadt.ukp.dkpro.core_de.tudarmstadt.ukp.dkpro.core.frequency-asl_1.9.0-SNAPSHOT
className=de.tudarmstadt.ukp.dkpro.core.frequency.phrasedetection.FrequencyCounter
inD=../testOutput_de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter/
otD=../testOutput_$className/

bash ../Linux_runUIMA.sh $coordinates $className -input $inD -output $otD -PtargetLocation='./counts.txt'

