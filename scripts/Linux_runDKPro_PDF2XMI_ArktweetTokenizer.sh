#coordinates=de.tudarmstadt.ukp.dkpro.core_de.tudarmstadt.ukp.dkpro.core.opennlp-asl_1.8.0
coordinates=de.tudarmstadt.ukp.dkpro.core_de.tudarmstadt.ukp.dkpro.core.arktools-gpl_1.8.0
#className=de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter
className=de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTokenizer
inD=../testOutput/
otD=../testOutput2/

bash ./Linux_runDKPro.sh $coordinates $className $inD $otD
