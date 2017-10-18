coordinates=org.dkpro.core_dkpro-core-io-rdf-asl_1.9.0-SNAPSHOT
className=org.dkpro.core.io.rdf.RdfWriter	
inD=../testOutput_de.tudarmstadt.ukp.dkpro.core.corenlp.CoreNlpSegmenter/
otD=../testOutput_$className/.

bash ../Linux_runUIMA.sh $coordinates $className -input $inD -output $otD -PtargetLocation=$otD -PfilenameExtension='.ttl' 
