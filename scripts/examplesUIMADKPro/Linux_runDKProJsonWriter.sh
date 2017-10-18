coordinates=de.tudarmstadt.ukp.dkpro.core_de.tudarmstadt.ukp.dkpro.core.io.json-asl_1.9.0-SNAPSHOT
className=de.tudarmstadt.ukp.dkpro.core.io.json.JsonWriter
inD=../testOutput_de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpSegmenter/
otD=../testOutput_$className/.

bash ../Linux_runUIMA.sh $coordinates $className -input $inD -output $otD -PtargetLocation=$otD
