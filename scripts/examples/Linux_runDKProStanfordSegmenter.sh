coordinates=de.tudarmstadt.ukp.dkpro.core_de.tudarmstadt.ukp.dkpro.core.stanfordnlp-gpl_1.9.0-SNAPSHOT
className=de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter
inD=../testOutput_de.tudarmstadt.ukp.dkpro.core.io.pdf.PdfReader/
otD=../testOutput_$className/

bash ../Linux_runDKPro.sh $coordinates $className $inD $otD
