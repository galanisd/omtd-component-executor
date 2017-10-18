# !!!! Works for small inputs. 
coordinates=de.tudarmstadt.ukp.dkpro.core_de.tudarmstadt.ukp.dkpro.core.corenlp-gpl_1.9.0-SNAPSHOT
className=de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpPosTagger
inD=../testOutput_de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpSegmenter/
otD=../testOutput_$className/

bash ../Linux_runUIMA.sh $coordinates $className -input $inD -output $otD
